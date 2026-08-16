package com.codingshuttle.ecommerce.order_service.service;

import com.codingshuttle.ecommerce.order_service.clients.InventoryFeignClient;
import com.codingshuttle.ecommerce.order_service.dto.OrderRequestDto;
import com.codingshuttle.ecommerce.order_service.entity.OrderItem;
import com.codingshuttle.ecommerce.order_service.entity.OrderStatus;
import com.codingshuttle.ecommerce.order_service.entity.Orders;
import com.codingshuttle.ecommerce.order_service.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrdersRepository ordersRepository;

    private final ModelMapper modelMapper;

    private final InventoryFeignClient inventoryFeignClient;

    public List<OrderRequestDto> getAllOrders(){
        log.info("Fetching all Orders");
        List<Orders> orders = ordersRepository.findAll();

        return orders.stream().
                map(order -> modelMapper.map(order, OrderRequestDto.class))
                .toList();
    }

    public OrderRequestDto getOrderById(Long id){
        log.info("Fetching product with id: {}", id);
        Orders order =  ordersRepository.findById(id)
                .orElseThrow(()->new RuntimeException("orders not found in DB"));

        return modelMapper.map(order, OrderRequestDto.class);
    }

    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {

        Double totalPrice = inventoryFeignClient.reduceStocks(orderRequestDto);

        orderRequestDto.setTotalPrice(BigDecimal.valueOf(totalPrice));

        Orders orders = modelMapper.map(orderRequestDto, Orders.class);

        for(OrderItem orderItem: orders.getItems()){
            orderItem.setOrder(orders);
        }

        orders.setTotalPrice(totalPrice);
        orders.setOrderStatus(OrderStatus.CONFIRMED);

        return modelMapper.map(ordersRepository.save(orders), OrderRequestDto.class);

    }
}
