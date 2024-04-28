package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class myController {
	
	 @GetMapping("/login")
	    public String log() {
	    	return "login";
	    }
	 
	 @GetMapping("/users")
	    public String Userlog() {
	    	return "user";
	    }

}
