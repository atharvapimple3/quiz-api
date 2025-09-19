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

    @Query(value = "Select * from question where quiz_id = :quizId and is_deleted = false order by rand() limit 10", nativeQuery = true)
    List<Question> randomQuestionsByQuizId(Integer quizId);

    @Query(value = "Select q from Question q where q.quiz.id = :quizId and q.isDeleted = false")
    List<Question> getQuestionsByQuizId(Integer quizId);
}
