package com.example.Interview.repository;

import com.example.Interview.entity.UserCreditLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserCreditRepository  extends JpaRepository<UserCreditLimit, Integer> {
    UserCreditLimit getByUserId(int userId);
}
