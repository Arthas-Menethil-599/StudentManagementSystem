package com.example.studentmanagementsystem.services;

import com.example.studentmanagementsystem.entities.Group;
import com.example.studentmanagementsystem.repositories.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> getGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Group getGroup(Long id) {
        return groupRepository.getById(id);
    }

    @Override
    public Group addOrUpdateGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }
}
