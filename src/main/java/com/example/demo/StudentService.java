package com.example.demo;

public interface StudentService {
    void registerStudent(Student student);
    Student findByUsername1(String username);
    void resetPassword(String username, String newPassword);
    void resetPassword(String username, Long id,String newPassword);

}