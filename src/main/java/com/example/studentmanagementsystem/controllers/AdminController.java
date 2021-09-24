package com.example.studentmanagementsystem.controllers;

import com.example.studentmanagementsystem.entities.*;
import com.example.studentmanagementsystem.repositories.RoleRepository;
import com.example.studentmanagementsystem.services.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin-panel")
public class AdminController {

    private final StudentService studentService;
    private final UserService userService;
    private final GroupService groupService;
    private final RoleRepository roleRepository;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private DbUser dbUser;

    public AdminController(StudentService studentService, UserService userService, GroupService groupService, RoleRepository roleRepository, TeacherService teacherService, CourseService courseService) {
        this.studentService = studentService;
        this.userService = userService;
        this.groupService = groupService;
        this.roleRepository = roleRepository;
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String index() {
        return "admin/index";
    }

    @GetMapping("/students-control")
    public String studentsControl(Model model) {
        model.addAttribute("students", studentService.getStudents());
        model.addAttribute("groups", groupService.getGroups());
        return "admin/students_control";
    }

    @PostMapping("/update-student")
    public String updateStudent(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") String email) {
        var student = studentService.getStudent(id);

        student.setName(name);
        student.setSurname(surname);
        student.getDbUser().setEmail(email);
        studentService.addOrUpdateStudent(student);
        return "redirect:/admin-panel/";
    }

    @PostMapping("/add-student")
    public String addStudent(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("group_id") Long groupId,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirm_password") String confirmPassword) {

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.save(new Role("Student", "ROLE_STUDENT")));
        DbUser dbUser = null;

        var group = groupService.getGroup(groupId);

        if(password.equals(confirmPassword)) {
            dbUser = userService.addUser(new DbUser(email, password, roles));
        } else {
            return "redirect/admin-panel/add-student?error=1";
        }

        if(dbUser != null) {
            studentService.addOrUpdateStudent(new Student(name,surname,group,dbUser));
            return "redirect:/admin-panel/";
        } else {
            return "redirect/admin-panel/add-student?error=2";
        }
    }

    @PostMapping("/delete-student")
    public String deleteStudent(@RequestParam("id") Long id) {
        studentService.deleteStudent(id);
        return "redirect:/admin-panel/";
    }

    @GetMapping("/teachers-control")
    public String teachersControl(Model model) {
        model.addAttribute("teachers", teacherService.getTeachers());
        model.addAttribute("courses", courseService.getCourses());
        return "admin/teachers_control";
    }

    @PostMapping("/update-teacher")
    public String updateTeacher(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") String email) {
        var teacher = teacherService.getTeacher(id);

        teacher.setName(name);
        teacher.setSurname(surname);
        teacher.getDbUser().setEmail(email);
        teacherService.addOrUpdateTeacher(teacher);
        return "redirect:/admin-panel/";
    }

    @PostMapping("/add-teacher")
    public String addTeacher(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("course_id") Long courseId,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirm_password") String confirmPassword) {

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.save(new Role("ROLE_TEACHER", "Teacher")));
        DbUser dbUser = null;

        var course = courseService.getCourse(courseId);

        if(password.equals(confirmPassword)) {
            dbUser = userService.addUser(new DbUser(email, password, roles));
        } else {
            return "redirect/admin-panel/add-teacher?error=1";
        }

        List<Course> courses = new ArrayList<>();
        courses.add(course);

        if(dbUser != null) {
            teacherService.addOrUpdateTeacher(new Teacher(name,surname,courses,dbUser));
            return "redirect:/admin-panel/";
        } else {
            return "redirect/admin-panel/add-teacher?error=2";
        }
    }

    @PostMapping("/delete-teacher")
    public String deleteTeacher(@RequestParam("id") Long id) {
        teacherService.deleteTeacher(id);
        return "redirect:/admin-panel/";
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
