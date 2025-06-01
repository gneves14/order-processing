package com.order.processing.domain;

import java.math.BigDecimal;

public final class ProductBuilder {
    private final Product product;

    private ProductBuilder() {
        product = new Product();
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public ProductBuilder id(Integer id) {
        this.product.setId(id);
        return this;
    }

    public ProductBuilder productId(Integer productId) {
        this.product.setProductId(productId);
        return this;
    }

    public ProductBuilder value(BigDecimal value) {
        this.product.setValue(value);
        return this;
    }

    public ProductBuilder order(Order order) {
        this.product.setOrder(order);
        return this;
    }

    public Product build() {
        return product;
    }
}
