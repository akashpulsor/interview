package com.example.Interview.consumer.repository;

import com.example.Interview.consumer.entity.TransactionHistory;
import com.example.Interview.consumer.entity.UserCreditLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionHistoryRepository  extends JpaRepository<TransactionHistory, Integer> {
}
