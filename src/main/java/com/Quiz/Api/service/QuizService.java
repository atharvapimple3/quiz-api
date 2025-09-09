package com.Quiz.Api.service;

import com.Quiz.Api.entities.Quiz;

import java.util.List;

public interface QuizService {

    Quiz createQuiz(Quiz quiz);

    Quiz updateQuiz(Integer id, Quiz quiz);

    Quiz patchQuiz(Integer id, Quiz quiz);

    List<Quiz> getAllQuiz();

    void deleteQuiz(Integer id);

    Quiz getQuizById(Integer id);

    void restoreById(Integer id);

}
