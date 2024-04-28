package com.example.demo;

import org.springframework.security.core.context.SecurityContext;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "session")
public class SecurityContextEntity {

    @Id
    private String sessionId;

    @Lob
    private SecurityContext securityContext;

    // Getters and setters
}
