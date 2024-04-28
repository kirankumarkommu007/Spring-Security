package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityContextEntityRepository extends JpaRepository<SecurityContextEntity, String> {
    SecurityContextEntity findBySessionId(String sessionId);
    boolean existsBySessionId(String sessionId);
}