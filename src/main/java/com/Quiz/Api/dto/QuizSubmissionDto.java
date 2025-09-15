package com.Quiz.Api.dto;

import java.util.List;
import java.util.Set;

public class QuizSubmissionDto {

    private Integer quizId;
    private Set<AnswerDto> answers;

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public Set<AnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<AnswerDto> answers) {
        this.answers = answers;
    }
}
