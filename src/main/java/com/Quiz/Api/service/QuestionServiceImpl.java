package com.Quiz.Api.service;

import com.Quiz.Api.entities.Question;
import com.Quiz.Api.exceptions.QuestionNotFoundException;
import com.Quiz.Api.repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    QuestionRepo questionRepo;

    @Autowired
    public QuestionServiceImpl(QuestionRepo questionRepo) {
        this.questionRepo = questionRepo;
    }

    @Override
    public Question createQuestion(Question question) {
        return questionRepo.save(question);
    }

    @Override
    public Question getQuestionById(Integer id) {
        Question question = questionRepo.findByIdAndIsDeletedFalse(id).orElseThrow(() -> {
            return new RuntimeException("Question not found with ID :" + id);
        });
        return question;
    }

    @Override
    public List<Question> getAllQuestions() {
        List<Question> questions = questionRepo.getAllActiveQuestions();
        return questions;
    }

    @Override
    public Question updateQuestion(Integer id, Question question) {
        Question existingQuestion = questionRepo.findById(id).orElseThrow(() -> {
            return new RuntimeException("Question not found with ID " + id);
        });
        existingQuestion.setQuestion(question.getQuestion());
        existingQuestion.setQuiz(question.getQuiz());
        existingQuestion.setOptions(question.getOptions());
        existingQuestion.setCorrectAnswer(question.getCorrectAnswer());
        return questionRepo.save(existingQuestion);
    }

    @Override
    public Question patchQuestion(Integer id, Question question) {
        Question existingQuestion = questionRepo.findById(id).orElseThrow(() -> {
            return new RuntimeException("Question not found with ID " + id);
        });
        if (question.getQuestion() != null) {
            existingQuestion.setQuestion(question.getQuestion());
        }
        if (question.getCorrectAnswer() != null) {
            existingQuestion.setCorrectAnswer(question.getCorrectAnswer());
        }
        if (question.getOptions() != null) {
            existingQuestion.setOptions(question.getOptions());
        }
        if (question.getCorrectAnswer() != null) {
            existingQuestion.setCorrectAnswer(question.getCorrectAnswer());
        }
        return questionRepo.save(existingQuestion);
    }

    @Override
    public void deleteQuestion(Integer id) {
        Question question = questionRepo.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Question not found");
        });
        question.setDeleted(true);
        questionRepo.save(question);
    }

    @Override
    public List<Question> getRandomQuestionsById(Integer quizId) {
        return questionRepo.randomQuestionsByQuizId(quizId);
    }

    @Override
    public Map<Integer, Question> getQuestionByIds(List<Integer> ids) {
        return questionRepo.findAllById(ids)
                .stream()
                .collect(Collectors.toMap(Question::getId, q -> q));
    }

    @Override
    public List<Question> getQuestionsByQuizId(Integer quizId) {
        List<Question> questions = questionRepo.getQuestionsByQuizId(quizId);

        if(questions.isEmpty()){
            throw new QuestionNotFoundException("No questions for quiz ID : " + quizId);
        }
        return questions;
    }


}
