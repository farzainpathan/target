package com.product.target.repository;

import com.product.target.bootstrap.TargetApplication;
import com.product.target.domain.Price;
import com.product.target.domain.Product;
import com.product.target.exception.ProductNotFoundException;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.*;

@SpringBootTest(classes = TargetApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TargetApplicationRepositoryTest {

  @Autowired private ProductPersistence productPersistence;

  @Test
  @Order(1)
  @DisplayName("should start the application")
  public void shouldStartApplication() {
    assertThat(productPersistence).isNotNull();
  }

  // ****************************************
  // ***************  GET  ******************
  // ****************************************
  @Test
  @Order(2)
  @DisplayName("should fetch all the product details from database")
  public void shouldFetchAllProductDetails() {
    // Given data from changelogs
    // When
    List<Product> productList = productPersistence.fetchAllProducts();
    // Then
    assertThat(productList)
        .isNotEmpty()
        .isNotNull()
        .hasSize(6)
        .extracting("productId", "name", "currentPrice")
        .contains(
            tuple(
                100L,
                "Canada Dry Ginger Ale - 2 L Bottle",
                Price.builder().value(10.9).currency("USD").build()),
            tuple(
                200L,
                "Pepsi Zero - 15pk/12 fl oz Cans",
                Price.builder().value(20.36).currency("USD").build()),
            tuple(
                300L,
                "Darlah Firwood Table - Christopher Knight Home",
                Price.builder().value(38.39).currency("USD").build()),
            tuple(
                400L,
                "Urban Industrial Farmhouse Metal X Entry Table - Saracina Home",
                Price.builder().value(152.99).currency("USD").build()),
            tuple(
                500L,
                "8pc Foam Paint Brush Variety Pack - Hand Made Modern",
                Price.builder().value(1.49).currency("USD").build()),
            tuple(
                600L,
                "Unfinished Natural Wood Tray Small - Hand Made Modern",
                Price.builder().value(7.99).currency("USD").build()));
  }

  @Test
  @Order(3)
  @DisplayName("should fetch product details when asked by product Id from database ")
  public void shouldFetchProductDetailsByProductId() throws ProductNotFoundException {
    // Given data from changelogs
    // When
    Product productDetailsByProductId = productPersistence.fetchProductById(300L);
    // Then
    assertThat(productDetailsByProductId)
        .isNotNull()
        .extracting("productId", "name", "currentPrice")
        .containsExactly(
            300L,
            "Darlah Firwood Table - Christopher Knight Home",
            Price.builder().value(38.39).currency("USD").build());
  }

  @Test
  @Order(4)
  @DisplayName(
      "should throw exception when asked for product details by product Id not exist in database ")
  public void shouldThrowExceptionWhenProductIdNotExist() throws ProductNotFoundException {
    // Given data from changelogs
    // When and Then
    assertThatThrownBy(() -> productPersistence.fetchProductById(1000L))
        .isInstanceOf(ProductNotFoundException.class)
        .hasMessageContaining("No product found in the database with id: 1000");
  }
  // ****************************************
  // ***************  UPDATE  ***************
  // ****************************************

  // ****************************************
  // ***************  SAVE  *****************
  // ****************************************
}
