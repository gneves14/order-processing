package com.order.processing.service;

import com.order.processing.domain.Product;
import com.order.processing.exception.BadRequestException;
import com.order.processing.exception.InternalServerErrorException;

import java.time.LocalDate;
import java.util.List;

public interface ProductService {

    List<Product> saveAllProducts(List<Product> products);

    List<Product> findProducts(Integer orderId, LocalDate startDate, LocalDate endDate) throws BadRequestException, InternalServerErrorException;
}
