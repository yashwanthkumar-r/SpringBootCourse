package com.codingshuttle.jpaTutorial.week_03_spring_data_jpa.repositories;

import com.codingshuttle.jpaTutorial.week_03_spring_data_jpa.entities.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    //get product which matches the given title
    ProductEntity findByTitleOrderByPrice(String title);

    //get product that are created after given date&time
    List<ProductEntity> findByCreatedAtAfter(LocalDateTime aLocal);

    List<ProductEntity> findByPriceGreaterThanAndQuantityLessThan(BigDecimal price, int quantity);

    ProductEntity findByTitleLike(String title);

    List<ProductEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    List<ProductEntity> findBySkuContainingOrderByPrice(String sku);

    List<ProductEntity> findBy(Sort sortBy);


    //We use Optional<T> to safely handle null return values and avoid NullPointerException
    //Optional<ProductEntity> findByPriceAndTitle(BigDecimal price, String title);

    @Query("select e from ProductEntity e where price=?1 and title=?2")
    Optional<ProductEntity> findByPriceAndTitle(BigDecimal price, String title);

}
