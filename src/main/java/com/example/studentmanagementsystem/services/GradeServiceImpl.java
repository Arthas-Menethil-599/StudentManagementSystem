package com.example.studentmanagementsystem.services;

import com.example.studentmanagementsystem.entities.Course;
import com.example.studentmanagementsystem.entities.Grade;
import com.example.studentmanagementsystem.entities.Student;
import com.example.studentmanagementsystem.repositories.CourseRepository;
import com.example.studentmanagementsystem.repositories.GradeRepository;
import com.example.studentmanagementsystem.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public GradeServiceImpl(GradeRepository gradeRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    Grade setGrade(Long studentId, Long courseId, Integer grade) {
        if(grade <= 0) {
            grade = 1;
        } else if (grade > 12) {
            grade = 12;
        }
        var student = studentRepository.getById(studentId);
        var course = courseRepository.getById(courseId);
        return gradeRepository.save(new Grade(student, course, grade));
    }
}
