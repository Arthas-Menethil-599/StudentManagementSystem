package com.example.studentmanagementsystem.services;

import com.example.studentmanagementsystem.entities.Group;

import java.util.List;

public interface GroupService {

    List<Group> getGroups();

    Group getGroup(Long id);

    Group addOrUpdateGroup(Group group);

    void deleteGroup(Long id);
}
