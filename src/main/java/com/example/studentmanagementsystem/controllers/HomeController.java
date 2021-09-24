package com.example.studentmanagementsystem.controllers;

import com.example.studentmanagementsystem.entities.Course;
import com.example.studentmanagementsystem.entities.DbUser;
import com.example.studentmanagementsystem.entities.Student;
import com.example.studentmanagementsystem.models.FactModel;
import com.example.studentmanagementsystem.services.GradeService;
import com.example.studentmanagementsystem.services.StudentService;
import com.example.studentmanagementsystem.services.TeacherService;
import com.example.studentmanagementsystem.services.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private final UserService userService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final GradeService gradeService;

    public HomeController(UserService userService, StudentService studentService, TeacherService teacherService, GradeService gradeService) {
        this.userService = userService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.gradeService = gradeService;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/my-students")
    public String myStudents(Model model) {

        var students = studentService.getStudents();

        model.addAttribute("students", students);

        return "my_students";
    }

    @GetMapping("/get-fact")
    public String getFact(Model model, RestTemplate restTemplate) {
        FactModel factModel = restTemplate.getForObject("https://catfact.ninja/fact", FactModel.class);
        model.addAttribute("factModel", factModel);
        return "get_fact";
    }

    @GetMapping(value = "login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "profile")
    public String profile(Model model) {
        var user = getUser();
        var student = studentService.findByDbUser(user);
        var teacher = teacherService.findByDbUser(user);

        if (student != null) {
            model.addAttribute("studentOrTeacher", student);
        } else if (teacher != null) {
            model.addAttribute("studentOrTeacher", teacher);
        } else {
            return "redirect:/profile?error=1";
        }

        return "profile";
    }

    @ModelAttribute
    public void addUser(Model model) {
        model.addAttribute("user", getUser());
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
