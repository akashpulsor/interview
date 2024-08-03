package com.example.Interview.auth.controller;


import com.example.Interview.dto.LoginRequestDto;
import com.example.Interview.dto.LoginResponseDto;
import com.example.Interview.dto.SignUpRequestDto;
import com.example.Interview.auth.manager.UserManager;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserManager userManager;

    public AuthController(UserManager userManager) {
        this.userManager = userManager;
    }

    @PostMapping("/login")
    public LoginResponseDto authenticateUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return this.userManager.login(loginRequestDto);
    }

    @PostMapping("/register")
    public LoginResponseDto registerUser(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        return this.userManager.signUp(signUpRequestDto);
    }
}
