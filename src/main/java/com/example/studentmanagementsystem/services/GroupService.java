package com.example.studentmanagementsystem.services;

import com.example.studentmanagementsystem.entities.Group;
import com.example.studentmanagementsystem.entities.Student;

import java.util.List;

public interface GroupService {

    List<Group> getGroups();

    Group getGroup(Long id);
}
