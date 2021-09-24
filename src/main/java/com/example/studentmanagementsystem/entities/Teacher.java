package com.example.studentmanagementsystem.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "teachers")
public class Teacher extends BaseEntity {

    private String name;

    private String surname;

    @OneToMany
    private List<Course> courses;

    @OneToOne
    private DbUser dbUser;
}
