package com.codingshuttle.ecommerce.order_service.dto;

import com.codingshuttle.ecommerce.order_service.entity.Orders;
import lombok.Data;

@Data
public class OrderRequestItemDto {

        private Long id;
        private Long productId;
        private Integer quantity;
}
