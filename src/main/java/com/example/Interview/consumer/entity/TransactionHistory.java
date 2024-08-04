package com.example.Interview.consumer.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="transaction_history")
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    private String transactionId;

    private int amount;

    private int merchantId;
    private Date timeStamp;
    private int userId;
}
