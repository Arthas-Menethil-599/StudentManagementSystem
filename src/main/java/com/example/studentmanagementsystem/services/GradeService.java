package com.example.studentmanagementsystem.services;

import com.example.studentmanagementsystem.entities.Course;
import com.example.studentmanagementsystem.entities.Grade;
import com.example.studentmanagementsystem.entities.Student;

public interface GradeService {

    Grade setGrade(Long studentId, Long courseId, Integer grade);

    Integer getAverageGradeForCourse(Course course, Student student);
}
