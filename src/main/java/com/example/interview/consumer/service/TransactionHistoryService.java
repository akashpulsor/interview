package com.example.Interview.consumer.service;


import com.example.Interview.consumer.entity.TransactionHistory;
import com.example.Interview.consumer.repository.TransactionHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionHistoryService {

    private final TransactionHistoryRepository transactionHistoryRepository;
    public TransactionHistoryService(TransactionHistoryRepository transactionHistoryRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
    }


    public TransactionHistory addTransactionHistory(TransactionHistory transactionHistory) {
        return this.transactionHistoryRepository.save(transactionHistory);
    }
}