package com.product.target.entity;

import com.product.target.domain.Price;
import com.product.target.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product")
public class ProductEntity {

  @Id
  @Field("id")
  private String id;

  @Field("productId")
  private Long productId;

  @Field("name")
  private String name;

  @Field("price")
  private Double price;

  @Field("currency")
  private String currency;

  @Field("category")
  private String category;

  public Product toModel() {
    return Product.builder()
        .id(id)
        .productId(productId)
        .name(name)
        .category(category)
        .currentPrice(Price.builder().value(price).currency(currency).build())
        .build();
  }
}
