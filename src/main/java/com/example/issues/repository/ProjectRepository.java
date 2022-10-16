package com.example.issues.repository;

import com.example.issues.model.User;
import com.example.issues.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAll();
    List<Project> findAllByUser(User user);
    Project findByid(Long id);

}
