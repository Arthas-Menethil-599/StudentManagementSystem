package com.example.studentmanagementsystem.entities;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "courses")
public class Course extends BaseEntity{

    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Group> groups;

    @ManyToMany(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Teacher> teachers;
}
