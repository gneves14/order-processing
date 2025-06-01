package com.order.processing.domain;

import com.order.processing.domain.dto.ProductDTO;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "product_value")
    private BigDecimal value;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    public Product() {
    }

    public Product(Integer id, Integer productId, BigDecimal value, Order order) {
        this.id = id;
        this.productId = productId;
        this.value = value;
        this.order = order;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ProductDTO toProductDTO() {
        return new ProductDTO(this.productId, this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(productId, product.productId) && Objects.equals(value, product.value) && Objects.equals(order, product.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, value, order);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productId=" + productId +
                ", value=" + value +
                ", order=" + order +
                '}';
    }
}
