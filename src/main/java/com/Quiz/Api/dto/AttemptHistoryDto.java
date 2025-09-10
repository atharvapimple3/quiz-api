package com.Quiz.Api.dto;

import java.time.LocalDateTime;

public class AttemptHistoryDto {

    private int userId;
    private String name;
    private String title;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private int score;

    public AttemptHistoryDto() {
    }

    public AttemptHistoryDto(int userId, String name, String title, LocalDateTime startedAt, LocalDateTime completedAt, int score) {
        this.userId = userId;
        this.name = name;
        this.title = title;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.score = score;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
