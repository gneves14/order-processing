package com.order.processing.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@JsonPropertyOrder({ "order_id", "total", "date", "products" })
public class OrderDTO {

    @JsonProperty("order_id")
    private Integer orderId;
    private BigDecimal total;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private List<ProductDTO> products;

    public OrderDTO() {
    }

    public OrderDTO(Integer orderId, BigDecimal total, LocalDate date, List<ProductDTO> products) {
        this.orderId = orderId;
        this.total = total;
        this.date = date;
        this.products = products;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
