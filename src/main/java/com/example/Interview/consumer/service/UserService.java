package com.example.Interview.consumer.service;


import com.example.Interview.consumer.repository.UserRepository;
import com.example.Interview.entity.User;
import com.example.Interview.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User getUserByCardNumber(String cardNumber){
        User user =this.userRepository.getByCardNumber(cardNumber);
        if(user == null) {
            log.error("Invalid Card data");
            throw  new UserNotFoundException("User Not found");
        }
        return user;
    }
}
