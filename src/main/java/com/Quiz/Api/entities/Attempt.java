package com.Quiz.Api.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "attempt")
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Column(name = "score")
    private int score;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "time_taken")
    private LocalDateTime timeTaken;

    public Attempt() {

    }

    public Attempt(int id, User user, Quiz quiz, int score, LocalDateTime completedAt, LocalDateTime startedAt, LocalDateTime timeTaken) {
        this.id = id;
        this.user = user;
        this.quiz = quiz;
        this.score = score;
        this.completedAt = completedAt;
        this.startedAt = startedAt;
        this.timeTaken = timeTaken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(LocalDateTime timeTaken) {
        this.timeTaken = timeTaken;
    }

    @Override
    public String toString() {
        return "Attempt{" +
                "id=" + id +
                ", user=" + user +
                ", quiz=" + quiz +
                ", score=" + score +
                ", completedAt=" + completedAt +
                ", startedAt=" + startedAt +
                ", timeTaken=" + timeTaken +
                '}';
    }
}
