package com.product.target.service;

import com.product.target.domain.Price;
import com.product.target.domain.Product;
import com.product.target.exception.ProductNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class TargetApplicationServiceTest {

  @Test
  @Order(1)
  @DisplayName("should start the application")
  public void shouldStart(@Mock RequestProduct requestProduct) {
    assertThat(requestProduct).isNotNull();
  }
  // ****************************************
  // ***************  GET  ******************
  // ****************************************
  @Test
  @Order(2)
  @DisplayName("should get the product details from repository")
  public void shouldGetTheProductDetailsFromRepository(@Mock RequestProduct requestProduct) {
    // Given
    when(requestProduct.getAllProducts()).thenReturn(mockProductList());
    // When
    List<Product> productList = requestProduct.getAllProducts();
    // Then
    assertThat(productList)
        .isNotNull()
        .isNotEmpty()
        .hasSize(2)
        .extracting("id", "productId", "name", "currentPrice")
        .containsExactly(
            tuple(
                mockProductList().get(0).getId(),
                mockProductList().get(0).getProductId(),
                mockProductList().get(0).getName(),
                mockProductList().get(0).getCurrentPrice()),
            tuple(
                mockProductList().get(1).getId(),
                mockProductList().get(1).getProductId(),
                mockProductList().get(1).getName(),
                mockProductList().get(1).getCurrentPrice()));

    verify(requestProduct, times(1)).getAllProducts();
  }

  @Test
  @Order(3)
  @DisplayName("should get the product details by product id from repository")
  public void shouldGetTheProductDetailsByProductIdFromRepository(
      @Mock RequestProduct requestProduct) throws ProductNotFoundException {
    // Given
    when(requestProduct.getProductByProductId(anyLong())).thenReturn(mockProduct());
    // When
    Product productDetails = requestProduct.getProductByProductId(500L);
    // Then
    assertThat(productDetails)
        .isNotNull()
        .extracting("id", "productId", "name", "currentPrice")
        .containsExactly(
            mockProduct().getId(),
            mockProduct().getProductId(),
            mockProduct().getName(),
            mockProduct().getCurrentPrice());

    verify(requestProduct, times(1)).getProductByProductId(500L);
  }

  // ****************************************
  // ***************  UPDATE  ***************
  // ****************************************

  // ****************************************
  // ***************  SAVE  *****************
  // ****************************************

  // ****************************************
  // ***************  HELPER  *****************
  // ****************************************
  private List<Product> mockProductList() {
    return List.of(
        Product.builder()
            .id("600ca5eeaf973673acd7fd0d")
            .productId(100L)
            .name("Canada Dry Ginger Ale - 2 L Bottle")
            .currentPrice(Price.builder().value(10.9).currency("USD").build())
            .build(),
        Product.builder()
            .id("600ca5eeaf973673acd7fd0e")
            .productId(200L)
            .name("Pepsi Zero - 15pk/12 fl oz Cans")
            .currentPrice(Price.builder().value(20.36).currency("USD").build())
            .build());
  }

  private Product mockProduct() {
    return Product.builder()
        .id("600ca5eeaf973673acd7fd0d")
        .productId(500L)
        .name("Canada Dry Ginger Ale - 2 L Bottle")
        .currentPrice(Price.builder().value(10.9).currency("USD").build())
        .build();
  }
}
