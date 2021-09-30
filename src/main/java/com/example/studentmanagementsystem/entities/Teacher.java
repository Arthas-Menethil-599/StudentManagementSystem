package com.example.studentmanagementsystem.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "teachers")
public class Teacher extends BaseEntity {

    private String name;

    private String surname;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @NotFound(action = NotFoundAction.IGNORE)
    private DbUser dbUser;
}
