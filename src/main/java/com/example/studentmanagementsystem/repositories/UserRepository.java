package com.example.studentmanagementsystem.repositories;

import com.example.studentmanagementsystem.entities.DbUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<DbUser, Long> {
    DbUser findUserByEmail(String email);
}
