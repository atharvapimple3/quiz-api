package com.Quiz.Api.service;

import com.Quiz.Api.dto.AttemptHistoryDto;
import com.Quiz.Api.dto.LeaderboardDto;
import com.Quiz.Api.dto.PopularQuizDto;
import com.Quiz.Api.dto.QuizSubmissionDto;
import com.Quiz.Api.entities.Attempt;
import com.Quiz.Api.entities.Question;
import com.Quiz.Api.entities.Quiz;

import java.util.List;

public interface AttemptService {

    Double avgScorePerQuiz(Integer quizId);

    PopularQuizDto popularQuiz();

    Attempt createAttempt(Integer userId, Quiz quiz, List<Question> questions);

    Integer submitQuiz(QuizSubmissionDto submissionDto, Integer attemptId);

    List<LeaderboardDto> getLeaderboard(Integer quizId);

    List<AttemptHistoryDto> attemptHistory(Integer userId);
}
