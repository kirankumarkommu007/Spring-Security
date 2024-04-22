package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
	
	//security with custom userdetails and password inline memory storage role based login
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/user").hasRole("USER")  
                .requestMatchers("/greet").permitAll()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
    
    @Bean 
    public UserDetailsService userDetailsService() {
    	UserDetails user=User.builder()
    			.username("kiran")
                .password(passwordEncoder().encode("1234"))
                .roles("USER")
    			.build();
    	
    	
    	UserDetails admin=User.builder()
    			.username("admin")
                .password(passwordEncoder().encode("1234"))
                .roles("ADMIN")
    			.build();
		return new InMemoryUserDetailsManager(user, admin);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
   
}
