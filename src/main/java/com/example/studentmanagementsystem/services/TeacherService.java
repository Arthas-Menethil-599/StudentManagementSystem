package com.example.studentmanagementsystem.services;

import com.example.studentmanagementsystem.entities.DbUser;
import com.example.studentmanagementsystem.entities.Teacher;

import java.util.List;

public interface TeacherService {

    Teacher addOrUpdateTeacher(Teacher teacher);

    Teacher getTeacher(Long id);

    List<Teacher> getTeachers();

    Teacher findByDbUser(DbUser dbUser);

    void deleteTeacher(Long id);

}
