package com.product.target.repository;

import com.product.target.domain.Product;

import java.util.List;

public interface ProductPersistence {
  List<Product> fetchAllProducts();
}
