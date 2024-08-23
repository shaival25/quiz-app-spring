package com.quiz.quizzapp.service;


import com.quiz.quizzapp.dao.QuestionDao;
import com.quiz.quizzapp.dao.QuizDao;
import com.quiz.quizzapp.model.Question;
import com.quiz.quizzapp.model.QuestionWrapper;
import com.quiz.quizzapp.model.Quiz;
import com.quiz.quizzapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        try {
            List<Question> questions = questionDao.findQuestionsForQuiz(category, numQ);
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questions);
            quizDao.save(quiz);
            return new ResponseEntity<>("Quiz Created", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(Integer id) {
        try {
            Optional<Quiz> quiz = quizDao.findById(id);
            List<Question> questionsFromDb = quiz.get().getQuestions();
            List<QuestionWrapper> questionsForUser = new ArrayList<>();
            for (Question q : questionsFromDb) {
                QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
                questionsForUser.add(qw);
            }
            return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Integer> getScore(Integer id, List<Response> responses) {
        int score = 0;
        try {
            Quiz quiz = quizDao.findById(id).get();
            List<Question> questions = quiz.getQuestions();
            int i = 0;
            for (Response response : responses) {
                if (response.getResponse().equals(questions.get(i).getRightAnswer())) {
                    score++;
                }
                i++;
            }
            return new ResponseEntity<>(score, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(score, HttpStatus.BAD_REQUEST);
    }
}
