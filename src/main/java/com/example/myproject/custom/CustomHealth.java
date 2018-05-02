package com.example.myproject.custom;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomHealth {

    private Map<String, Object> healthDetails;

    @JsonAnyGetter
    public Map<String, Object> getHealthDetails() {
        return this.healthDetails;
    }

    public void setHealthDetails(Map<String, Object> healthDetails) {
        this.healthDetails = healthDetails;
    }
}