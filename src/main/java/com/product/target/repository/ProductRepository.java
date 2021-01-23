package com.product.target.repository;

import com.product.target.dao.ProductDao;
import com.product.target.domain.Price;
import com.product.target.domain.Product;
import com.product.target.entity.ProductEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProductRepository implements ProductPersistence {
  private final ProductDao productDao;

  public ProductRepository(ProductDao productDao) {
    this.productDao = productDao;
  }

  @Override
  public List<Product> fetchAllProducts() {
    log.info("Fetching the product details from database");
    return productDao.findAll().stream().map(ProductEntity::toModel).collect(Collectors.toList());
  }
}
