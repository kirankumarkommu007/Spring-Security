package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Securityconfig {
	
	
	//basic security with default user and password ________login with basic or default custom page
	
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	            .authorizeHttpRequests((requests) -> requests
	                .anyRequest().authenticated()
	            )
	            .httpBasic(Customizer.withDefaults());

	        return http.build();
	    }

}
