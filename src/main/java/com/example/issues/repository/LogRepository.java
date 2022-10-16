package com.example.issues.repository;

import com.example.issues.model.LogSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogSettings, Long> {

}
