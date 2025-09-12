package com.Quiz.Api.service;

import com.Quiz.Api.dto.PopularQuizDto;
import com.Quiz.Api.entities.Attempt;

import java.util.List;

public interface AttemptService {

    List<Attempt> attemptsByQuizId (Integer quizId);

    Double avgScorePerQuiz(Integer quizId);

    PopularQuizDto popularQuiz();
}
