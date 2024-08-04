package com.example.Interview.consumer.service;


import com.example.Interview.consumer.entity.UserCreditLimit;
import com.example.Interview.consumer.repository.UserCreditRepository;
import com.example.Interview.exception.UserCreditLimitNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CreditService {

    private final UserCreditRepository userCreditRepository;

    public CreditService(UserCreditRepository userCreditRepository) {
        this.userCreditRepository = userCreditRepository;
    }


    public UserCreditLimit getUserCreditLimit(int userId) {
        UserCreditLimit userCreditLimit = this.userCreditRepository.getByUserId(userId);

        if(userCreditLimit == null) {
            log.error("Credit Limit Not Found for User -{}", userId);
            throw  new UserCreditLimitNotFoundException("User Credit Limit Not found");
        }

        return userCreditLimit;
    }
}
