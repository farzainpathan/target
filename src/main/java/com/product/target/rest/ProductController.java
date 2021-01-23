package com.product.target.rest;

import com.product.target.domain.Product;
import com.product.target.service.RequestProduct;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@Api(value = "Target-Product Endpoints")
public class ProductController {

  private final RequestProduct requestProduct;

  public ProductController(RequestProduct requestProduct) {
    this.requestProduct = requestProduct;
  }

  @GetMapping("/products")
  @ApiOperation(value = "This endpoint fetches all the products from database")
  public List<Product> getAllProducts() {
    return requestProduct.getAllProducts();
  }

  @GetMapping("/products/{id}")
  @ApiOperation(value = "This endpoint fetches the products by id from database")
  public Product getProductById(@PathVariable Long id) {
    return Product.builder().build();
  }

  @PutMapping("/products/{id}")
  @ApiOperation(value = "This endpoint updates the products by id into database")
  public Product updatedProductById(@PathVariable Long id) {
    return Product.builder().build();
  }

  @PostMapping("/products")
  @ApiOperation(value = "This endpoint save the products information into database")
  public Product saveProduct(Product product) {
    return Product.builder().build();
  }
}
