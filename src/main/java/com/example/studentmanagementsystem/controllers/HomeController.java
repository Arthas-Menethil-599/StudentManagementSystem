package com.example.studentmanagementsystem.controllers;

import com.example.studentmanagementsystem.entities.Course;
import com.example.studentmanagementsystem.entities.DbUser;
import com.example.studentmanagementsystem.entities.Group;
import com.example.studentmanagementsystem.entities.Student;
import com.example.studentmanagementsystem.entities.Teacher;
import com.example.studentmanagementsystem.models.FactModel;
import com.example.studentmanagementsystem.services.CourseService;
import com.example.studentmanagementsystem.services.GradeService;
import com.example.studentmanagementsystem.services.GroupService;
import com.example.studentmanagementsystem.services.StudentService;
import com.example.studentmanagementsystem.services.TeacherService;
import com.example.studentmanagementsystem.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class HomeController {

    private final UserService userService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final GradeService gradeService;
    private final CourseService courseService;
    private final GroupService groupService;

    @Value("${cat.fact.url}")
    private String catFactUrl;

    public HomeController(UserService userService, StudentService studentService, TeacherService teacherService, GradeService gradeService, CourseService courseService, GroupService groupService) {
        this.userService = userService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.gradeService = gradeService;
        this.courseService = courseService;
        this.groupService = groupService;
    }

    @GetMapping("/")
    public String index(Model model) {
        var user = getUser();
        var student = studentService.findByDbUser(user);
        var teacher = teacherService.findByDbUser(user);

        if (student != null) {
            model.addAttribute("student", student);
        } else if (teacher != null) {
            model.addAttribute("teacher", teacher);
        }
        return "index";
    }

    @GetMapping("/my-students")
    public String myStudents(Model model) {

        DbUser dbUser = getUser();
        Teacher teacher = teacherService.findByDbUser(dbUser);

        List<Course> courses = courseService.getCourses();
        List<Student> students = studentService.getStudents();
        var myGroups = new ArrayList<Group>();

        var myCourses = new ArrayList<Course>();

        for(Course item : courses) {
            if (item.getTeachers().contains(teacher)) {
                myCourses.add(item);
                myGroups.addAll(item.getGroups());
            }
        }

        var myStudents = new ArrayList<Student>();

        for(Student student : students) {
            for(Group group : myGroups) {
                if (student.getGroup().equals(group)) {
                    myStudents.add(student);
                }
            }
        }

        model.addAttribute("courses", myCourses);
        model.addAttribute("students", myStudents);

        return "my_students";
    }

    @PostMapping("/set-grade")
    public String setGrade(@RequestParam("grade") Integer grade,
                           @RequestParam("student_id") Long studentId,
                           @RequestParam("course_id") Long courseId) {
        gradeService.setGrade(studentId, courseId, grade);
        return "redirect:/my-students";
    }

    @GetMapping("/get-fact")
    public String getFact(Model model, RestTemplate restTemplate) {
        FactModel factModel = restTemplate.getForObject(catFactUrl, FactModel.class);
        model.addAttribute("factModel", factModel);
        return "get_fact";
    }

    @GetMapping(value = "login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "profile")
    public String profile(Model model) {
        DbUser user = getUser();
        var student = studentService.findByDbUser(user);
        var teacher = teacherService.findByDbUser(user);

        if (student != null) {
            model.addAttribute("student", student);
        } else if (teacher != null) {
            model.addAttribute("teacher", teacher);
        }
        return "profile";
    }

    @GetMapping("/my-courses")
    public String myCourses(Model model) {
        DbUser dbUser = getUser();
        Student student = studentService.findByDbUser(dbUser);

        var courses = courseService.getCourses();

        var myCourses = new ArrayList<Course>();

        for (Course course : courses) {
            if ((course.getGroups().contains(student.getGroup()))) {
                myCourses.add(course);
            }
        }

        model.addAttribute("courses", myCourses);
        return "my_courses";
    }

    @GetMapping("/course/{id}")
    public String myCourse(Model model, @PathVariable("id") Long courseId) {
        DbUser dbUser = getUser();
        Student student = studentService.findByDbUser(dbUser);

        var course = courseService.getCourse(courseId);

        var myAverageGrade = gradeService.getAverageGradeForCourse(course, student);

        model.addAttribute("average", myAverageGrade);
        return "course";
    }

    @ModelAttribute
    public void addUser(Model model) {
        var user = getUser();
        model.addAttribute("user", user);
    }

    private DbUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User securityUser = (User) authentication.getPrincipal();
            DbUser user = userService.getUser(securityUser.getUsername());
            return user;
        }

        return null;

    }
}
