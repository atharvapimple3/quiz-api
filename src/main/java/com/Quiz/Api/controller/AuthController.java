package com.Quiz.Api.controller;

import com.Quiz.Api.dto.LoginDto;
import com.Quiz.Api.dto.ResponseToken;
import com.Quiz.Api.entities.User;
import com.Quiz.Api.repository.UserRepo;
import com.Quiz.Api.security.CustomUserDetailsService;
import com.Quiz.Api.security.JwtUtils;
import com.Quiz.Api.security.MyUserDetails;
import com.Quiz.Api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    AuthenticationManager authenticationManager;
    UserService userService;
    PasswordEncoder passwordEncoder;
    JwtUtils jwtUtils;
    UserRepo userRepo;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, UserRepo userRepo) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.userRepo = userRepo;
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        if (authentication.isAuthenticated()) {
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            Map<String, Object> claims = new HashMap<>();
            claims.put("email", userDetails.getEmail());
            claims.put("userID", userDetails.getUserId());
            claims.put("role", userDetails.getRole());

            String token = jwtUtils.generateToken(loginDto.getEmail(), claims);
            ResponseToken responseToken = new ResponseToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(responseToken);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user, BindingResult result) {
        if(result.hasErrors()){
            throw new IllegalArgumentException("Invalid data");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }


}
