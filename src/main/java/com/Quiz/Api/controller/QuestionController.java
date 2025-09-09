package com.Quiz.Api.controller;

import com.Quiz.Api.entities.Question;
import com.Quiz.Api.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping()
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.status(HttpStatus.OK).body(questions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Integer id) {
        Question question = questionService.getQuestionById(id);
        return ResponseEntity.status(HttpStatus.OK).body(question);
    }

    @PostMapping()
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        Question newQuestion = questionService.createQuestion(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(question);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Integer id, @RequestBody Question question) {
        Question updateQuestion = questionService.updateQuestion(id, question);
        return ResponseEntity.status(HttpStatus.OK).body(updateQuestion);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Question> patchQuestion(@PathVariable Integer id, @RequestBody Question question) {
        Question patchQuestion = questionService.patchQuestion(id, question);
        return ResponseEntity.status(HttpStatus.OK).body(patchQuestion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Question> deleteQuestion(@PathVariable Integer id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
