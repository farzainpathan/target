package com.product.target.exception;

import com.product.target.domain.Product;

public class ProductNotFoundException extends Exception {

  public ProductNotFoundException(Product product) {
    super("Invalid request : " + product);
  }

  public ProductNotFoundException(Long productId) {
    super("No product found in the database with id: " + productId);
  }

  public ProductNotFoundException(String key) {
    super("No product found in the database with name|id|category: " + key);
  }

  public ProductNotFoundException(Double lowerLimit, Double higherLimit) {
    super(
        "No product found in the database within the requested price range of : "
            + lowerLimit
            + " and "
            + higherLimit);
  }
}
