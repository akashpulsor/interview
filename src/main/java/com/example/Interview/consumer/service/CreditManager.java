package com.example.Interview.consumer.service;

import com.example.Interview.dto.CreditTransactionDto;
import com.example.Interview.entity.User;

public interface CreditManager {


    boolean validateTransaction(User user, CreditTransactionDto creditTransactionDto);

    boolean addTransaction(CreditTransactionDto creditTransactionDto);
}
