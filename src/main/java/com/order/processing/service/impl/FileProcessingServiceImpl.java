package com.order.processing.service.impl;

import com.order.processing.domain.Order;
import com.order.processing.domain.Product;
import com.order.processing.domain.User;
import com.order.processing.exception.InternalServerErrorException;
import com.order.processing.service.FileProcessingService;
import com.order.processing.service.OrderService;
import com.order.processing.service.ProductService;
import com.order.processing.service.UserService;
import com.order.processing.service.component.LineToOrderParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileProcessingServiceImpl implements FileProcessingService {

    private final LineToOrderParser lineToOrderParser;
    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    public FileProcessingServiceImpl(LineToOrderParser lineToOrderParser, OrderService orderService,
                                     UserService userService, ProductService productService) {
        this.lineToOrderParser = lineToOrderParser;
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public void processFile(MultipartFile file) throws InternalServerErrorException {
        Map<Integer, User> userCache = new HashMap<>();
        Map<Integer, Order> orderCache = new HashMap<>();
        List<Product> products = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineToOrderParser.parseLine(line, orderCache, userCache, products);
            }
            persistObjectsOfOrders(userCache, orderCache, products);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    private void persistObjectsOfOrders(Map<Integer, User> userCache, Map<Integer, Order> orderCache, List<Product> products) {
        userService.saveAllUsers(userCache.values());
        orderService.saveAllOrders(orderCache.values());
        productService.saveAllProducts(products);
    }
}
