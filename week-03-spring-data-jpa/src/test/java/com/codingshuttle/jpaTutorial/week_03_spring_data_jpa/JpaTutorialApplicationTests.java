package com.codingshuttle.jpaTutorial.week_03_spring_data_jpa;

import com.codingshuttle.jpaTutorial.week_03_spring_data_jpa.entities.ProductEntity;
import com.codingshuttle.jpaTutorial.week_03_spring_data_jpa.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class JpaTutorialApplicationTests {

    @Autowired
    private ProductRepository productRepository;


    @Test
    void contextLoads() {
    }

    @Test
    public void testCreateProduct() {
        ProductEntity productEntity = ProductEntity.builder()
                .sku("ITC203")
                .title("Campa")
                .quantity(12)
                .price(BigDecimal.valueOf(1.99))
                .build();
        System.out.println(productRepository.save(productEntity));

    }

    @Test
    public void testGetAllProduct() {
        List<ProductEntity> productEntityList = productRepository.findAll();

        for (ProductEntity e : productEntityList) {
            System.out.println(e);
        }
    }

    @Test
    public void testCustomMethods() {
//     ProductEntity productEntity = productRepository.findByTitleOrderByPrice("Campa");
//        System.out.println(productEntity);

     /*      List<ProductEntity> productEntity1 = productRepository
        .findByCreatedAtAfter(LocalDateTime.of(2025, 1,1,12,8,23));
        System.out.println(productEntity1);

        List<ProductEntity> productEntityList = productRepository
        .findByPriceGreaterThanAndQuantityLessThan(BigDecimal.valueOf(1.99), 12);
        for (ProductEntity p : productEntityList)
            System.out.println(p);

        ProductEntity productEntity2 = productRepository.findByTitleLike("%Cola%");
        System.out.println(productEntity2);

        ProductEntity productEntity3 = productRepository.findByTitleContainingIgnoreCase("buScuit");
        System.out.println(productEntity3);


        Optional<ProductEntity> productEntity4 = productRepository
                .findByPriceAndTitle(BigDecimal.valueOf(3.99),"DEW");
        productEntity4.ifPresent(System.out::println);*/


        List<ProductEntity> productEntityList1 = productRepository.findBySkuContainingOrderByPrice("pep");
        for (ProductEntity p : productEntityList1)
            System.out.println(p);


    }

}
