package com.example.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint";
    }

    @GetMapping("/user")
    public String userEndpoint() {
        return "This is a user endpoint";
    }

    @GetMapping("/admin")
    public String adminEndpoint() {
        return "This is an admin endpoint";
    }
    
   
}