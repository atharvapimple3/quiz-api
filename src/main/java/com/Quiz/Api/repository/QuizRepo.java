package com.Quiz.Api.repository;

import com.Quiz.Api.entities.Quiz;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepo extends JpaRepository<Quiz, Integer> {

    @EntityGraph(attributePaths = {"questions"})
    @Query("Select q from Quiz q where q.isDeleted = false")
    List<Quiz> getActiveQuiz();

    Optional<Quiz> findByIdAndIsDeletedFalse(Integer id);

    @Modifying
    @Transactional
    @Query("Update Quiz q set q.isDeleted = false where q.id = :id")
    void restoreById(@Param("id") Integer id);

    @Query("Select q from Quiz q where size(q.questions) >= 10")
    List<Quiz> findQuizWithAtleast10Questions();

}
