package com.example.Interview.consumer.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="user_credit_limit")
public class UserCreditLimit {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="user_id")
    private int userId;

    @Column(name="total_limit")
    private int totalLimit;

    @Column(name="current_spending")
    private int currentSpending;
}
