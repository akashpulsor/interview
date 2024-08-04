package com.example.Interview.repository;

import com.example.Interview.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionHistoryRepository  extends JpaRepository<TransactionHistory, Integer> {
}
