package com.example.Interview.dto;


import lombok.Data;

import java.util.Date;

@Data
public class CreditTransactionDto {

    private String transactionId;

    private String cardNumber;

    private int amount;

    private Date timeStamp;
    private int merchantId;

}
