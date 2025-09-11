package com.Quiz.Api.service;

import com.Quiz.Api.entities.Question;

import java.util.List;

public interface QuestionService {

    Question createQuestion(Question question);

    Question getQuestionById(Integer id);

    List<Question> getAllQuestions();

    Question updateQuestion(Integer id,Question question);

    Question patchQuestion(Integer id, Question question);

    void deleteQuestion(Integer id);

}
