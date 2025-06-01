package com.order.processing.service.impl;

import com.order.processing.domain.Product;
import com.order.processing.exception.BadRequestException;
import com.order.processing.exception.InternalServerErrorException;
import com.order.processing.repository.ProductRepository;
import com.order.processing.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> saveAllProducts(List<Product> products) {
        return productRepository.saveAllAndFlush(products);
    }

    @Override
    public List<Product> findProducts(Integer orderId, LocalDate startDate, LocalDate endDate) throws BadRequestException, InternalServerErrorException {
        try {
            return productRepository.findProducts(orderId, startDate, endDate);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

}
