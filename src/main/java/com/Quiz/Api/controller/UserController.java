package com.Quiz.Api.controller;

import com.Quiz.Api.dto.AttemptHistoryDto;
import com.Quiz.Api.entities.Attempt;
import com.Quiz.Api.entities.User;
import com.Quiz.Api.service.AttemptService;
import com.Quiz.Api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AttemptService attemptService;

    @Autowired
    public UserController(UserService userService, AttemptService attemptService) {
        this.userService = userService;
        this.attemptService = attemptService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            throw new IllegalArgumentException("Validation failed for user with ID: " + id);
        }
        User updated = userService.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(@PathVariable Integer id, @Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            throw new IllegalArgumentException("Validation failed for user with ID: " + id);
        }
        User patched = userService.patchUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(patched);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/restore/{id}")
    public ResponseEntity<Void> restoreUser(@PathVariable Integer id) {
        userService.restoreById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<AttemptHistoryDto>> getUserAttempts(@PathVariable Integer userId){
        List<AttemptHistoryDto> attemptsByUser = attemptService.attemptHistory(userId);
        return ResponseEntity.status(HttpStatus.OK).body(attemptsByUser);
    }


}
