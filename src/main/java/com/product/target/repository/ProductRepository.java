package com.product.target.repository;

import com.product.target.dao.ProductDao;
import com.product.target.domain.Price;
import com.product.target.domain.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductRepository implements ProductPersistence {
  private final ProductDao productDao;

  public ProductRepository(ProductDao productDao) {
    this.productDao = productDao;
  }

  @Override
  public List<Product> fetchAllProducts() {
    return List.of(
        Product.builder()
            .productId(100L)
            .name("The Big Lebowski (Blu-ray) (Widescreen)")
            .currentPrice(Price.builder().value(13.49).currency("USD").build())
            .build(),
        Product.builder()
            .productId(200L)
            .name("Testing the swagger ui")
            .currentPrice(Price.builder().value(20.63).currency("USD").build())
            .build());
  }
}
