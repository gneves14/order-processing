package com.order.processing.repository;

import com.order.processing.domain.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should return product when orderId exists")
    void shouldReturnProductWhenOrderIdExists() {
        Integer orderId = 1;
        LocalDate startDate = null;
        LocalDate endDate = null;

        this.persistObjects();

        List<Product> productsList = productRepository.findProducts(orderId, startDate, endDate);

        assertNotNull(productsList);
        assertFalse(productsList.isEmpty());
        assertEquals(1, productsList.getFirst().getProductId());
    }

    @Test
    @DisplayName("Should not return product when none exist in database")
    void shouldNotReturnProductWhenNoneExist() {
        Integer orderId = 1;
        LocalDate startDate = null;
        LocalDate endDate = null;

        List<Product> productsList = productRepository.findProducts(orderId, startDate, endDate);

        assertNotNull(productsList);
        assertTrue(productsList.isEmpty());
    }

    @Test
    @DisplayName("Should not return product when orderId does not match any existing product")
    void shouldNotReturnProductWhenOrderIdDoesNotMatch() {
        Integer orderId = 2;

        this.persistObjects();

        List<Product> productsList = productRepository.findProducts(orderId, null, null);

        assertNotNull(productsList);
        assertTrue(productsList.isEmpty());
    }

    private void persistObjects() {
        User user = UserBuilder.builder()
                .userId(1)
                .name("Test 1")
                .build();

        Order order = OrderBuilder.builder()
                .orderId(1)
                .date(LocalDate.now())
                .user(user)
                .build();

        Product product = ProductBuilder.builder()
                .productId(1)
                .value(BigDecimal.TEN)
                .order(order)
                .build();

        entityManager.persist(user);
        entityManager.persist(order);
        entityManager.persist(product);

    }

}