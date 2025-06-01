package com.order.processing.service.impl;

import com.order.processing.domain.*;
import com.order.processing.domain.dto.OrderResponseDTO;
import com.order.processing.exception.BadRequestException;
import com.order.processing.exception.InternalServerErrorException;
import com.order.processing.repository.OrderRepository;
import com.order.processing.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    ProductService productService;

    @InjectMocks
    OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return a list of saved orders")
    void shouldReturnSavedOrdersWhenGivenValidCollection() {
        Order order1 = OrderBuilder.builder()
                .date(LocalDate.now())
                .orderId(1)
                .user(UserBuilder.builder()
                        .userId(1)
                        .name("User Test 1")
                        .build())
                .build();

        Order order2 = OrderBuilder.builder()
                .date(LocalDate.now())
                .orderId(2)
                .user(UserBuilder.builder()
                        .userId(2)
                        .name("User Test 2")
                        .build())
                .build();

        List<Order> ordersCollection = List.of(order1, order2);

        when(orderRepository.saveAllAndFlush(ordersCollection)).thenReturn(ordersCollection);

        List<Order> ordersList = orderService.saveAllOrders(ordersCollection);

        verify(orderRepository, times(1)).saveAllAndFlush(ordersCollection);
        assertEquals(ordersCollection, ordersList);
    }

    @Test
    @DisplayName("Should return empty list when saving an empty collection of orders")
    void shouldReturnEmptyListWhenGivenEmptyCollection() {
        List<Order> emptyList = List.of();

        when(orderRepository.saveAllAndFlush(emptyList)).thenReturn(emptyList);

        List<Order> result = orderService.saveAllOrders(emptyList);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return grouped order response when given valid parameters")
    void shouldReturnOrderResponseDTOWhenGivenValidData() {
        User user = UserBuilder.builder().userId(1).name("User 1").build();
        Order order = OrderBuilder.builder().orderId(10).date(LocalDate.of(2022, 1, 1)).user(user).build();
        Product product = ProductBuilder.builder().productId(100).value(BigDecimal.TEN).order(order).build();

        when(productService.findProducts(10, LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 10)))
                .thenReturn(List.of(product));

        List<OrderResponseDTO> result = orderService.findOrders(10, LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 10));

        assertEquals(1, result.size());
        assertEquals("User 1", result.getFirst().getName());
        assertEquals(10, result.getFirst().getOrders().getFirst().getOrderId());
        assertEquals(BigDecimal.TEN, result.getFirst().getOrders().getFirst().getTotal());
    }

    @Test
    @DisplayName("Should throw BadRequestException when ProductService throws BadRequestException")
    void shouldThrowBadRequestExceptionWhenProductServiceFails() {
        when(productService.findProducts(any(), any(), any())).thenThrow(new BadRequestException("Invalid params"));

        BadRequestException thrown = assertThrows(BadRequestException.class, () ->
                orderService.findOrders(1, LocalDate.now(), LocalDate.now())
        );

        assertEquals("Invalid params", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw InternalServerErrorException when unexpected exception is thrown")
    void shouldThrowInternalServerErrorExceptionWhenUnexpectedExceptionOccurs() {
        when(productService.findProducts(any(), any(), any())).thenThrow(new RuntimeException("Unexpected error"));

        InternalServerErrorException thrown = assertThrows(InternalServerErrorException.class, () ->
                orderService.findOrders(1, LocalDate.now(), LocalDate.now())
        );

        assertEquals("Unexpected error", thrown.getMessage());
    }
}