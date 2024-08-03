package com.example.Interview.auth.manager;


import com.example.Interview.dto.Principal;
import com.example.Interview.auth.entity.ERole;
import com.example.Interview.auth.entity.RefreshToken;
import com.example.Interview.auth.entity.Role;
import com.example.Interview.auth.entity.User;
import com.example.Interview.auth.repository.RefreshTokenRepository;
import com.example.Interview.auth.repository.RoleRepository;
import com.example.Interview.auth.repository.UserRepository;
import com.example.Interview.exception.TokenRefreshException;
import com.example.Interview.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Value("${dalai.llama.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;
    private final UserRepository userRepository;


    private final RefreshTokenRepository refreshTokenRepository;

    private final RoleRepository roleRepository;

    public  UserService(UserRepository userRepository,
                        RefreshTokenRepository refreshTokenRepository,
                        RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.roleRepository = roleRepository;
    }


    public List<User> getUsersByEmail(String email) {
        return  this.userRepository.findByEmailStartsWith(email);
    }

    public List<User> getUsersByUserName(String userName) {
        return  this.userRepository.finaProfiles(userName);
    }

    public User createUser(User user) {
        return  this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(username).
                orElseThrow(()-> new UserNotFoundException("User Not Found with username: "+ username));

        return Principal.build(user);
    }

    public User findUserById(long id) {
        return this.userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User not found"));
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken getRefreshToken(User user){
        return this.refreshTokenRepository.findByUser(user);
    }
    public RefreshToken createRefreshToken(int userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(int userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }


    public boolean findByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public Optional<Role> findByName(ERole role) {
        return this.roleRepository.findByName(role);
    }

    public boolean findByPhone(String phone) {
        return this.userRepository.existsByPhoneNumber(phone);
    }

}
