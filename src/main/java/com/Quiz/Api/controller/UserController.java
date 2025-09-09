package com.Quiz.Api.controller;

import com.Quiz.Api.entities.User;
import com.Quiz.Api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            throw new RuntimeException("Cannot add user");
        }
        User savedUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            throw new RuntimeException("Validation failed for user with ID: " + id);
        }
        User updated = userService.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<User> patchUser(@PathVariable Integer id, @Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            throw new RuntimeException("Validation failed for user with ID: " + id);
        }
        User patched = userService.patchUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(patched);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
