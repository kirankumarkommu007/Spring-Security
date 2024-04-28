package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {


    @Autowired
    private StudentService studentService; 

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               RedirectAttributes redirectAttributes) {
        if (studentService.findByUsername1(username) != null) {
            redirectAttributes.addFlashAttribute("error", "Username already exists!");
            return "redirect:/register";
        }

        String encodedPassword = passwordEncoder.encode(password);
        Student student = new Student();
        student.setUsername(username);
        student.setPassword(encodedPassword);
        studentService.registerStudent(student);
        return "redirect:/login";
    }

    @GetMapping("/resetPassword")
    public String showResetPasswordForm() {
        return "resetPassword";
    }
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("username") String username, @RequestParam("id") Long id,@RequestParam("password") String newPassword) {
        studentService.resetPassword(username,id, newPassword); 
        return "redirect:/login"; 
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin_dashboard";
    }

    @GetMapping("/user/dashboard")
    public String userDashboard() {
        return "user_dashboard";
    }

 
}
