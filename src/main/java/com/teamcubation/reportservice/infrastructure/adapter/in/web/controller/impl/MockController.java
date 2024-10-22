package com.teamcubation.reportservice.infrastructure.adapter.in.web.controller.impl;

import com.teamcubation.reportservice.domain.model.user.UserAuthenticated;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/user")
    public String user() {
        return "Only for users with role USER";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Only for users with role ADMIN";
    }

    @GetMapping("/auth")
    public String authenticated() throws Exception {

        UserAuthenticated userAthenticated = null;
        try {
            userAthenticated = (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            //TODO pensar custom exception para este caso
            throw new Exception(e);
        }
        return "Bienvenido " + userAthenticated.getUsername();
    }
}
