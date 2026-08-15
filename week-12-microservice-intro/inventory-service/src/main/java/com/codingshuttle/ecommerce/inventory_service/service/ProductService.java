package com.codingshuttle.ecommerce.inventory_service.service;

import com.codingshuttle.ecommerce.inventory_service.dto.ProductDto;
import com.codingshuttle.ecommerce.inventory_service.entity.Product;
import com.codingshuttle.ecommerce.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
