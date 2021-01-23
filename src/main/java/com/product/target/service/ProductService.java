package com.product.target.service;

import com.product.target.domain.Product;
import com.product.target.repository.ProductPersistence;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements RequestProduct{
    private final ProductPersistence productPersistence;

    public ProductService(ProductPersistence productPersistence) {
        this.productPersistence = productPersistence;
    }

    @Override
    public List<Product> getAllProducts() {
        return productPersistence.fetchAllProducts();
    }
}
