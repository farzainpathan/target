package com.product.target.rest;

import com.product.target.domain.Cart;
import com.product.target.domain.Product;
import com.product.target.domain.CartProduct;
import com.product.target.exception.ProductNotFoundException;
import com.product.target.service.RequestProduct;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1")
@Api(tags = "Product Commands", value = "ProductCommands")
public class ProductCartController {

  private final RequestProduct requestProduct;

  public ProductCartController(RequestProduct requestProduct) {
    this.requestProduct = requestProduct;
  }

  @GetMapping("/product-cart/checkout")
  @ApiOperation(value = "This endpoint adds the cart product for total")
  public Cart checkout() {
    return requestProduct.checkout();
  }

  @PostMapping("/product-cart/add-product/{productId}")
  @ApiOperation(value = "This endpoint adds the product into cart by product id")
  public Map<Product, Integer> addProductToCart(@PathVariable Long productId)
      throws ProductNotFoundException {
    log.info("Ask service to aad the product into cart by id: " + productId);
    return requestProduct.addProduct(productId);
  }

  @PutMapping("/product-cart/remove-product/{productId}")
  @ApiOperation(value = "This endpoint removes the product from cart by product id")
  public Map<Product, Integer> removeProductFromCart(@PathVariable Long productId)
      throws ProductNotFoundException {
    log.info("Ask service to remove the product from cart by id: " + productId);
    return requestProduct.removeProduct(productId);
  }
}
