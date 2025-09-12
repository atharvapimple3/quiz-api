package com.Quiz.Api.service;

import com.Quiz.Api.dto.PopularQuizDto;
import com.Quiz.Api.entities.Attempt;
import com.Quiz.Api.entities.Quiz;
import com.Quiz.Api.exceptions.QuizNotFoundException;
import com.Quiz.Api.repository.AttemptRepo;
import com.Quiz.Api.repository.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttemptServiceImpl implements AttemptService {

    AttemptRepo attemptRepo;
    QuizRepo quizRepo;

    @Autowired
    public AttemptServiceImpl(AttemptRepo attemptRepo, QuizRepo quizRepo) {
        this.attemptRepo = attemptRepo;
        this.quizRepo = quizRepo;
    }

    @Override
    public List<Attempt> attemptsByQuizId(Integer quizId) {
        Quiz quiz = quizRepo.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found for ID :" + quizId));

        List<Attempt> attemptsForEachQuiz = attemptRepo.findAllByQuiz_Id(quizId);
        return attemptsForEachQuiz;
    }

    @Override
    public Double avgScorePerQuiz(Integer quizId) {
        return attemptRepo.findAvgScoreByQuizId(quizId);
    }

    @Override
    public PopularQuizDto popularQuiz() {
        return attemptRepo.popularQuiz(PageRequest.of(0,1)).getFirst();
    }


}
