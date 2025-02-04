package com.example.Interview.consumer.service;

import com.example.Interview.auth.manager.UserService;
import com.example.Interview.entity.TransactionHistory;
import com.example.Interview.entity.UserCreditLimit;
import com.example.Interview.dto.CreditTransactionDto;
import com.example.Interview.entity.User;
import com.example.Interview.exception.CreditLimitExceedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreditManagerImpl implements CreditManager {

    private final CreditService creditService;
    private final UserService userService;

    private final TransactionHistoryService transactionHistoryService;
    public CreditManagerImpl(CreditService creditService,
                             UserService userService,
                             TransactionHistoryService transactionHistoryService) {
        this.creditService = creditService;
        this.userService = userService;
        this.transactionHistoryService = transactionHistoryService;
    }
    @Override
    public boolean validateTransaction(User user, CreditTransactionDto creditTransactionDto) {

        UserCreditLimit userCreditLimit = this.creditService.getUserCreditLimit(user.getId());
        int totalLimit = userCreditLimit.getTotalLimit();

        int currentSpending = userCreditLimit.getCurrentSpending();
        int currentAmount = creditTransactionDto.getAmount();
        if(totalLimit - (currentAmount + currentSpending) > 0){
            return true;
        }
        log.info("Credit limit is expired");
        return false;
    }

    @Override
    public boolean addTransaction(CreditTransactionDto creditTransactionDto) {
        User user = this.userService.getUserByCardNumber(creditTransactionDto.getCardNumber());
        if(validateTransaction(user,  creditTransactionDto)){
            throw new CreditLimitExceedException("Credit Limit Exceed Exception");
        }

        log.info("Transaction is valid and has spending limit");
        TransactionHistory transactionHistory = creditTransactionToHistoryDto(user, creditTransactionDto);
        this.transactionHistoryService.addTransactionHistory(transactionHistory);
        return true;
    }


    private TransactionHistory creditTransactionToHistoryDto(User user,
                                                             CreditTransactionDto creditTransactionDto) {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionId(creditTransactionDto.getTransactionId());
        transactionHistory.setAmount(creditTransactionDto.getAmount());
        transactionHistory.setMerchantId(creditTransactionDto.getMerchantId());
        transactionHistory.setTimeStamp(creditTransactionDto.getTimeStamp());
        transactionHistory.setUserId(user.getId());
        return transactionHistory;
    }
}
