package com.product.target.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Product cart model to checkout")
public class CartProduct {
    @ApiModelProperty(notes = "Product details")
    private Product product;

    @ApiModelProperty(notes = "Quantity of the products")
    private Integer quantity;

    @ApiModelProperty(notes = "Cart total amount")
    private BigDecimal total;
}
