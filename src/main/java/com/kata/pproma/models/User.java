package com.kata.pproma.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public User() {}

    public User(String firstName, String secondName, Integer age,
                String email, String password) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.email = email;
        this.password = password;
    }
}
