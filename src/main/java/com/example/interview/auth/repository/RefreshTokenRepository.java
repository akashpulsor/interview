package com.example.Interview.auth.repository;


import com.example.Interview.auth.entity.RefreshToken;
import com.example.Interview.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository   extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByToken(String token);

    RefreshToken findByUser(User user);

    @Modifying
    int deleteByUser(User user);
}
