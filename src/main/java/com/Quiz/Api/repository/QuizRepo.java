package com.Quiz.Api.repository;

import com.Quiz.Api.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepo extends JpaRepository<Quiz, Integer> {

    @Query("Select q from Quiz q where q.isDeleted = false")
    List<Quiz> getActiveQuiz();

    Optional<Quiz> findByIdAndIsDeletedFalse(Integer id);

}
