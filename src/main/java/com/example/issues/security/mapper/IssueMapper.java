package com.example.issues.security.mapper;

import com.example.issues.security.dto.IssueAddRequest;
import com.example.issues.security.dto.IssueDTO;
import com.example.issues.model.Issue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IssueMapper {

    IssueMapper INSTANCE = Mappers.getMapper(IssueMapper.class);

    @Mapping(target = "createdBy", source = "issue.owner.username")
    @Mapping(target = "projectId", source = "issue.project.id", defaultValue = "3l")
    IssueDTO convertToIssueDTO(Issue issue);

    Issue convertToIssue(IssueDTO issueDTO);

    Issue convertToIssue(IssueAddRequest issueAddRequest);


    List<IssueDTO> covertToIssueDTOList(List<Issue> issuesDTO);

    List<Issue> covertToIssueList(List<IssueDTO> issues);


}
