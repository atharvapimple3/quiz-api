package com.Quiz.Api.repository;

import com.Quiz.Api.entities.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptRepo extends JpaRepository<Attempt, Integer> {
}
