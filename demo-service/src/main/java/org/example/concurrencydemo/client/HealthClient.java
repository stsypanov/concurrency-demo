package org.example.concurrencydemo.client;

import org.example.concurrencydemo.config.InternalFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "healthClient", url = "${downstream.health.url}", configuration = InternalFeignConfiguration.class)
public interface HealthClient {

    @GetMapping
    ResponseEntity<String> checkHealth();
}