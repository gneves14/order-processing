package com.order.processing.repository;

import com.order.processing.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = """
            SELECT p
              FROM Product p
             WHERE (:orderId IS NULL OR p.order.orderId = :orderId)
               AND (:startDate IS NULL OR p.order.date >= :startDate)
               AND (:endDate IS NULL OR p.order.date <= :endDate)
            """)
    List<Product> findProducts(Integer orderId, LocalDate startDate, LocalDate endDate);

}
