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
@ApiModel(description = "Product model to create or update")
public class Product {
  @ApiModelProperty(notes = "Technical id for a document", example = "600e982f7dcd745fe7e02e10")
  private String id;

  @ApiModelProperty(notes = "Unique if for a product", example = "100")
  private Long productId;

  @ApiModelProperty(notes = "Product category", example = "grocery")
  private String category;

  @ApiModelProperty(notes = "Product name", example = "Darlah Side Table")
  private String name;

  private Price currentPrice;
}
