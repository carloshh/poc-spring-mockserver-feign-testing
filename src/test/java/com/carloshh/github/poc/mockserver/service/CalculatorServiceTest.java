package com.carloshh.github.poc.mockserver.service;

import feign.RetryableException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayName("Integration tests without mock server for calculator-service")
public class CalculatorServiceTest {

    @Autowired
    private CalculatorService calculatorService;

    @Test
    @DisplayName("Without overriding the environment property for the client calculator-service is unknown as expected ")
    public void testCalculatorServiceUnknownHostException() {
        var exception= assertThrows(RetryableException.class, () -> calculatorService.calculate(1, 1));

        assertThat(exception.getCause().getClass()).isEqualTo(UnknownHostException.class);
    }

}
