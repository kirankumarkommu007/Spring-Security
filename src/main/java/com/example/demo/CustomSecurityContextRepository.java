package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSecurityContextRepository implements SecurityContextRepository {

    @Autowired
    private SecurityContextEntityRepository securityContextEntityRepository;

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        String sessionId = requestResponseHolder.getRequest().getSession().getId();
        SecurityContextEntity entity = securityContextEntityRepository.findBySessionId(sessionId);
        return (entity != null) ? entity.getSecurityContext() : null;
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        String sessionId = request.getSession().getId();
        SecurityContextEntity entity = securityContextEntityRepository.findBySessionId(sessionId);
        if (entity == null) {
            entity = new SecurityContextEntity();
            entity.setSessionId(sessionId);
        }
        entity.setSecurityContext(context);
        securityContextEntityRepository.save(entity);
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        return securityContextEntityRepository.existsBySessionId(sessionId);
    }
}
