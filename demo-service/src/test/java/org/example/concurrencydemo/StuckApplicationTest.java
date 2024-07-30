package org.example.concurrencydemo;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StuckApplicationTest {

    @Test
    void run() {
        var restTemplate = new RestTemplate();
        var latch = new CountDownLatch(1);
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        try (executor) {
            for (int i = 0; i < 100; i++) {
                executor.submit(() -> {
                    waitOn(latch);
                    assertNotNull(restTemplate.getForEntity("http://localhost:8081/actuator/health", ResponseEntity.class));
                });
            }
            latch.countDown();
        }
        assertTrue(executor.isTerminated());
    }

    private static void waitOn(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
