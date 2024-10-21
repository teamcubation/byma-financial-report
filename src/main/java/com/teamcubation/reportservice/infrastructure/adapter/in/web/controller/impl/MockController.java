package com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.impl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mock")
public class MockController {

    @GetMapping("/public")
    public String mock() {
        return "mock";
    }

    @GetMapping("/auth")
    public String authenticated() {
        return "error";
    }
}
