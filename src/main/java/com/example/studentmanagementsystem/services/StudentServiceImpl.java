package com.example.studentmanagementsystem.services;

import com.example.studentmanagementsystem.entities.DbUser;
import com.example.studentmanagementsystem.entities.Group;
import com.example.studentmanagementsystem.entities.Student;
import com.example.studentmanagementsystem.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student getStudent(Long id) {
        return studentRepository.getById(id);
    }

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> getAllStudentsFromGroup(Long groupId) {
        return studentRepository.findAllByGroupId(groupId);
    }

    @Override
    public Student addOrUpdateStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student findByDbUser(DbUser dbUser) {
        return studentRepository.findByDbUser(dbUser);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
