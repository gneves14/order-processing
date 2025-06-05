package com.order.processing.service.component;

import com.order.processing.domain.Order;
import com.order.processing.domain.Product;
import com.order.processing.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
class LineToOrderParserTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Should parse line and populate userCache, orderCache and products list correctly")
    void shouldParseLineSuccessfully() {
        LineToOrderParser parser = new LineToOrderParser();
        String line = "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308";

        Map<Integer, User> userCache = new HashMap<>();
        Map<Integer, Order> orderCache = new HashMap<>();
        List<Product> products = new ArrayList<>();

        parser.parseLine(line, orderCache, userCache, products);

        assertEquals(1, userCache.size());
        assertEquals(1, orderCache.size());
        assertEquals(1, products.size());

        User user = userCache.get(70);
        assertNotNull(user);
        assertEquals(70, user.getUserId());
        assertEquals("Palmer Prosacco", user.getName());

        Order order = orderCache.get(753);
        assertNotNull(order);
        assertEquals(753, order.getOrderId());
        assertEquals(LocalDate.of(2021, 3, 8), order.getDate());
        assertEquals(user, order.getUser());

        Product product = products.getFirst();
        assertNotNull(product);
        assertEquals(3, product.getProductId());
        assertEquals(new BigDecimal("1836.74"), product.getValue());
        assertEquals(order, product.getOrder());
    }
}
