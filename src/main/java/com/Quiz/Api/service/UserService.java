package com.Quiz.Api.service;

import com.Quiz.Api.dto.AttemptHistoryDto;
import com.Quiz.Api.entities.Attempt;
import com.Quiz.Api.entities.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();

    User getUserById(Integer id);

    User updateUser(Integer id, User user);

    void deleteUser(Integer id);

    User patchUser(Integer id, User user);

    void restoreById(Integer id);

    List<AttemptHistoryDto> getUserHistoryOfAttempts(Integer userId);

    User findByEmail(String email);

}
