package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private UserDetailsImplementationClass studentDetailsService;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/admin").hasAuthority("ADMIN")
                    .requestMatchers("/user").hasAnyRole("USER")
                    .requestMatchers("/login").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(formLogin ->
                formLogin
                    .loginPage("/login")
                    .successHandler((request, response, authentication) -> {
                        if (authentication.getAuthorities().stream()
                                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"))) {
                            response.sendRedirect("/admin");
                        } else if (authentication.getAuthorities().stream()
                                .anyMatch(auth -> auth.getAuthority().equals("USER"))) {
                            response.sendRedirect("/user");
                        } else {
                            response.sendRedirect("/public");
                        }
                    })
            )
            .logout(logout -> logout.logoutUrl("/logout"))
            .csrf(csrf -> csrf.disable())
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    
    @Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(studentDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
