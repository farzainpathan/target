package com.product.target.service;

import com.product.target.domain.Cart;
import com.product.target.domain.Product;
import com.product.target.exception.ProductNotFoundException;

import java.util.List;
import java.util.Map;

public interface RequestProduct {

  List<Product> getAllProducts();

  Product getProductByProductId(Long productId) throws ProductNotFoundException;

  List<Product> getAllProductsWithinPriceRange(Double lowerLimit, Double higherLimit)
      throws ProductNotFoundException;

  List<Product> getProductByProductName(String productName) throws ProductNotFoundException;

  Product getProductById(String id) throws ProductNotFoundException;

  List<Product> getAllProductsByProductCategory(String productCategory)
      throws ProductNotFoundException;

  Product saveProduct(Product product);

  Product updateProductById(Product product) throws ProductNotFoundException;

  Map<Product, Integer> addProduct(Long productId) throws ProductNotFoundException;

  Map<Product, Integer> removeProduct(Long productId) throws ProductNotFoundException;

  Cart checkout();
}
