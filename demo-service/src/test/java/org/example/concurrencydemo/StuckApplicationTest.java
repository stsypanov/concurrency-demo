package org.example.concurrencydemo;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class StuckApplicationTest {

    @Test
    void name() throws InterruptedException {
        var restTemplate = new RestTemplate();
        var latch = new CountDownLatch(1);
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 100; i++) {
                executor.submit(() -> {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    restTemplate.getForEntity("http://localhost:8081/actuator/health", ResponseEntity.class);
                });
            }
            latch.countDown();
            boolean b = executor.awaitTermination(100, TimeUnit.SECONDS);
            assertFalse(b);
        }
    }
}