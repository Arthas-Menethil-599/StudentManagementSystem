package com.example.studentmanagementsystem.services;

import com.example.studentmanagementsystem.entities.DbUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    DbUser getUser(String email);

    DbUser addUser(DbUser dbUser);

    DbUser updateUser(DbUser dbUser);

    void deleteUser(Long id);

    List<DbUser> getUsers();
}
