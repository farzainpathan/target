package com.product.target.service;

import com.product.target.domain.Product;
import com.product.target.exception.ProductNotFoundException;

import java.util.List;

public interface RequestProduct {

  List<Product> getAllProducts();

  Product getProductByProductId(Long productId) throws ProductNotFoundException;
}
