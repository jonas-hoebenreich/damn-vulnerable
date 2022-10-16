package com.example.issues.security.service;

import com.example.issues.security.dto.IssueAddRequest;
import com.example.issues.model.Issue;
import com.example.issues.security.dto.IssueDTO;

import java.util.List;

public interface IssueService {

    Issue getIssueByID(Long id);

    IssueDTO getIssueDTOByID(Long id);

    String updateIssue(IssueDTO issueDTO);

    String updateIssue(Issue Issue);

    List<Issue> getAllByProjectId(Long projectId);

    List<IssueDTO> getAllDTOByProjectId(Long projectId);

    Issue createIssue(IssueAddRequest issueAddRequest, Long projectId);

    List<Issue> getIssuesFromUser();

    List<IssueDTO> getIssuesDTOFromUser();
}
