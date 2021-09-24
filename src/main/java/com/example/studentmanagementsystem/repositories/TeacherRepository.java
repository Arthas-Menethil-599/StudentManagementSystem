package com.example.studentmanagementsystem.repositories;

import com.example.studentmanagementsystem.entities.Course;
import com.example.studentmanagementsystem.entities.DbUser;
import com.example.studentmanagementsystem.entities.Student;
import com.example.studentmanagementsystem.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Teacher findByDbUser(DbUser dbUser);
}
