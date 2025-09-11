package com.Quiz.Api.entities;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "attempt")
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Column(name = "score")
    private int score;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "time_taken")
    private Long timeTaken;


    @Type(JsonType.class)
    @Column(name = "question_ids", columnDefinition = "json")
    private List<Integer> questionIds;

    public Attempt() {

    }

    public Attempt(int id, User user, Quiz quiz, int score, LocalDateTime completedAt, LocalDateTime startedAt, Long timeTaken, List<Integer> questionIds) {
        this.id = id;
        this.user = user;
        this.quiz = quiz;
        this.score = score;
        this.completedAt = completedAt;
        this.startedAt = startedAt;
        this.timeTaken = timeTaken;
        this.questionIds = questionIds;
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

    public Long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public List<Integer> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<Integer> questionIds) {
        this.questionIds = questionIds;
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
