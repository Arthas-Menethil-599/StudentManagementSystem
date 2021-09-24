package com.example.studentmanagementsystem.services;

import com.example.studentmanagementsystem.entities.Course;

import java.util.List;

public interface CourseService {

    Course getCourse(Long id);



    List<Course> getCourses();
}
