package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer.SessionFixationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private StudentDetailsServiceImpl studentDetailsService;
	
	@Autowired
    private CustomSecurityContextRepository customSecurityContextRepository;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.requestMatchers("/admin/**").hasRole("ADMIN")
				.requestMatchers("/user/**").hasRole("USER").anyRequest().permitAll())

				.formLogin(
						(form) -> form.loginPage("/login").usernameParameter("username").passwordParameter("password")

								.successHandler((request, response, authentication) -> {
									if (authentication.getAuthorities().stream()
											.anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
										response.sendRedirect("/admin/dashboard");
									} else if (authentication.getAuthorities().stream()
											.anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
										response.sendRedirect("/user/dashboard");
									}
								}).permitAll())

				.logout((logout) -> logout
						.invalidateHttpSession(true)
						.clearAuthentication(true)
//			            .deleteCookies("JSESSIONID")
	                    .addLogoutHandler(new CookieClearingLogoutHandler("JSESSIONID")) // Clear specific cookie
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?logout").permitAll())
				
				.sessionManagement((session)->session.maximumSessions(1).maxSessionsPreventsLogin(true))
				
//				.sessionManagement(session -> session.invalidSessionUrl("/invalidSession"))
//						 .sessionManagement((sessionManagement) -> sessionManagement
//					                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//					                .sessionFixation(SessionFixationConfigurer::changeSessionId))


	            .securityContext().securityContextRepository(customSecurityContextRepository);
			
				
     
				
				;
		

		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.httpBasic(Customizer.withDefaults());
		

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
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	
	

}
