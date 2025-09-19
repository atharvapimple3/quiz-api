package com.Quiz.Api.repository;

import com.Quiz.Api.dto.PopularQuizDto;
import com.Quiz.Api.entities.Attempt;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttemptRepo extends JpaRepository<Attempt, Integer> {

    @EntityGraph(attributePaths = {"user","quiz"})
    @Query("Select a from Attempt a " +
            "where a.quiz.id = :quizId " +
            "and a.score = ( " +
            "   select max(a2.score) " +
            "   from Attempt a2 " +
            "   where a2.user.id = a.user.id " +
            "   and a2.quiz.id = a.quiz.id " +
            ") " +
            "order by a.score desc, a.timeTaken asc")
    List<Attempt> findTop10ByQuiz_IdOrderByScoreDescTimeTakenAsc(@Param("quizId") Integer quizId);

    @EntityGraph(attributePaths = {"user","quiz"})
    List<Attempt> findAllByUser_Id(Integer userId);

    @EntityGraph(attributePaths = {"user","quiz"})
    List<Attempt> findAllByQuiz_Id(Integer quizId);

    @Query("select avg(a.score) from Attempt a where a.quiz.id = :quizId")
    Double findAvgScoreByQuizId(@Param("quizId") Integer quizId);

    @Query("select new com.Quiz.Api.dto.PopularQuizDto(a.quiz.title, count(a)) " +
            "from Attempt a group by a.quiz order by count(a) desc")
    List<PopularQuizDto> popularQuiz(Pageable pageable);

}
