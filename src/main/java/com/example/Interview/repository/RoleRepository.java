package com.example.Interview.repository;

import com.example.Interview.entity.ERole;
import com.example.Interview.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository   extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

}
