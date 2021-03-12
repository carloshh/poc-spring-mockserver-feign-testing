package com.carloshh.github.poc.mockserver.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "calculator-service-client", url = "${calculator-service-client-url:calculator-service}")
public interface CalculatorClient {

    @GetMapping("/add")
    Integer calculate(@RequestParam("a") int a, @RequestParam("b") int b);

}
