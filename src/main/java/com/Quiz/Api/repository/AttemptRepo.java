package com.Quiz.Api.repository;

import com.Quiz.Api.entities.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttemptRepo extends JpaRepository<Attempt, Integer> {

    List<Attempt> findTop10ByQuiz_IdOrderByScoreDesc(Integer quizId);

}
