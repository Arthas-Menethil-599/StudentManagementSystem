package com.example.studentmanagementsystem.services;

import com.example.studentmanagementsystem.entities.DbUser;
import com.example.studentmanagementsystem.entities.Student;

import java.util.List;

public interface StudentService {

    Student getStudent(Long id);

    List<Student> getStudents();

    List<Student> getAllStudentsFromGroup(Long groupId);

    Student addOrUpdateStudent(Student student);

    Student findByDbUser(DbUser dbUser);

    void deleteStudent(Long id);


}
