package com.Quiz.Api.controller;

import com.Quiz.Api.dto.PopularQuizDto;
import com.Quiz.Api.service.AttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    AttemptService attemptService;

    @Autowired
    public AnalyticsController(AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @GetMapping("/avg-score/{quizId}")
    public ResponseEntity<Double> getAvgScorePerQuiz(@PathVariable Integer quizId){

        return ResponseEntity.status(HttpStatus.OK).body(attemptService.avgScorePerQuiz(quizId));
    }

    @GetMapping("/popular-quiz")
    public ResponseEntity<PopularQuizDto> popularQuiz(){
        return ResponseEntity.status(HttpStatus.OK).body(attemptService.popularQuiz());
    }



}
