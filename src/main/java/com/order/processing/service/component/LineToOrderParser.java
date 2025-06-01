package com.order.processing.service.component;

import com.order.processing.domain.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
public class LineToOrderParser {

    public void parseLine(String line, Map<Integer, Order> orderCache, Map<Integer, User> userCache, List<Product> products) {
        User user = extractUser(line, userCache);
        Order order = extractOrder(line, user, orderCache);
        extractProduct(line, products, order);
    }

    private static void extractProduct(String line, List<Product> products, Order order) {
        Integer productId = Integer.parseInt(line.substring(65, 75).trim());
        BigDecimal value = new BigDecimal(line.substring(75, 87).trim());

        products.add(ProductBuilder.builder()
                .productId(productId)
                .value(value)
                .order(order)
                .build());
    }

    private static Order extractOrder(String line, User user, Map<Integer, Order> orderCache) {
        Integer orderId = Integer.parseInt(line.substring(55, 65).trim());
        LocalDate date = LocalDate.parse(line.substring(87, 95), DateTimeFormatter.ofPattern("yyyyMMdd"));
        return orderCache.computeIfAbsent(orderId, id -> OrderBuilder.builder()
                .orderId(orderId)
                .date(date)
                .user(user)
                .build());
    }

    private static User extractUser(String line, Map<Integer, User> userCache) {
        Integer userId = Integer.parseInt(line.substring(0, 10).trim());
        String userName = line.substring(10, 55).trim();
        return userCache.computeIfAbsent(userId, id -> UserBuilder.builder()
                .userId(userId)
                .name(userName)
                .build());
    }

}
