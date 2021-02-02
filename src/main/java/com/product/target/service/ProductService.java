package com.product.target.service;

import com.product.target.domain.Cart;
import com.product.target.domain.CartProduct;
import com.product.target.domain.Product;
import com.product.target.exception.ProductNotFoundException;
import com.product.target.repository.ProductPersistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.product.target.service.validator.ProductParameterRequestValidator.PARAMETER_REQUEST_VALIDATOR;

@Service
@Slf4j
public class ProductService implements RequestProduct {
  private Map<Product, Integer> productCart = new HashMap<>();
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
    log.info("Fetching products in price range: " + lowerLimit + " and " + higherLimit);
    PARAMETER_REQUEST_VALIDATOR.assertPriceRange(lowerLimit, higherLimit);
    return productPersistence.fetchAllProductsWithinPriceRange(lowerLimit, higherLimit);
  }

  @Override
  public List<Product> getProductByProductName(String productName) throws ProductNotFoundException {
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

  @Override
  public List<Product> getAllProductsByProductCategory(String productCategory)
      throws ProductNotFoundException {
    log.info("Fetching product details from repository for product category : " + productCategory);
    PARAMETER_REQUEST_VALIDATOR.assertParameterStringValue(productCategory);
    return productPersistence.fetchAllProductsByCategory(productCategory);
  }

  @Override
  public Product saveProduct(Product product) {
    log.info("Persisting the product details through repository : " + product);
    return productPersistence.saveProduct(product);
  }

  @Override
  public Product updateProductById(Product updateProduct) throws ProductNotFoundException {
    log.info("Updating the given product details by id : " + updateProduct);
    return productPersistence.updateProduct(updateProduct);
  }

  @Override
  public Map<Product, Integer> addProduct(Long productId) throws ProductNotFoundException {
    log.info("Adding the product into the cart by id : " + productId);
    Product product = productPersistence.fetchProductByProductId(productId);
    if (product == null) throw new ProductNotFoundException(productId);

    if (productCart.containsKey(product))
      productCart.replace(product, productCart.get(product) + 1);
    else productCart.put(product, 1);

    return productCart;
  }

  @Override
  public Map<Product, Integer> removeProduct(Long productId) throws ProductNotFoundException {
    log.info("Removing the product from cart by id : " + productId);
    Product product = productPersistence.fetchProductByProductId(productId);
    if (productCart.containsKey(product)) {
      if (productCart.get(product) > 1) {
        productCart.replace(product, productCart.get(product) - 1);
      } else if (productCart.get(product) == 1) {
        productCart.remove(product);
      } else {
        throw new ProductNotFoundException(productId);
      }
    }
    return productCart;
  }

  @Override
  public Cart checkout() {
    List<CartProduct> cartProductList = getCartProductList();
    BigDecimal cartTotal =
            cartProductList.stream()
                    .map(CartProduct::getTotal)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO);
    Cart cart = Cart.builder().cartProducts(cartProductList).cartTotal(cartTotal).build();
    productCart.clear();
    return cart;
  }

  private List<CartProduct> getCartProductList() {
    List<CartProduct> cartProducts = new ArrayList<>();
    for (Map.Entry<Product, Integer> entry : productCart.entrySet()) {
      cartProducts.add(
              CartProduct.builder()
                      .product(entry.getKey())
                      .quantity(entry.getValue())
                      .total(
                              BigDecimal.valueOf(
                                      entry.getKey().getCurrentPrice().getValue() * entry.getValue()))
                      .build());
    }
    return cartProducts;
  }
}
