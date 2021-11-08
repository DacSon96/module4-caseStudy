package com.codegym.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;


    @Column(columnDefinition = "varchar(255) default 'Your bio ...'")
    private String intro;

    @Column(columnDefinition = "varchar(255) default 'Your work ...'")
    private String work;

    @Column(columnDefinition = "varchar(255) default 'Your address ...'")
    private String address;

    @Column(columnDefinition = "varchar(255) default '/images/default-avatar.png'")
    private String avatar;

    @Column(columnDefinition = "varchar(255) default '/images/default-cover.png'")
    private String cover;
}