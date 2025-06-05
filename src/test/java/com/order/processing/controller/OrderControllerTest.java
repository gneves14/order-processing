package com.order.processing.controller;

import com.order.processing.domain.Order;
import com.order.processing.domain.OrderBuilder;
import com.order.processing.domain.UserBuilder;
import com.order.processing.domain.dto.OrderDTO;
import com.order.processing.domain.dto.OrderResponseDTO;
import com.order.processing.exception.BadRequestException;
import com.order.processing.exception.InternalServerErrorException;
import com.order.processing.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
@ActiveProfiles("test")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Test
    @DisplayName("Should return 200 OK with a list containing pre-registration order objects")
    void shouldReturn200WithListOfOrders() throws Exception {
        Order order = OrderBuilder.builder()
                .orderId(1)
                .date(LocalDate.now())
                .user(UserBuilder.builder()
                        .userId(1)
                        .name("Test 1")
                        .build())
                .build();

        OrderDTO orderDTO = new OrderDTO(order.getOrderId(), BigDecimal.TEN, LocalDate.now(), null);

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO(order.getUser().getUserId(), order.getUser().getName(), Collections.singletonList(orderDTO));

        when(orderService.findOrders(any(), any(), any())).thenReturn(Collections.singletonList(orderResponseDTO));

        mockMvc.perform(get("/orders/find")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].user_id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test 1"));
        verify(orderService, times(1)).findOrders(any(), any(), any());
    }

    @Test
    @DisplayName("Should return 200 OK with a list containing filtered order objects")
    void shouldReturn200WithListOfOrdersFiltered() throws Exception {
        Order order1 = OrderBuilder.builder()
                .orderId(1)
                .date(LocalDate.now())
                .user(UserBuilder.builder()
                        .userId(1)
                        .name("Test 1")
                        .build())
                .build();

        Order order2 = OrderBuilder.builder()
                .orderId(2)
                .date(LocalDate.now())
                .user(UserBuilder.builder()
                        .userId(2)
                        .name("Test 2")
                        .build())
                .build();

        OrderDTO orderDTO1 = new OrderDTO(order1.getOrderId(), BigDecimal.TEN, LocalDate.now(), null);
        OrderDTO orderDTO2 = new OrderDTO(order2.getOrderId(), BigDecimal.TWO, LocalDate.now(), null);

        OrderResponseDTO orderResponseDTO1 = new OrderResponseDTO(order1.getUser().getUserId(), order1.getUser().getName(), Collections.singletonList(orderDTO1));
        OrderResponseDTO orderResponseDTO2 = new OrderResponseDTO(order2.getUser().getUserId(), order2.getUser().getName(), Collections.singletonList(orderDTO2));

        when(orderService.findOrders(eq(2), any(), any())).thenReturn(List.of(orderResponseDTO2));

        mockMvc.perform(get("/orders/find?orderId=2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].user_id").value(2))
                .andExpect(jsonPath("$[0].name").value("Test 2"));
        verify(orderService, times(1)).findOrders(any(), any(), any());
    }

    @Test
    @DisplayName("Should return 400 BAD REQUEST when BadRequestException is thrown during order search")
    void shouldReturn400BadRequestWhenBadRequestExceptionIsThrown() throws Exception {
        when(orderService.findOrders(any(), any(), any())).thenThrow(new BadRequestException("Error"));

        mockMvc.perform(get("/orders/find")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error"));
        verify(orderService, times(1)).findOrders(any(), any(), any());
    }

    @Test
    @DisplayName("Should return 500 INTERNAL SERVER ERROR when InternalServerErrorException is thrown during order search")
    void shouldReturn500InternalServerErrorWhenInternalServerErrorExceptionIsThrown() throws Exception {
        when(orderService.findOrders(any(), any(), any())).thenThrow(new InternalServerErrorException("Internal Error"));

        mockMvc.perform(get("/orders/find")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error during process of find orders!"));
        verify(orderService, times(1)).findOrders(any(), any(), any());
    }

}