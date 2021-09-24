package com.example.studentmanagementsystem.services;

import java.util.List;
import com.example.studentmanagementsystem.entities.Course;
import com.example.studentmanagementsystem.entities.DbUser;
import com.example.studentmanagementsystem.entities.Student;
import com.example.studentmanagementsystem.entities.Teacher;

public interface TeacherService {

    Teacher addOrUpdateTeacher(Teacher teacher);

    Teacher getTeacher(Long id);

    List<Teacher> getTeachers();

    Teacher findByDbUser(DbUser dbUser);

    void deleteTeacher(Long id);

}
