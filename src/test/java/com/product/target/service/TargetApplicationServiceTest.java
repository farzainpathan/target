package com.product.target.service;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class TargetApplicationServiceTest {

    @Test
    @Order(1)
    public void shouldStart(@Mock RequestProduct requestProduct) {
        assertThat(requestProduct).isNotNull();
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
