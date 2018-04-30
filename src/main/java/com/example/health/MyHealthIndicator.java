package com.example.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MyHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        boolean isError = check(); // perform some specific health check
        if (isError) {
            int errorCode = 404;
            return Health.down().withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }

    private boolean check() {
        return false;
    }

}