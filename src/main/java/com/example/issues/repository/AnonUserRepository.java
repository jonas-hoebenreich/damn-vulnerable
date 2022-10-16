package com.example.issues.repository;

import com.example.issues.model.AnonUser;
import com.example.issues.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnonUserRepository  extends JpaRepository<AnonUser, Long> {
    AnonUser findByUsername(String username);

}
