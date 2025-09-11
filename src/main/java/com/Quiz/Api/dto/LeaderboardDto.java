package com.Quiz.Api.dto;

public class LeaderboardDto {

    private int userId;
    private String name;
    private String email;
    private int score;
    private String title;
    private int quizId;

    public LeaderboardDto() {
    }

    public LeaderboardDto(int userId, String name, String email, int score, String title, int quizId) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.score = score;
        this.title = title;
        this.quizId = quizId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }
}
