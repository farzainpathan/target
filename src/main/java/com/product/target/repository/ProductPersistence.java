package com.product.target.repository;

import com.product.target.domain.Product;
import com.product.target.exception.ProductNotFoundException;

import java.util.List;

public interface ProductPersistence {

  List<Product> fetchAllProducts();

  Product fetchProductById(Long id) throws ProductNotFoundException;
}
