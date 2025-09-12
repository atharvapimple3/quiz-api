package com.Quiz.Api.repository;

import com.Quiz.Api.dto.PopularQuizDto;
import com.Quiz.Api.entities.Attempt;
import com.Quiz.Api.entities.Quiz;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttemptRepo extends JpaRepository<Attempt, Integer> {

    List<Attempt> findTop10ByQuiz_IdOrderByScoreDescTimeTakenAsc(Integer quizId);

    List<Attempt> findAllByUser_Id(Integer userId);

    List<Attempt> findAllByQuiz_Id(Integer quizId);

    @Query("select avg(a.score) from Attempt a where a.quiz.id = :quizId")
    Double findAvgScoreByQuizId(@Param("quizId") Integer quizId);

    @Query("select new com.Quiz.Api.dto.PopularQuizDto(a.quiz.title, count(a)) " +
            "from Attempt a group by a.quiz order by count(a) desc")
    List<PopularQuizDto> popularQuiz(Pageable pageable);

}
