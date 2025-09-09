package com.Quiz.Api.repository;

import com.Quiz.Api.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {

    @Query("Select q from Question q where q.isDeleted = false ")
    List<Question> getAllActiveQuestions();

    Optional<Question> findByIdAndIsDeletedFalse(Integer id);
}
