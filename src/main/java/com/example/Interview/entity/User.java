package com.example.Interview.entity;

import com.example.Interview.dto.Gender;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="card_number")
    private String cardNumber;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="account_non_expired")
    private boolean accountNonExpired;

    @Column(name="account_non_locked")
    private boolean accountNonLocked;

    @Column(name="credentials_non_expired")
    private boolean credentialsNonExpired;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @Enumerated(EnumType.STRING)
    private Gender gender;

}
