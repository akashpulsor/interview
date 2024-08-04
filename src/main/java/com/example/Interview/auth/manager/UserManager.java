package com.example.Interview.auth.manager;

import com.example.Interview.dto.*;

public interface UserManager {

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    LoginResponseDto signUp(SignUpRequestDto signUpRequestDto);

    UserDto createUser(UserDto userDto);

    UserDto getUserById(long id);

    LoginResponseDto logout();

    TokenRefreshResponse refreshToken(TokenRefreshRequest request);



    UserDto getUser(int userId);

    UserDto updateUser(UserDto userDto);

}
