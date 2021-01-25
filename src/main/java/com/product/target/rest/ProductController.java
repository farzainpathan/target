package com.product.target.rest;

import com.product.target.domain.Product;
import com.product.target.exception.ProductNotFoundException;
import com.product.target.service.RequestProduct;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1")
@Api(tags = "Product Commands", value = "ProductCommands")
public class ProductController {

  private final RequestProduct requestProduct;

  public ProductController(RequestProduct requestProduct) {
    this.requestProduct = requestProduct;
  }

  @GetMapping("/products")
  @ApiOperation(value = "This endpoint fetches all the products from database")
  public List<Product> getAllProducts() {
    log.info("Ask service for all product information");
    return requestProduct.getAllProducts();
  }

  @GetMapping("/product-by/{id}")
  @ApiOperation(value = "This endpoint fetches the products by product id from database")
  public Product getProductByProductId(@PathVariable Long id) throws ProductNotFoundException {
    log.info("Ask service for product details for product Id : " + id);
    return requestProduct.getProductByProductId(id);
  }

  @GetMapping("/product/{id}")
  @ApiOperation(value = "This endpoint fetches the products by product id from database")
  public Product getProductById(@PathVariable String id) throws ProductNotFoundException {
    log.info("Ask service for product details for Id : " + id);
    return requestProduct.getProductById(id);
  }

  @GetMapping("/products/price")
  @ApiOperation(value = "This endpoint fetches the products within the price range")
  public List<Product> getProductsWithinPriceRange(
      @RequestParam("priceLowerLimit") Double lowerLimit,
      @RequestParam("priceHigherLimit") Double higherLimit)
      throws ProductNotFoundException {
    log.info("Ask service for products in price range : " + lowerLimit + " and " + higherLimit);
    return requestProduct.getAllProductsWithinPriceRange(lowerLimit, higherLimit);
  }

  @GetMapping("/products/name")
  @ApiOperation(value = "This endpoint fetches the products by product id from database")
  public List<Product> getProductByProductName(@RequestParam String productName)
      throws ProductNotFoundException {
    log.info("Ask service for product details for product name : " + productName);
    return requestProduct.getProductByProductName(productName);
  }

  @GetMapping("/products/category")
  @ApiOperation(value = "This endpoint fetches the products by product id from database")
  public List<Product> getProductByProductCategory(@RequestParam String categoryName)
      throws ProductNotFoundException {
    log.info("Ask service for product details for product name : " + categoryName);
    return requestProduct.getAllProductsByProductCategory(categoryName);
  }

  @PutMapping("/products/update")
  @ApiOperation(value = "This endpoint updates the products by id into database")
  public Product updatedProductById(@RequestBody Product product) throws ProductNotFoundException {
    log.info("Updating the existing product : " + product);
    return requestProduct.updateProductById(product);
  }

  @PostMapping("/products/save")
  @ApiOperation(value = "This endpoint save the products information into database")
  public Product saveProduct(@RequestBody Product product) {
    log.info("Persisting the new product : " + product);
    return requestProduct.saveProduct(product);
  }
}
