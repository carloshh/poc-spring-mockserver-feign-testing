package com.carloshh.github.poc.mockserver.service;

import com.carloshh.github.poc.mockserver.client.CalculatorClient;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    private final CalculatorClient calculatorClient;

    public CalculatorService(CalculatorClient calculatorClient) {
        this.calculatorClient = calculatorClient;
    }

    public Integer calculate(int a, int b) {
        return calculatorClient.calculate(a, b);
    }

}
