package com.Quiz.Api.service;

import com.Quiz.Api.dto.*;
import com.Quiz.Api.entities.Attempt;
import com.Quiz.Api.entities.Question;
import com.Quiz.Api.entities.Quiz;
import com.Quiz.Api.exceptions.AttemptNotFoundException;
import com.Quiz.Api.repository.AttemptRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AttemptServiceImpl implements AttemptService {

    AttemptRepo attemptRepo;
    QuestionService questionService;

    @Autowired
    public AttemptServiceImpl(AttemptRepo attemptRepo, QuestionService questionService) {
        this.attemptRepo = attemptRepo;
        this.questionService = questionService;
    }

    @Override
    public Double avgScorePerQuiz(Integer quizId) {
        return attemptRepo.findAvgScoreByQuizId(quizId);
    }

    @Override
    public PopularQuizDto popularQuiz() {
        return attemptRepo.popularQuiz(PageRequest.of(0, 1)).getFirst();
    }

    @Override
    public Attempt createAttempt(Integer userId, Quiz quiz, List<Question> questions) {
        Attempt attempt = new Attempt();

        attempt.setQuestionIds(questions.stream()
                .map(Question::getId)
                .toList());

        attempt.setStartedAt(LocalDateTime.now());
        attempt.setQuiz(quiz);
//        attempt.setUser();
        attemptRepo.save(attempt);

        return attempt;
    }

    @Override
    public Integer submitQuiz(QuizSubmissionDto submissionDto, Integer attemptId) {
        Attempt attempt = attemptRepo.findById(attemptId)
                .orElseThrow(() -> new AttemptNotFoundException("Attempt not found"));

        List<Integer> allowedQuestions = attempt.getQuestionIds();

        Map<Integer, Question> questionMap = questionService.getQuestionByIds(allowedQuestions);

        int score = 0;
        for (AnswerDto ans : submissionDto.getAnswers()) {
            if (!allowedQuestions.contains(ans.getQuestionId())) {
                continue;
            }

            Question question = questionMap.get(ans.getQuestionId());
            if (question.getCorrectAnswer().equals(ans.getAnswerString())) {
                score++;
            }
        }

        attempt.setScore(score);
        attempt.setCompletedAt(LocalDateTime.now());
        attempt.setTimeTaken(Duration.between(attempt.getStartedAt(),attempt.getCompletedAt()).getSeconds());

        attemptRepo.save(attempt);
        return attempt.getScore();
    }

    public List<LeaderboardDto> getLeaderboard(Integer quizId) {
        List<Attempt> attempts = attemptRepo.findTop10ByQuiz_IdOrderByScoreDescTimeTakenAsc(quizId);

        if (attempts.isEmpty()) {
            throw new AttemptNotFoundException("Attempt not found for quiz ID: " + quizId);
        }

        List<LeaderboardDto> leaderboard = new ArrayList<>();
        for (Attempt a : attempts) {
            LeaderboardDto leaderboardDto = new LeaderboardDto();
            leaderboardDto.setEmail(a.getUser().getEmail());
            leaderboardDto.setName(a.getUser().getName());
            leaderboardDto.setTitle(a.getQuiz().getTitle());
            leaderboardDto.setScore(a.getScore());
            leaderboardDto.setQuizId(a.getQuiz().getId());
            leaderboardDto.setUserId(a.getUser().getId());

            leaderboard.add(leaderboardDto);
        }
        return leaderboard;
    }

    @Override
    public List<AttemptHistoryDto> attemptHistory(Integer userId) {
        List<Attempt> attempts = attemptRepo.findAllByUser_Id(userId);

        if (attempts.isEmpty()) {
            throw new AttemptNotFoundException("No attempts for this user: " + userId);
        }

        List<AttemptHistoryDto> attemptHistoryList = new ArrayList<>();

        for (Attempt a : attempts) {
            AttemptHistoryDto attemptHistoryDto = new AttemptHistoryDto();
            attemptHistoryDto.setUserId(a.getUser().getId());
            attemptHistoryDto.setTitle(a.getQuiz().getTitle());
            attemptHistoryDto.setScore(a.getScore());
            attemptHistoryDto.setName(a.getUser().getName());
            attemptHistoryDto.setStartedAt(a.getStartedAt());
            attemptHistoryDto.setCompletedAt(a.getCompletedAt());

            attemptHistoryList.add(attemptHistoryDto);
        }

        return attemptHistoryList;
    }
}

