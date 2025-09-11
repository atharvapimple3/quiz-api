package com.Quiz.Api.controller;

import com.Quiz.Api.dto.LeaderboardDto;
import com.Quiz.Api.dto.QuizSubmissionDto;
import com.Quiz.Api.entities.Quiz;
import com.Quiz.Api.service.QuestionService;
import com.Quiz.Api.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    QuizService quizService;
    QuestionService questionService;

    @Autowired
    public QuizController(QuizService quizService, QuestionService questionService) {
        this.quizService = quizService;
        this.questionService = questionService;
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
    public ResponseEntity<Map<String, Object>> getQuestionsForQuiz(@PathVariable Integer quizId) {
        Map<String, Object> quiz = quizService.startQuiz(quizId);
        return ResponseEntity.status(HttpStatus.OK).body(quiz);
    }

//    @GetMapping("/{quizId}/start/{index}")
//    public ResponseEntity<Question> getOneQuestion(@PathVariable Integer quizId, @PathVariable Integer index) {
//
//        List<Question> questions = questionService.getRandomQuestionsForQuiz(quizId);
//
//        Question currentQuestion = questionService.getOneQuestion(questions, index);
//        return ResponseEntity.status(HttpStatus.OK).body(currentQuestion);
//    }

    @PostMapping("/{quizId}/submit/{attemptId}")
    public ResponseEntity<String> quizSubmission(@RequestBody QuizSubmissionDto quizSubmissionDto, @PathVariable Integer attemptId) {
        String score = quizService.submitQuiz(quizSubmissionDto, attemptId);
        return ResponseEntity.status(HttpStatus.OK).body(score);
    }

    @GetMapping("/leader-board/{quizId}")
    public ResponseEntity<List<LeaderboardDto>> getLeaderboardForQuiz(@PathVariable Integer quizId){
        List<LeaderboardDto> leaderboard = quizService.leaderBoardForQuiz(quizId);
        return ResponseEntity.status(HttpStatus.OK).body(leaderboard);
    }


}
