package com.order.processing.controller;

import com.order.processing.exception.BadRequestException;
import com.order.processing.exception.InternalServerErrorException;
import com.order.processing.service.OrderService;
import com.order.processing.springdoc.OrderDoc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/orders")
public class OrderController implements OrderDoc {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    @GetMapping("/find")
    public ResponseEntity<?> findOrders(@RequestParam(required = false) Integer orderId,
                                        @RequestParam(required = false) LocalDate startDate,
                                        @RequestParam(required = false) LocalDate endDate) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.findOrders(orderId, startDate, endDate));
        } catch (BadRequestException e) {
            LOGGER.warn("Error during process of find orders - Type: {} - Message: {}", e.getClass().getSimpleName(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (InternalServerErrorException e) {
            LOGGER.info("Error during process of find orders - Type: {} - Message: {}", e.getClass().getSimpleName(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during process of find orders!");
        }
    }
}