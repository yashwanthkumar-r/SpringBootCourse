package com.codingshuttle.jpaTutorial.week_03_spring_data_jpa.controllers;

import com.codingshuttle.jpaTutorial.week_03_spring_data_jpa.entities.ProductEntity;
import com.codingshuttle.jpaTutorial.week_03_spring_data_jpa.repositories.ProductRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    private final int PAGE_SIZE=5;

    @GetMapping("/product/sku/{sku}")
    public List<ProductEntity> getSkuContainingOrderByPrice(@PathVariable String sku){
        return productRepository.findBySkuContainingOrderByPrice(sku);

    }

    @GetMapping("/product/title/{title}")
    public ProductEntity getTitleOrderByPrice(@PathVariable String title){
        return productRepository.findByTitleOrderByPrice(title);

    }

    //if the column name given doesn't match then sortBy default ID
    @GetMapping("/product")
    public List<ProductEntity> getAll(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") int pageNo){

     /*  // return productRepository.findBy(Sort.by(Sort.Direction.DESC ,sortBy));
        return productRepository.findBy(Sort.by(
                Sort.Order.desc(sortBy)     //Primary sort: sortBy (DESC)
                ,Sort.Order.asc("price") //Secondary sort: price (ASC)
        ));*/

/*        Pageable pageable = PageRequest.of(
                pageNo,  //display given pageNo
                PAGE_SIZE, //How many items in a page
                Sort.by(sortBy)); //sortBy the given column

        return productRepository.findAll(pageable).getContent();*/


        //let's combine all derived query, pagination and sorting together

        return productRepository.findByTitleContainingIgnoreCase(
                title,
                PageRequest.of(pageNo,PAGE_SIZE,Sort.by(sortBy))
        );

    }


}
