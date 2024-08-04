package com.example.Interview.auth.manager;

import com.example.Interview.dto.*;
import com.example.Interview.entity.ERole;
import com.example.Interview.entity.RefreshToken;
import com.example.Interview.entity.Role;
import com.example.Interview.entity.User;
import com.example.Interview.exception.EmailExistsException;
import com.example.Interview.exception.PhoneNumberExistsException;
import com.example.Interview.exception.TokenRefreshException;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class UserManagerImpl  implements  UserManager{

    private final UserService userService;

    private final JwtService jwtService;

    private PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public UserManagerImpl(UserService userService,
                           JwtService jwtService,
                           AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDto createUser(UserDto userDto) {
        return userModelToDto(this.userService.createUser(userDtoToModel(userDto)));
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(),
                        loginRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Principal userDetails = (Principal) authentication.getPrincipal();
        String jwtToken  = this.jwtService.generateJwtToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        User user = this.userService.findUserById(userDetails.getId());
        RefreshToken refreshToken = this.userService.getRefreshToken(user);
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setAccessToken(jwtToken);
        if(refreshToken!=null){
            this.userService.deleteByUserId(userDetails.getId());
        }
        refreshToken = this.userService.createRefreshToken(userDetails.getId());
        loginResponseDto.setRefreshToken(refreshToken.getToken());
        loginResponseDto.setRefreshToken(refreshToken.getToken());
        return loginResponseDto;
    }



    @Override
    public UserDto getUserById(long id) {
        return userModelToDto(this.userService.findUserById(id));
    }

    @Override
    public LoginResponseDto logout() {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (principle.toString() != "anonymousUser") {
            int userId = ((Principal) principle).getId();
            this.userService.deleteByUserId(userId);
        }
        ResponseCookie jwtCookie = this.jwtService.getCleanJwtCookie();
        ResponseCookie jwtRefreshCookie = this.jwtService.getCleanJwtRefreshCookie();
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setAccessToken(jwtCookie.toString());
        loginResponseDto.setRefreshToken(jwtRefreshCookie.toString());
        return loginResponseDto;
    }

    @Override
    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        String refreshToken = request.getRefreshToken();
        if ((refreshToken != null) && (refreshToken.length() > 0)) {
            return this.userService.findByToken(refreshToken)
                    .map(this.userService::verifyExpiration)
                    .map(RefreshToken::getUser)
                    .map(user -> {
                        String token =  this.jwtService.generateTokenFromUsername(user.getName());
                        TokenRefreshResponse response = new TokenRefreshResponse();
                        response.setAccessToken(token);
                        response.setRefreshToken(refreshToken);
                        return response;
                    })
                    .orElseThrow(() -> new TokenRefreshException(refreshToken,
                            "Refresh token is not in database!"));

        }
        throw  new TokenRefreshException(refreshToken, "Refresh token is not in database!");
    }

    @Override
    public UserDto getUser(int userId) {
        return null;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        return null;
    }


    @Override
    public LoginResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        if(this.userService.findByEmail(signUpRequestDto.getEmail())) {
            throw  new EmailExistsException("Email Already exception");
        }

        if(this.userService.findByPhone(signUpRequestDto.getPhone())) {
            throw  new PhoneNumberExistsException("Phone Number exists exception");
        }
        User user = singUpRequestDtoToUser(signUpRequestDto);
        Set<String> strRoles = signUpRequestDto.getRoles();
        user.setRoles(getRoles(strRoles));
        this.userService.createUser(user);

        UserDto userDto  =userModelToDto(user);
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUserName(userDto.getName());
        loginRequestDto.setPassword(signUpRequestDto.getPassword());
        return login(loginRequestDto);
    }

    private User singUpRequestDtoToUser(SignUpRequestDto signUpRequestDto){
        User user = new User();
        user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        user.setPhoneNumber(signUpRequestDto.getPhone());
        user.setEmail(signUpRequestDto.getEmail());
        user.setName(signUpRequestDto.getName());

        user.setGender(signUpRequestDto.getGender());
        return user;
    }

    private Set<Role> getRoles(Set<String> strRoles){
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = this.userService.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = this.userService.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;

                    default:
                        Role userRole = this.userService.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        return roles;
    }



    private UserDto userModelToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setNumber(user.getPhoneNumber());
        userDto.setPassword(user.getPassword());

        userDto.setGender(user.getGender());
        return userDto;
    }

    private User userDtoToModel(UserDto userDto){
        User user = new User();
        if(userDto.getName()!= null) user.setName(userDto.getName()) ;
        if(userDto.getEmail()!= null) user.setEmail(userDto.getEmail());
        if(userDto.getNumber()!= null) user.setPhoneNumber(userDto.getNumber());
        if(userDto.getGender()!= null) user.setGender(userDto.getGender());
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonExpired(true);
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        return user;
    }

    private User updateUserDtoToModel(UserDto userDto){
        User user = new User();
        if(userDto.getName()!= null) user.setName(userDto.getName()) ;
        if(userDto.getEmail()!= null) user.setEmail(userDto.getEmail());
        if(userDto.getNumber()!= null) user.setPhoneNumber(userDto.getNumber());
        if(userDto.getGender()!= null) user.setGender(userDto.getGender());
        if(userDto.getUserId()!= 0) user.setId(userDto.getUserId()); ;
        return user;
    }
}
