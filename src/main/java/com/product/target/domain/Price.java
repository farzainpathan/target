package com.product.target.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Price model to create or update")
public class Price {
  @ApiModelProperty(notes = "Product price value", example = "20.00")
  private Double value;

  @ApiModelProperty(notes = "Currency name", example = "USD")
  private String currency;
}
