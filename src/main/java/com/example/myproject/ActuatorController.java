package com.example.myproject;

import com.example.myproject.custom.CustomEndPoint;
import com.example.myproject.health.MyHealthIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActuatorController {

    @Autowired
    private MyHealthIndicator healthCheck;

    @Autowired
    private CustomEndPoint customEndPoint;

    @RequestMapping("/healthInfo")
    public Health healthInfo() {
        return healthCheck.health();
    }

    @RequestMapping("/customEndPoint")
    public List<String> custom() {
        return customEndPoint.invoke();
    }
}