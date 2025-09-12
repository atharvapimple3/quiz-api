package com.Quiz.Api.dto;

import com.Quiz.Api.entities.Quiz;

public class PopularQuizDto {

    private String quizTitle;
    private Long count;

    public PopularQuizDto() {

    }

    public PopularQuizDto(String quizTitle, Long count) {
        this.quizTitle = quizTitle;
        this.count = count;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
