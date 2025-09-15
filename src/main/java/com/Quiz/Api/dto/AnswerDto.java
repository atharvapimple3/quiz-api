package com.Quiz.Api.dto;

import java.util.Objects;

public class AnswerDto {

    private int questionId;
    private String answerString;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswerString() {
        return answerString;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AnswerDto answerDto = (AnswerDto) o;
        return questionId == answerDto.questionId && Objects.equals(answerString, answerDto.answerString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId);
    }
}
