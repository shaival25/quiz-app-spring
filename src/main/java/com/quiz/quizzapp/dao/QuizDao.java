package com.quiz.quizzapp.dao;

import com.quiz.quizzapp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz, Integer> {
}
