package com.example.studentmanagementsystem.repositories;

import com.example.studentmanagementsystem.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
