package com.example.issues.security.service.impl;

import com.example.issues.model.Project;
import com.example.issues.model.User;
import com.example.issues.repository.ProjectRepository;
import com.example.issues.security.mapper.ProjectMapper;
import com.example.issues.security.service.IssueService;
import com.example.issues.security.service.LogService;
import com.example.issues.security.service.ProjectService;
import com.example.issues.security.dto.ProjectDTO;
import com.example.issues.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    final ProjectRepository projectRepository;
    final IssueService issueService;
    final UserService userService;
    final LogService logService;


    @Override
    public Project getProjectByID(Long id) {
        Project project = projectRepository.findByid(id);
        /*project.setIssues(issueService.getAllByProjectId(id));
        log.warn(project.getIssues().toString());*/
        return project;
    }
    @Override
    public ProjectDTO getProjectDTOByID(Long id) {
        ProjectDTO theProject = ProjectMapper.INSTANCE.convertToProjectDTO(getProjectByID(id));
        theProject.setIssues(issueService.getAllDTOByProjectId(id));
        return theProject;
    }

    public String updateProject(ProjectDTO projectDTO){
        log.warn(projectDTO.getId().toString());
        if (projectDTO.getId() == null)
            return null;
        Project project = projectRepository.findByid(projectDTO.getId());
        log.warn("PROJECT:" + project.getProjectName());
        log.warn("PROJECT:" + project.getId().toString());
        project.setProjectName(projectDTO.getProjectName());
        project.setDescription(projectDTO.getDescription());
        logService.log(String.format("updated project %s", project.getId()));
        return updateProject(project);
    }

    public String updateProject(Project project){
        log.warn(project.getId().toString());
        projectRepository.save(project);
        return project.getId().toString();
    }

    public String createProject(ProjectDTO projectDTO){
        Project project = ProjectMapper.INSTANCE.convertToProject(projectDTO);
        project.setUser(userService.getUser());
        projectRepository.save(project);
        logService.log(String.format("created project %s", project.getId()));

        return project.getId().toString();
    }

    @Override
    public List<Project> getProjectsFromUser(){
        return projectRepository.findAllByUser(userService.getUser());
    }

    @Override
    public List<ProjectDTO> getProjectsDTOFromUser(){
        return ProjectMapper.INSTANCE.convertToDTOList(getProjectsFromUser());
    }

    public List<Project> getProjectsFromUser(User user){
        return projectRepository.findAllByUser(user);
    }

    @Override
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    @Override
    public List<ProjectDTO> getAllDTO() {
        return ProjectMapper.INSTANCE.convertToDTOList(getAll());
    }

}
