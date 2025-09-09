package com.Quiz.Api.service;

import com.Quiz.Api.entities.User;
import com.Quiz.Api.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User createUser(User user) {
        log.info("Creating new User with email :{}", user.getEmail());
        if (userRepo.existsByEmail(user.getEmail())) {
            log.warn("Email already exists :{}", user.getEmail());
            throw new RuntimeException("User already exists");
        }
        User newUser = userRepo.save(user);
        log.info("User save successfully with id: {}", user.getId());
        return newUser;
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Getting all users");
        List<User> users = userRepo.getActiveUsers();
        log.info("Total users fetched :{}", users.size());
        return users;
    }

    @Override
    public User getUserById(Integer id) {
        log.info("Getting user with ID: {}", id);
        return userRepo.findByIdAndIsDeletedFalse(id).orElseThrow(() ->
                new RuntimeException("No user with ID :" + id));
    }

    @Override
    public User updateUser(Integer id, User user) {
        log.info("Updating User with ID :{}", id);
        User existingUser = userRepo.findById(id).orElseThrow(() -> {
            log.warn("User not found with ID: {}", id);
            return new RuntimeException("User not found with ID: " + id);
        });
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());

        log.info("User updated with ID: {}", id);
        return existingUser;
    }

    @Override
    public void deleteUser(Integer id) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found with ID :" + id));
        user.setIsDeleted(true);
        userRepo.save(user);
    }

    @Override
    public User patchUser(Integer id, User user) {
        log.info("Patching user with ID: {}", id);
        User existing = userRepo.findById(id).orElseThrow(() -> {
            log.warn("User not found with ID :{}", id);
            return new RuntimeException("User not found with ID:" + id);
        });
        if (user.getName() != null) {
            existing.setName(user.getName());
        }
        if (user.getEmail() != null) {
            existing.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            existing.setPassword(user.getPassword());
        }

        User updated = userRepo.save(existing);
        log.info("User patched with ID: {}", id);
        return updated;
    }

    @Override
    public void restoreById(Integer id) {
        User user = userRepo.findById(id).orElseThrow(() ->{
            throw new RuntimeException("User not found with ID: "+ id);
        });
        userRepo.restoreById(id);
    }
}
