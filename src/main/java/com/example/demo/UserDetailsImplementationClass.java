package com.example.demo;

import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsImplementationClass implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity user =userRepo.findByUsername(username);
	      if (user == null) {
	            throw new UsernameNotFoundException("User not found with username: " + username);
	        }
	        return org.springframework.security.core.userdetails.User.builder()
	                .username(user.getUsername())
	                .password(user.getPassword())
	                .authorities(user.getRole())
	                .build();
	    	}

}
