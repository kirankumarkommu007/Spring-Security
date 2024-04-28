package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class Myrest {

	@Autowired
	public StudentRepository a;
	
	@Autowired
	public SecurityContextEntityRepository contextEntityRepository;

	@Autowired
	public PasswordEncoder encoder;

	@GetMapping("/admin/all")
	public List<Student> getAll() {
		return a.findAll();
	}

	@PostMapping("/save")
	public Student savestude(@RequestBody Student student) {
		student.setPassword(encoder.encode(student.getPassword()));
		return a.save(student);

	}
	

	    @GetMapping("/session-data")
	   public List<SecurityContextEntity> Getdata(){
	    	return contextEntityRepository.findAll();
	    }
	

	
}
