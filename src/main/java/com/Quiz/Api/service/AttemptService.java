package com.Quiz.Api.service;

import com.Quiz.Api.dto.*;
import com.Quiz.Api.entities.Attempt;
import com.Quiz.Api.entities.Question;
import com.Quiz.Api.entities.Quiz;
import com.Quiz.Api.entities.User;

import java.util.List;

public interface AttemptService {

    Double avgScorePerQuiz(Integer quizId);

    PopularQuizDto popularQuiz();

    Attempt createAttempt(User user, Quiz quiz, List<Question> questions);

    Integer submitQuiz(QuizSubmissionDto submissionDto, Integer attemptId);

    List<LeaderboardDto> getLeaderboard(Integer quizId);

    List<AttemptHistoryDto> attemptHistory(Integer userId);
}
