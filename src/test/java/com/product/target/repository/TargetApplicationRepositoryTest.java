package com.product.target.repository;

import com.product.target.bootstrap.TargetApplication;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TargetApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TargetApplicationRepositoryTest {

    @Autowired private ProductPersistence productPersistence;

    @Test
    @Order(1)
    public void shouldStartApplication() {
       assertThat(productPersistence).isNotNull();
    }

    // ****************************************
    // ***************  GET  ******************
    // ****************************************

    // ****************************************
    // ***************  UPDATE  ***************
    // ****************************************

    // ****************************************
    // ***************  SAVE  *****************
    // ****************************************
}
