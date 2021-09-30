package com.example.studentmanagementsystem.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "students")
public class Student extends BaseEntity{

    private String name;

    private String surname;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Group group;

    @OneToOne(cascade= CascadeType.REMOVE)
    private DbUser dbUser;
}
