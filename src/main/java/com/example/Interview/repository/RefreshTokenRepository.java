package com.example.Interview.repository;


import com.example.Interview.entity.RefreshToken;
import com.example.Interview.entity.User;
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
