package com.Quiz.Api.controller;

import com.Quiz.Api.dto.LeaderboardDto;
import com.Quiz.Api.dto.QuestionDto;
import com.Quiz.Api.dto.QuizSubmissionDto;
import com.Quiz.Api.entities.Attempt;
import com.Quiz.Api.entities.Question;
import com.Quiz.Api.entities.Quiz;
import com.Quiz.Api.security.MyUserDetails;
import com.Quiz.Api.service.AttemptService;
import com.Quiz.Api.service.QuestionService;
import com.Quiz.Api.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    QuizService quizService;
    QuestionService questionService;
    AttemptService attemptService;

    @Autowired
    public QuizController(QuizService quizService, QuestionService questionService, AttemptService attemptService) {
        this.quizService = quizService;
        this.questionService = questionService;
        this.attemptService = attemptService;
    }

    @GetMapping()
    public ResponseEntity<List<Quiz>> getAllQuiz() {
        List<Quiz> quizzes = quizService.getAllQuiz();
        return ResponseEntity.status(HttpStatus.OK).body(quizzes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Integer id) {
        Quiz quiz = quizService.getQuizById(id);
        return ResponseEntity.status(HttpStatus.OK).body(quiz);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping()
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        Quiz newQuiz = quizService.createQuiz(quiz);
        return ResponseEntity.status(HttpStatus.CREATED).body(newQuiz);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Integer id, @RequestBody Quiz quiz) {
        Quiz updatedQuiz = quizService.updateQuiz(id, quiz);
        return ResponseEntity.status(HttpStatus.OK).body(updatedQuiz);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<Quiz> patchQuiz(@PathVariable Integer id, @RequestBody Quiz quiz) {
        Quiz patchQuiz = quizService.patchQuiz(id, quiz);
        return ResponseEntity.status(HttpStatus.OK).body(patchQuiz);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Quiz> deleteQuiz(@PathVariable Integer id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/restore/{id}")
    public ResponseEntity<Quiz> restoreQuiz(@PathVariable Integer id) {
        quizService.restoreById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{quizId}/start")
    public ResponseEntity<Map<String, Object>> startQuiz(@PathVariable Integer quizId, @AuthenticationPrincipal MyUserDetails details) {

        Quiz quiz = quizService.getQuizById(quizId);
        List<Question> questions = questionService.getRandomQuestionsById(quizId);

        Attempt attempt = attemptService.createAttempt(details.getUserId(), quiz, questions);

        List<QuestionDto> dto = questions.stream()
                .map(q -> new QuestionDto(q.getId(), q.getQuestion(), q.getOptions()))
                .toList();

        Map<String, Object> startQuiz = new HashMap<>();
        startQuiz.put("attemptId", attempt.getId());
        startQuiz.put("questions", dto);

        return ResponseEntity.status(HttpStatus.OK).body(startQuiz);
    }

    @PostMapping("/{quizId}/submit/{attemptId}")
    public ResponseEntity<Integer> quizSubmission(@RequestBody QuizSubmissionDto quizSubmissionDto, @PathVariable Integer attemptId) {
        return ResponseEntity.status(HttpStatus.OK).body(attemptService.submitQuiz(quizSubmissionDto, attemptId));
    }

    @GetMapping("/leader-board/{quizId}")
    public ResponseEntity<List<LeaderboardDto>> getLeaderboardForQuiz(@PathVariable Integer quizId) {
        return ResponseEntity.status(HttpStatus.OK).body(attemptService.getLeaderboard(quizId));
    }
}
