package com.order.processing.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;

@JsonPropertyOrder({ "product_id", "value" })
public class ProductDTO {

    @JsonProperty("product_id")
    private Integer productId;
    private BigDecimal value;

    public ProductDTO() {
    }

    public ProductDTO(Integer productId, BigDecimal value) {
        this.productId = productId;
        this.value = value;
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
}
