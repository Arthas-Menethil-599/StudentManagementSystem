package com.example.studentmanagementsystem.services;

import com.example.studentmanagementsystem.entities.Course;
import com.example.studentmanagementsystem.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course getCourse(Long id) {
        return courseRepository.getById(id);
    }

    @Override
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }
}
