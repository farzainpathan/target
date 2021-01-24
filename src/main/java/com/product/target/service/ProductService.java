package com.product.target.service;

import com.product.target.domain.Product;
import com.product.target.exception.ProductNotFoundException;
import com.product.target.repository.ProductPersistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.product.target.service.validator.ProductParameterRequestValidator.PARAMETER_REQUEST_VALIDATOR;

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

  @Override
  public Product getProductByProductId(Long productId) throws ProductNotFoundException {
    log.info("Fetching product details from repository for product Id : " + productId);
    return productPersistence.fetchProductByProductId(productId);
  }

  @Override
  public List<Product> getAllProductsWithinPriceRange(Double lowerLimit, Double higherLimit)
      throws ProductNotFoundException {
    log.info("Fetching all products within the price range between: " + lowerLimit + " and " + higherLimit);
    PARAMETER_REQUEST_VALIDATOR.assertPriceRange(lowerLimit, higherLimit);
    return productPersistence.fetchAllProductsWithinPriceRange(lowerLimit, higherLimit);
  }

  @Override
  public Product getProductByProductName(String productName) throws ProductNotFoundException {
    log.info("Fetching product details from repository for product name : " + productName);
    PARAMETER_REQUEST_VALIDATOR.assertParameterStringValue(productName);
    return productPersistence.fetchProductByProductName(productName);
  }

  @Override
  public Product getProductById(String id) throws ProductNotFoundException {
    log.info("Fetching product details from repository for Id : " + id);
    PARAMETER_REQUEST_VALIDATOR.assertParameterStringValue(id);
    return productPersistence.fetchProductById(id);
  }
}
