package com.Quiz.Api.service;

import com.Quiz.Api.dto.AnswerDto;
import com.Quiz.Api.dto.LeaderboardDTO;
import com.Quiz.Api.dto.QuestionDTO;
import com.Quiz.Api.dto.QuizSubmissionDto;
import com.Quiz.Api.entities.Attempt;
import com.Quiz.Api.entities.Question;
import com.Quiz.Api.entities.Quiz;
import com.Quiz.Api.repository.AttemptRepo;
import com.Quiz.Api.repository.QuestionRepo;
import com.Quiz.Api.repository.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuizServiceImpl implements QuizService {

    QuizRepo quizRepo;
    QuestionRepo questionRepo;
    AttemptRepo attemptRepo;

    @Autowired
    public QuizServiceImpl(QuizRepo quizRepo, QuestionRepo questionRepo, AttemptRepo attemptRepo) {
        this.quizRepo = quizRepo;
        this.attemptRepo = attemptRepo;
        this.questionRepo = questionRepo;
    }

    @Override
    public Quiz createQuiz(Quiz quiz) {
        return quizRepo.save(quiz);
    }

    @Override
    public Quiz updateQuiz(Integer id, Quiz quiz) {
        Quiz existingQuiz = quizRepo.findById(id).orElseThrow(() -> {
            return new RuntimeException("Quiz not found with ID: " + id);
        });
        existingQuiz.setCategory(quiz.getCategory());
        existingQuiz.setTitle(quiz.getTitle());
        return quizRepo.save(existingQuiz);
    }

    @Override
    public Quiz patchQuiz(Integer id, Quiz quiz) {
        Quiz existingQuiz = quizRepo.findById(id).orElseThrow(() -> {
            return new RuntimeException("Quiz not found with ID: " + id);
        });
        if (quiz.getCategory() != null) {
            existingQuiz.setCategory(quiz.getCategory());
        }
        if (quiz.getTitle() != null) {
            existingQuiz.setTitle(quiz.getTitle());
        }
        return quizRepo.save(existingQuiz);
    }

    @Override
    public List<Quiz> getAllQuiz() {
        List<Quiz> quizes = quizRepo.getActiveQuiz();
        return quizes;
    }

    @Override
    public void deleteQuiz(Integer id) {
        Quiz quiz = quizRepo.findById(id).orElseThrow(() -> {
            return new RuntimeException("Quiz not found with ID :" + id);
        });
        quiz.setIsDeleted(true);
        quizRepo.save(quiz);
    }

    @Override
    public Quiz getQuizById(Integer id) {
        Quiz quiz = quizRepo.findByIdAndIsDeletedFalse(id).orElseThrow(() -> {
            return new RuntimeException("Quiz not found with ID: " + id);
        });
        return quiz;
    }

    @Override
    public void restoreById(Integer id) {
        Quiz quiz = quizRepo.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found with ID:" + id));
        quizRepo.restoreById(id);
    }

    @Override
    public Map<String, Object> startQuiz(Integer quizId) {

        List<Question> randomQuestionsForQuiz = questionRepo.randomQuestionsByQuizId(quizId);


        List<QuestionDTO> questionDTOs = randomQuestionsForQuiz.stream()
                .map(q -> new QuestionDTO(q.getId(), q.getQuestion(), q.getOptions()))
                .toList();

        List<Integer> questionIds = randomQuestionsForQuiz
                .stream()
                .map(Question::getId)
                .toList();

        Attempt attempt = new Attempt();
//        attempt.setUser();
        attempt.setQuestionIds(questionIds);
        attempt.setStartedAt(LocalDateTime.now());
        attempt.setQuiz(randomQuestionsForQuiz.getFirst().getQuiz());
        attemptRepo.save(attempt);

        Map<String, Object> map = new HashMap<>();
        map.put("Attempt ID:", attempt.getId());
        map.put("Questions", questionDTOs);

        return map;
    }

    @Override
    public Question getOneQuestion(List<Integer> randomQuestionsById, Integer index) {
        if (index < 0 || index >= randomQuestionsById.size()) {
            throw new RuntimeException("Invalid index: " + index);
        }

        Integer questionId = randomQuestionsById.get(index);
        return questionRepo.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + questionId));
    }

    @Override
    public String submitQuiz(QuizSubmissionDto quizSubmissionDto, Integer attemptId) {

        Attempt attempt = attemptRepo.findById(attemptId).orElseThrow(() ->
                new RuntimeException("Attempt not found"));

        List<Integer> allowedQuestions = attempt.getQuestionIds();

        List<AnswerDto> answers = quizSubmissionDto.getAnswers();

        Integer score = 0;

        for (AnswerDto ans : answers) {
            if (!attempt.getQuestionIds().contains(ans.getQuestionId())) {
                continue;
            }

            Question currentQuestion = questionRepo.findById(ans.getQuestionId()).orElseThrow(() ->
                    new RuntimeException("Question not found "));

            if (currentQuestion.getCorrectAnswer().equalsIgnoreCase(ans.getAnswerString())) {
                score++;
            }
        }

        attempt.setScore(score);
        attempt.setCompletedAt(LocalDateTime.now());

        Duration timeTaken = Duration.between(attempt.getStartedAt() , attempt.getCompletedAt());
        attempt.setTimeTaken(timeTaken.getSeconds());
        attemptRepo.save(attempt);

        return score.toString();
    }

    @Override
    public List<LeaderboardDTO> leaderBoardForQuiz(Integer quizId) {
        List<Attempt> attempts = attemptRepo.findTop10ByQuiz_IdOrderByScoreDescTimeTakenAsc(quizId);

        if (attempts.isEmpty()) {
            throw new RuntimeException("Not enough attempts");
        }

        List<LeaderboardDTO> leaderBoard = new ArrayList<>();

        for (Attempt a : attempts) {
            LeaderboardDTO leaderboardDTO = new LeaderboardDTO();
            leaderboardDTO.setScore(a.getScore());
            leaderboardDTO.setQuizId(a.getQuiz().getId());
            leaderboardDTO.setEmail(a.getUser().getEmail());
            leaderboardDTO.setName(a.getUser().getName());
            leaderboardDTO.setTitle(a.getQuiz().getTitle());
            leaderboardDTO.setUserId(a.getUser().getId());

            leaderBoard.add(leaderboardDTO);
        }

        return leaderBoard;
    }

}
