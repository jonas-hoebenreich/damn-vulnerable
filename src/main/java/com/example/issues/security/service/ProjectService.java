package com.example.issues.security.service;

import com.example.issues.model.Project;
import com.example.issues.security.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    Project getProjectByID(Long id);


    ProjectDTO getProjectDTOByID(Long id);

    String updateProject(ProjectDTO projectDTO);

    String updateProject(Project project);

    List<ProjectDTO> getProjectsDTOFromUser();

    List<Project> getAll();

    List<ProjectDTO> getAllDTO();


    String createProject(ProjectDTO projectDTO);

    Object getProjectsFromUser();
}
