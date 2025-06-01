package com.order.processing.service;

import com.order.processing.domain.Order;
import com.order.processing.domain.dto.OrderResponseDTO;
import com.order.processing.exception.BadRequestException;
import com.order.processing.exception.InternalServerErrorException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface OrderService {

    List<Order> saveAllOrders(Collection<Order> orders);

    List<OrderResponseDTO> findOrders(Integer orderId, LocalDate startDate, LocalDate endDate) throws BadRequestException, InternalServerErrorException;
}
