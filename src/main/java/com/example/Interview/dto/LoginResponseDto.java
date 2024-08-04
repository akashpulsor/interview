package com.example.Interview.dto;


import lombok.Data;

@Data
public class LoginResponseDto {
    private String accessToken;

    private String refreshToken;

    private String type = "Bearer";

}
