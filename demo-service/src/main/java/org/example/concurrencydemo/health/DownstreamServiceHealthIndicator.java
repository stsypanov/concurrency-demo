package org.example.concurrencydemo.health;

import lombok.RequiredArgsConstructor;
import org.example.concurrencydemo.client.HealthClient;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class DownstreamServiceHealthIndicator implements HealthIndicator {
    private final HealthClient healthClient;

    @Override
    public Health health() {
        var response = healthClient.checkHealth();
        if (response.getStatusCode().is2xxSuccessful()) {
            return new Health.Builder().up().build();
        }
        return new Health.Builder().down().withDetails(Map.of("response", response)).build();
    }
}
