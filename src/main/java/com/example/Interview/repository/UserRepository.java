package com.example.Interview.repository;

import com.example.Interview.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository   extends JpaRepository<User, Integer> {

    User getByCardNumber(String cardNumber);

    @Query(nativeQuery=true, value="SELECT email FROM User WHERE email like '?1%'")
    List<User> getUsersByEmail(String email);

    List<User> findByEmailStartsWith(String email);


    List<User> findByUserNameStartsWith( String userName);

    @Query(nativeQuery=true, value="SELECT  *  FROM  User  u WHERE u.user_name LIKE CONCAT(:userName,'%')")
    List<User> finaProfiles(String userName);
    User save(User user);

    Optional<User> findByEmail(String userName);

    Optional<User> findById(long id);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phone);
}
