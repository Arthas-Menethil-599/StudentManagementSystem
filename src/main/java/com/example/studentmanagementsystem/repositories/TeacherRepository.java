package com.example.studentmanagementsystem.repositories;

import com.example.studentmanagementsystem.entities.DbUser;
import com.example.studentmanagementsystem.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Teacher findByDbUser(DbUser dbUser);
}
