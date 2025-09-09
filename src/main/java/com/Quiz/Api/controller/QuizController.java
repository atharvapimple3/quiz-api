package com.Quiz.Api.controller;

import com.Quiz.Api.entities.Quiz;
import com.Quiz.Api.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
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

    @PostMapping()
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        Quiz newQuiz = quizService.createQuiz(quiz);
        return ResponseEntity.status(HttpStatus.CREATED).body(newQuiz);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Integer id, @RequestBody Quiz quiz) {
        Quiz updatedQuiz = quizService.updateQuiz(id, quiz);
        return ResponseEntity.status(HttpStatus.OK).body(updatedQuiz);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Quiz> patchQuiz(@PathVariable Integer id, @RequestBody Quiz quiz) {
        Quiz patchQuiz = quizService.patchQuiz(id, quiz);
        return ResponseEntity.status(HttpStatus.OK).body(patchQuiz);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Quiz> deleteQuiz(@PathVariable Integer id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
