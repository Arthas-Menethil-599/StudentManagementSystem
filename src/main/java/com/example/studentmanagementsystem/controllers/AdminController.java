package com.example.studentmanagementsystem.controllers;

import com.example.studentmanagementsystem.config.StaticConfig;
import com.example.studentmanagementsystem.entities.Course;
import com.example.studentmanagementsystem.entities.DbUser;
import com.example.studentmanagementsystem.entities.Group;
import com.example.studentmanagementsystem.entities.Role;
import com.example.studentmanagementsystem.entities.Student;
import com.example.studentmanagementsystem.entities.Teacher;
import com.example.studentmanagementsystem.repositories.RoleRepository;
import com.example.studentmanagementsystem.services.CourseService;
import com.example.studentmanagementsystem.services.GroupService;
import com.example.studentmanagementsystem.services.StudentService;
import com.example.studentmanagementsystem.services.TeacherService;
import com.example.studentmanagementsystem.services.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "redirect:/admin-panel/students-control";
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
        roles.add(roleRepository.save(roleRepository.getById(StaticConfig.ROLE_STUDENT)));
        DbUser dbUser;

        var group = groupService.getGroup(groupId);

        if(password.equals(confirmPassword)) {
            dbUser = userService.addUser(new DbUser(email, password, roles));
        } else {
            return "redirect/admin-panel/add-student?error=1";
        }

        if(dbUser != null) {
            studentService.addOrUpdateStudent(new Student(name,surname,group,dbUser));
            return "redirect:/admin-panel/students-control";
        } else {
            return "redirect/admin-panel/add-student?error=2";
        }
    }

    @DeleteMapping("/delete-student/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return "redirect:/admin-panel/students-control";
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
        return "redirect:/admin-panel/teachers-control";
    }

    @PostMapping("/add-teacher")
    public String addTeacher(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirm_password") String confirmPassword) {

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.save(roleRepository.getById(StaticConfig.ROLE_TEACHER)));
        DbUser dbUser;

        if(password.equals(confirmPassword)) {
            dbUser = userService.addUser(new DbUser(email, password, roles));
        } else {
            return "redirect/admin-panel/add-teacher?error=1";
        }

        if(dbUser != null) {
            teacherService.addOrUpdateTeacher(new Teacher(name,surname,dbUser));
            return "redirect:/admin-panel/teachers-control";
        } else {
            return "redirect/admin-panel/add-teacher?error=2";
        }
    }

    @DeleteMapping("/delete-teacher/{id}")
    public String deleteTeacher(@PathVariable("id") Long id) {
        teacherService.deleteTeacher(id);
        return "redirect:/admin-panel/teachers-control";
    }

    @GetMapping("/groups-control")
    public String groupsControl(Model model) {
        model.addAttribute("groups", groupService.getGroups());
        return "admin/groups_control";
    }

    @PostMapping("/update-group")
    public String updateGroup(
            @RequestParam("id") Long id,
            @RequestParam("name") String name) {
        var group = groupService.getGroup(id);

        group.setName(name);
        groupService.addOrUpdateGroup(group);
        return "redirect:/admin-panel/groups-control";
    }

    @PostMapping("/add-group")
    public String addGroup(
            @RequestParam("name") String name) {
        groupService.addOrUpdateGroup(new Group(name));
        return "redirect:/admin-panel/groups-control";
    }

    @DeleteMapping({"/delete-group/{id}"})
    public String deleteGroup(@PathVariable("id") Long id) {
        groupService.deleteGroup(id);
        return "redirect:/admin-panel/groups-control";
    }

    @GetMapping("/courses-control")
    public String coursesControl(Model model) {
        model.addAttribute("courses", courseService.getCourses());
        model.addAttribute("teachers", teacherService.getTeachers());
        model.addAttribute("groups", groupService.getGroups());
        return "admin/courses_control";
    }

    @PostMapping("/update-course")
    public String updateCourse(
            @RequestParam("id") Long id,
            @RequestParam("name") String name) {

        var course = courseService.getCourse(id);
        course.setName(name);
        courseService.addOrUpdateCourse(course);
        return "redirect:/admin-panel/courses-control";
    }

    @PostMapping("/add-course")
    public String addCourse(
            @RequestParam("name") String name,
            @RequestParam("group_id") Long group_id,
            @RequestParam("teacher_id") Long teacher_id) {

        List<Group> groups = new ArrayList<>();
        var group = groupService.getGroup(group_id);
        groups.add(group);

        List<Teacher> teachers = new ArrayList<>();
        var teacher = teacherService.getTeacher(teacher_id);
        teachers.add(teacher);

        courseService.addOrUpdateCourse(new Course(name, groups, teachers));
        return "redirect:/admin-panel/courses-control";
    }

    @DeleteMapping({"/delete-course/{id}"})
    public String deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourse(id);
        return "redirect:/admin-panel/courses-control";
    }

    @ModelAttribute
    public void addUser(Model model) {
        model.addAttribute("user", getUser());
    }

    private DbUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User securityUser = (User) authentication.getPrincipal();
            var user = userService.getUser(securityUser.getUsername());
            return user;
        }

        return null;

    }

}
