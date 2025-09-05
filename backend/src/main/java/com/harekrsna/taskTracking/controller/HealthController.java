package com.harekrsna.taskTracking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/check-health")
public class HealthController {

    @GetMapping
    public String checkHealth() {
        return "Healthy";
    }
}
