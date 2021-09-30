package com.example.studentmanagementsystem.services;

import com.example.studentmanagementsystem.entities.DbUser;
import com.example.studentmanagementsystem.entities.Teacher;
import com.example.studentmanagementsystem.repositories.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Teacher addOrUpdateTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher getTeacher(Long id) {
        return teacherRepository.getById(id);
    }

    @Override
    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher findByDbUser(DbUser dbUser) {
        return teacherRepository.findByDbUser(dbUser);
    }

    @Override
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

//    @Override
//    public List<Course> getCourses(Teacher teacher) {
//        return teacherRepository.
//    }
}
