package com.order.processing.service.impl;

import com.order.processing.domain.OrderBuilder;
import com.order.processing.domain.Product;
import com.order.processing.domain.ProductBuilder;
import com.order.processing.domain.UserBuilder;
import com.order.processing.exception.InternalServerErrorException;
import com.order.processing.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return a list of saved products")
    void shouldReturnSavedProductsWhenGivenValidCollection() {
        Product product1 = ProductBuilder.builder()
                .productId(1)
                .value(BigDecimal.TEN)
                .order(OrderBuilder.builder()
                        .date(LocalDate.now())
                        .orderId(1)
                        .user(UserBuilder.builder()
                                .userId(1)
                                .name("User Test 1")
                                .build())
                        .build())
                .build();

        Product product2 = ProductBuilder.builder()
                .productId(2)
                .value(BigDecimal.TEN)
                .order(OrderBuilder.builder()
                        .date(LocalDate.now())
                        .orderId(2)
                        .user(UserBuilder.builder()
                                .userId(2)
                                .name("User Test 2")
                                .build())
                        .build())
                .build();

        List<Product> productsCollection = List.of(product1, product2);

        when(productRepository.saveAllAndFlush(productsCollection)).thenReturn(productsCollection);

        List<Product> productsList = productService.saveAllProducts(productsCollection);

        verify(productRepository, times(1)).saveAllAndFlush(productsCollection);
        assertEquals(productsCollection, productsList);
    }

    @Test
    @DisplayName("Should return a list of products when given valid order ID and date range")
    void shouldReturnProductsWhenGivenValidOrderIdAndDateRange() {
        Product product1 = ProductBuilder.builder()
                .productId(1)
                .value(BigDecimal.TEN)
                .order(OrderBuilder.builder()
                        .date(LocalDate.of(2022, 1, 1))
                        .orderId(1)
                        .user(UserBuilder.builder()
                                .userId(1)
                                .name("User Test 1")
                                .build())
                        .build())
                .build();

        List<Product> productsList = Collections.singletonList(product1);

        Integer orderId = 1;
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.now();

        when(productRepository.findProducts(orderId, startDate, endDate)).thenReturn(productsList);

        List<Product> result = productService.findProducts(orderId, startDate, endDate);

        verify(productRepository, times(1)).findProducts(orderId, startDate, endDate);
        assertEquals(1, result.size());
        assertEquals(1, result.getFirst().getOrder().getOrderId());
    }

    @Test
    @DisplayName("Should return empty list when saving an empty collection of products")
    void shouldReturnEmptyListWhenGivenEmptyCollection() {
        List<Product> emptyList = List.of();

        when(productRepository.saveAllAndFlush(emptyList)).thenReturn(emptyList);

        List<Product> result = productService.saveAllProducts(emptyList);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should throw exception when find products")
    void shouldThrowExceptionWhenFindProducts() {
        when(productRepository.findProducts(anyInt(), any(LocalDate.class), any(LocalDate.class))).thenThrow(new RuntimeException("Internal error"));

        InternalServerErrorException thrown = assertThrows(InternalServerErrorException.class, () -> productService.findProducts(1, LocalDate.now(), LocalDate.now()));

        assertEquals(thrown.getMessage(), "Internal error");
    }
}