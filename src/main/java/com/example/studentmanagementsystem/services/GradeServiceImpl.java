package com.example.studentmanagementsystem.services;

import com.example.studentmanagementsystem.entities.Course;
import com.example.studentmanagementsystem.entities.Grade;
import com.example.studentmanagementsystem.entities.Student;
import com.example.studentmanagementsystem.repositories.CourseRepository;
import com.example.studentmanagementsystem.repositories.GradeRepository;
import com.example.studentmanagementsystem.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    @Override
    public Grade setGrade(Long studentId, Long courseId, Integer grade) {
        if(grade <= 0) {
            grade = 1;
        } else if (grade > 12) {
            grade = 12;
        }
        var student = studentRepository.getById(studentId);
        var course = courseRepository.getById(courseId);
        return gradeRepository.save(new Grade(student, course, grade));
    }

    @Override
    public Integer getAverageGradeForCourse(Course course, Student student) {
        List<Grade> grades = gradeRepository.findAllByCourseAndStudent(course, student);

        if (!(Objects.isNull(grades) || grades.isEmpty())) {
            Integer result = 0;

            for (Grade grade : grades) {
                result += grade.getGrade();
            }

            return result / grades.size();
        }
        var minimalGrade = 1;
        return minimalGrade;
    }
}
