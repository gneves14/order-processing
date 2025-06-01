package com.order.processing.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({ "user_id", "name", "orders" })
public class OrderResponseDTO {

    @JsonProperty("user_id")
    private Integer userId;
    private String name;
    private List<OrderDTO> orders;

    public OrderResponseDTO() {
    }

    public OrderResponseDTO(Integer userId, String name, List<OrderDTO> orders) {
        this.userId = userId;
        this.name = name;
        this.orders = orders;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }
}
