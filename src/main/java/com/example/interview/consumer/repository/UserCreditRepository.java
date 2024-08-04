package com.example.Interview.consumer.repository;

import com.example.Interview.consumer.entity.UserCreditLimit;
import com.example.Interview.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserCreditRepository  extends JpaRepository<UserCreditLimit, Integer> {
    UserCreditLimit getByUserId(int userId);
}
