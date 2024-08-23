package com.quiz.quizzapp.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table
@Data
@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;

    @ManyToMany
    private List<Question> questions;
}
