package com.example.issues.security.mapper;

import com.example.issues.model.Issue;
import com.example.issues.model.Project;
import com.example.issues.security.dto.IssueDTO;
import com.example.issues.security.dto.ProjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    ProjectDTO convertToProjectDTO(Project project);

    Project convertToProject(ProjectDTO projectDTO);

    List<ProjectDTO> convertToDTOList(List<Project> projects);

    @Mapping(target = "createdBy", source = "issue.owner.username")
    @Mapping(target = "projectId", source = "issue.project.id", defaultValue = "3l")
    IssueDTO issueToIssueDTO(Issue issue);

}
