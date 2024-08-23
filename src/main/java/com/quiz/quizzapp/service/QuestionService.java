package com.quiz.quizzapp.service;

import com.quiz.quizzapp.model.Question;
import com.quiz.quizzapp.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {

        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDao.save(question);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Success", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        try {
            questionDao.deleteById(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Question is used in quiz", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
    }
}
