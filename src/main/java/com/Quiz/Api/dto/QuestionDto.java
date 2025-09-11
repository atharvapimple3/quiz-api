package com.Quiz.Api.dto;

import java.util.*;

public class QuestionDto {

    private int id;
    private String question;
    private List<String> options;

    public QuestionDto(Integer id, String question, List<String> options) {
        this.id = id;
        this.question = question;
        this.options = new ArrayList<>(options);
        Collections.shuffle(this.options);
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
