package com.example.issues.repository;

import com.example.issues.model.User;
import com.example.issues.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findAllByProjectId(Long projectId);
    List<Issue> findAllByOwner(User user);

}
