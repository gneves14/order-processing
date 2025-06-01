package com.order.processing.domain;

import java.time.LocalDate;

public final class OrderBuilder {
    private final Order order;

    private OrderBuilder() {
        order = new Order();
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public OrderBuilder orderId(Integer orderId) {
        this.order.setOrderId(orderId);
        return this;
    }

    public OrderBuilder date(LocalDate date) {
        this.order.setDate(date);
        return this;
    }

    public OrderBuilder user(User user) {
        this.order.setUser(user);
        return this;
    }

    public Order build() {
        return order;
    }
}
