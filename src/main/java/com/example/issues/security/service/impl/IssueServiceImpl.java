package com.example.issues.security.service.impl;

import com.example.issues.model.Issue;
import com.example.issues.repository.IssueRepository;
import com.example.issues.repository.ProjectRepository;
import com.example.issues.security.dto.IssueAddRequest;
import com.example.issues.security.dto.IssueDTO;
import com.example.issues.security.mapper.IssueMapper;
import com.example.issues.security.service.IssueService;
import com.example.issues.repository.UserRepository;
import com.example.issues.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

    final IssueRepository issueRepository;
    final UserRepository userRepository;
    final ProjectRepository projectRepository;
    final UserService userService;



    @Override
    public Issue getIssueByID(Long id) {
        Optional<Issue> issueOptional = issueRepository.findById(id);
        if (issueOptional.isPresent()) return issueOptional.get();
        return null;
    }


    @Override
    public IssueDTO getIssueDTOByID(Long id) {
        return IssueMapper.INSTANCE.convertToIssueDTO(getIssueByID(id));
    }

    @Override
    public String updateIssue(IssueDTO issueDTO) {
        return updateIssue(IssueMapper.INSTANCE.convertToIssue(issueDTO));
    }

    @Override
    public String updateIssue(Issue Issue) {
        issueRepository.save(Issue);
        return Issue.getId().toString();
    }

    @Override
    public List<Issue> getAllByProjectId(Long projectId) {
        return issueRepository.findAllByProjectId(projectId);
    }

    @Override
    public List<IssueDTO> getAllDTOByProjectId(Long projectId) {
        return IssueMapper.INSTANCE.covertToIssueDTOList(getAllByProjectId(projectId));
    }

    @Override
    public Issue createIssue(IssueAddRequest issueAddRequest, Long projectId){

        Issue issue = IssueMapper.INSTANCE.convertToIssue(issueAddRequest);
        //chamgeme
        issue.setOwner(userService.getUser());
        issue.setProject(projectRepository.findByid(projectId));
        issueRepository.save(issue);
        log.warn(issue.toString());
        return issue;
    }

    @Override
    public List<Issue> getIssuesFromUser(){
        return issueRepository.findAllByOwner(userService.getUser());
    }

    @Override
    public List<IssueDTO> getIssuesDTOFromUser(){
        return IssueMapper.INSTANCE.covertToIssueDTOList(getIssuesFromUser());
    }
}
