package com.product.target.service;

import com.product.target.exception.ProductNotFoundException;
import com.product.target.repository.ProductPersistence;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

// @RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TargetApplicationValidatorTest {

  /*@Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }*/
  // ****************************************
  // ***************  GET  ******************
  // ****************************************
  @Test
  @Order(1)
  @DisplayName("should throw exception for invalid higher limit")
  public void shouldThrowExceptionForInvalidHigherLimit(
      @Mock ProductPersistence productPersistence) {
    // Given
    RequestProduct requestProduct = Mockito.spy(new ProductService(productPersistence));
    // When and Then
    assertThatThrownBy(() -> requestProduct.getAllProductsWithinPriceRange(0.0, 0.0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Higher limit value cannot be zero");
  }

  @Test
  @Order(2)
  @DisplayName("should throw exception for null higher limit")
  public void shouldThrowExceptionForNullHigherLimit(@Mock ProductPersistence productPersistence) {
    // Given
    RequestProduct requestProduct = Mockito.spy(new ProductService(productPersistence));
    // When and Then
    assertThatThrownBy(() -> requestProduct.getAllProductsWithinPriceRange(20.0, 10.0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Lower limit cannot be greater/equal to higher limit");
  }

  @Test
  @Order(3)
  @DisplayName("should throw exception for negative higher limit")
  public void shouldThrowExceptionForNegativeHigherLimit(
      @Mock ProductPersistence productPersistence) {
    // Given
    RequestProduct requestProduct = Mockito.spy(new ProductService(productPersistence));
    // When and Then
    assertThatThrownBy(() -> requestProduct.getAllProductsWithinPriceRange(10.0, -10.0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("higherLimit value cannot be negative Lower limit cannot be greater/equal to higher limit ");
  }

  @Test
  @Order(4)
  @DisplayName("should throw exception for negative lower limit")
  public void shouldThrowExceptionForNegativeLowerLimit(
      @Mock ProductPersistence productPersistence) {
    // Given
    RequestProduct requestProduct = Mockito.spy(new ProductService(productPersistence));
    // When and Then
    assertThatThrownBy(() -> requestProduct.getAllProductsWithinPriceRange(-0.23, 10.0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("lowerLimit value cannot be negative");
  }

  @Test
  @Order(5)
  @DisplayName("should throw exception for Empty product name")
  public void shouldThrowExceptionForProductEmpty(@Mock ProductPersistence productPersistence) {
    // Given
    RequestProduct requestProduct = Mockito.spy(new ProductService(productPersistence));
    // When and Then
    assertThatThrownBy(() -> requestProduct.getProductByProductName(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("The requesting parameter value cannot be null or Empty");
  }

  @Test
  @Order(6)
  @DisplayName("should throw exception for Empty product id")
  public void shouldThrowExceptionForIdEmpty(@Mock ProductPersistence productPersistence) {
    // Given
    RequestProduct requestProduct = Mockito.spy(new ProductService(productPersistence));
    // When and Then
    assertThatThrownBy(() -> requestProduct.getProductById(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("The requesting parameter value cannot be null or Empty");
  }

  @Test
  @Order(7)
  @DisplayName("should throw exception for Empty product category")
  public void shouldThrowExceptionForProductCategoryEmpty(@Mock ProductPersistence productPersistence) {
    // Given
    RequestProduct requestProduct = Mockito.spy(new ProductService(productPersistence));
    // When and Then
    assertThatThrownBy(() -> requestProduct.getAllProductsByProductCategory(""))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("The requesting parameter value cannot be null or Empty");
  }
}
