package com.Quiz.Api.repository;

import com.Quiz.Api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    @Query("Select u from User u where u.isDeleted = false")
    List<User> getActiveUsers();

    Optional<User> findByIdAndIsDeletedFalse(Integer id);

}
