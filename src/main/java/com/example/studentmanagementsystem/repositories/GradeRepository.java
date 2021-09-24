package com.example.studentmanagementsystem.repositories;

import com.example.studentmanagementsystem.entities.Course;
import com.example.studentmanagementsystem.entities.Grade;
import com.example.studentmanagementsystem.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
}
