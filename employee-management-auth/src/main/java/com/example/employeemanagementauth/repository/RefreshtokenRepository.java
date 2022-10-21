package com.example.employeemanagementauth.repository;

import com.example.employeemanagementauth.model.Refreshtoken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshtokenRepository extends JpaRepository <Refreshtoken, Long> {
    Optional<Refreshtoken> findByToken(String token);

    void deleteByToken(String token);
}
