package com.Quiz.Api.service;

import com.Quiz.Api.entities.Quiz;
import com.Quiz.Api.repository.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService{

    QuizRepo quizRepo;

    @Autowired
    public QuizServiceImpl(QuizRepo quizRepo) {
        this.quizRepo = quizRepo;
    }

    @Override
    public Quiz createQuiz(Quiz quiz) {
        return quizRepo.save(quiz);
    }

    @Override
    public Quiz updateQuiz(Integer id, Quiz quiz) {
        Quiz existingQuiz = quizRepo.findById(id).orElseThrow(() ->{
            return new RuntimeException("Quiz not found with ID: "+ id);
        });
        existingQuiz.setCategory(quiz.getCategory());
        existingQuiz.setTitle(quiz.getTitle());
        return quizRepo.save(existingQuiz);
    }

    @Override
    public Quiz patchQuiz(Integer id, Quiz quiz) {
        Quiz existingQuiz = quizRepo.findById(id).orElseThrow(() ->{
            return new RuntimeException("Quiz not found with ID: "+ id);
        });
        if(quiz.getCategory() != null){
            existingQuiz.setCategory(quiz.getCategory());
        }
        if(quiz.getTitle() != null){
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
        Quiz quiz = quizRepo.findById(id).orElseThrow(() ->{
            return new RuntimeException("Quiz not found with ID :" + id);
        });
        quiz.setIsDeleted(true);
        quizRepo.save(quiz);
    }

    @Override
    public Quiz getQuizById(Integer id) {
        Quiz quiz = quizRepo.findByIdAndIsDeletedFalse(id).orElseThrow(() ->{
            return new RuntimeException("Quiz not found with ID: "+ id);
        });
        return quiz;
    }
}
