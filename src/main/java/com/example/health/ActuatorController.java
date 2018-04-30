package com.example.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActuatorController {

    @Autowired
    private MyHealthIndicator healthCheck;

    @RequestMapping("/healthInfo")
    public Health healthInfo() {
        return healthCheck.health();
    }
}