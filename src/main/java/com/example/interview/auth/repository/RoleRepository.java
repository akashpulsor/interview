package com.example.Interview.auth.repository;

import com.example.Interview.auth.entity.ERole;
import com.example.Interview.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository   extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

}
