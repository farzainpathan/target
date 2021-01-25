package com.product.target.repository;

import com.product.target.domain.Product;
import com.product.target.exception.ProductNotFoundException;

import java.util.List;

public interface ProductPersistence {

  List<Product> fetchAllProducts();

  Product fetchProductByProductId(Long id) throws ProductNotFoundException;

  List<Product> fetchAllProductsWithinPriceRange(Double lowerLimit, Double higherLimit)
      throws ProductNotFoundException;

  Product fetchProductByProductName(String productName) throws ProductNotFoundException;

  Product fetchProductById(String id) throws ProductNotFoundException;

  List<Product> fetchAllProductsByCategory(String grocery) throws ProductNotFoundException;

  Product saveProduct(Product product);

  Product updateProduct(Product updateProduct) throws ProductNotFoundException;
}
