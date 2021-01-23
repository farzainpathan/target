package com.product.target.repository;

import com.product.target.bootstrap.TargetApplication;
import com.product.target.domain.Price;
import com.product.target.domain.Product;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
  // ****************************************
  // ***************  UPDATE  ***************
  // ****************************************

  // ****************************************
  // ***************  SAVE  *****************
  // ****************************************
}
