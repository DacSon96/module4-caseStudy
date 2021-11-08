package com.codegym.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name ="Image")
public class Img {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String img;

    @ManyToOne
    private User user;
}
