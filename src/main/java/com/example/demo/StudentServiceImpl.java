package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void registerStudent(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRole("ADMIN");
        studentRepository.save(student);
    }

    @Override
    public Student findByUsername1(String username) {
        return studentRepository.findByUsername(username);
    }

    
    @Override
    public void resetPassword(String username, String newPassword) {
        Student student = studentRepository.findByUsername(username);
        if (student != null) {
            student.setPassword(passwordEncoder.encode(newPassword)); // Encode the new password
            studentRepository.save(student);
        }
    }

	@Override
	public void resetPassword(String username, Long id, String newPassword) {
		  Student student = studentRepository.findByUsernameAndId(username, id);
	        if (student != null) {
	            student.setPassword(passwordEncoder.encode(newPassword)); // Encode the new password
	            studentRepository.save(student);
	        }		
	}
}
