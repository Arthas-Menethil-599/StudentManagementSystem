package com.example.studentmanagementsystem.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="db_users")
public class DbUser extends BaseEntity{

    @Column(name = "email", unique = true)
    private String email;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
}
