package com.Quiz.Api.dto;

import java.util.List;

public class QuizSubmissionDto {

    private Integer quizId;
    private List<AnswerDto> answers;

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public List<AnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDto> answers) {
        this.answers = answers;
    }
}
