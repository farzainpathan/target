package com.product.target.service;

import com.product.target.domain.Product;
import com.product.target.repository.ProductPersistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService implements RequestProduct {
  private final ProductPersistence productPersistence;

  public ProductService(ProductPersistence productPersistence) {
    this.productPersistence = productPersistence;
  }

  @Override
  public List<Product> getAllProducts() {
    log.info("Fetching all product information from repository");
    return productPersistence.fetchAllProducts();
  }
}
