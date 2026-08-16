package com.codingshuttle.ecommerce.inventory_service.service;

import com.codingshuttle.ecommerce.inventory_service.dto.OrderRequestDto;
import com.codingshuttle.ecommerce.inventory_service.dto.OrderRequestItemDto;
import com.codingshuttle.ecommerce.inventory_service.dto.ProductDto;
import com.codingshuttle.ecommerce.inventory_service.entity.Product;
import com.codingshuttle.ecommerce.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;


    public List<ProductDto> getAllInventory(){
        log.info("Fetching all inventory items");
        List<Product> productList = productRepository.findAll();

        return productList.stream().
                map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
    }

    public ProductDto getProductById(Long id){
        log.info("Fetching product with id: {}", id);
        Product product =  productRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Item not found in inventory"));

        return modelMapper.map(product, ProductDto.class);
    }

    @Transactional
    public Double reduceStocks(OrderRequestDto orderRequestDto) {
        log.info("Reducing the stocks");

        Double totalPrice = 0.0;
        for(OrderRequestItemDto orderRequestItemDto: orderRequestDto.getItems()){
            Long productId = orderRequestItemDto.getProductId();
            Integer quantity = orderRequestItemDto.getQuantity();

                Product item = productRepository.findById(productId)
                        .orElseThrow(()->new RuntimeException("Product not found with id: "+ productId));

            if(item.getStock() < quantity){
                throw new RuntimeException("Out Of Stock, we only have :" + item.getStock());
            }

            item.setStock(item.getStock()-quantity);
            productRepository.save(item);

            totalPrice += item.getPrice()*quantity;
        }
        return totalPrice;
    }
}
