package com.Quiz.Api.service;

import com.Quiz.Api.dto.LeaderboardDto;
import com.Quiz.Api.dto.QuizSubmissionDto;
import com.Quiz.Api.entities.Question;
import com.Quiz.Api.entities.Quiz;

import java.util.List;
import java.util.Map;

public interface QuizService {

    Quiz createQuiz(Quiz quiz);

    Quiz updateQuiz(Integer id, Quiz quiz);

    Quiz patchQuiz(Integer id, Quiz quiz);

    List<Quiz> getAllQuiz();

    void deleteQuiz(Integer id);

    Quiz getQuizById(Integer id);

    void restoreById(Integer id);

    Question getOneQuestion(List<Integer> randomQuestionsByIndex, Integer index);

    String submitQuiz(QuizSubmissionDto quizSubmissionDto, Integer attemptId);

    Map<String, Object> startQuiz(Integer quizId);

    List<LeaderboardDto> leaderBoardForQuiz(Integer quizId);

}
