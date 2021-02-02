package com.product.target.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Cart details")
public class Cart {
    @ApiModelProperty(notes = "Product details")
    @JsonProperty("products")
    private List<CartProduct> cartProducts;

    @ApiModelProperty(notes = "Cart Total")
    @JsonProperty("grandTotal")
    private BigDecimal cartTotal;
}
