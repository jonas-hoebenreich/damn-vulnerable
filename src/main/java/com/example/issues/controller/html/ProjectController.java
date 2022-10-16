package com.example.issues.controller.html;

import com.example.issues.security.dto.ProjectDTO;
import com.example.issues.security.service.ProjectService;
import com.example.issues.security.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final AttachmentService attachmentService;

    @GetMapping("/projects/{projectID}")
    public String projectView(@PathVariable("projectID") Long projectID, Model model){
        model.addAttribute("project", projectService.getProjectDTOByID(projectID));
        model.addAttribute("attachments", attachmentService.getAttachmentsDTOByProjectId(projectID));
        log.warn(projectService.getProjectDTOByID(projectID).getIssues().toString());
        return "project/project";
    }

    @GetMapping("/projects/{projectID}/edit")
    public String projectEditView(@PathVariable("projectID") Long projectID, Model model){
        model.addAttribute("project", projectService.getProjectDTOByID(projectID));
        return "project/editproject";
    }

    @PostMapping("/projects/edit")
    public String projectEdit(Model model,
                              @ModelAttribute ProjectDTO projectDTO){
        log.warn("Editing");
        projectService.updateProject(projectDTO);
        model.addAttribute("project", projectService.getProjectDTOByID(projectDTO.getId()));
        model.addAttribute("attachments", attachmentService.getAttachmentsDTOByProjectId(projectDTO.getId()));
        return "project/project";
    }

    @GetMapping("/projects/new")
    public String projectNewView(Model model){
        model.addAttribute("project", new ProjectDTO());
        return "project/addproject";
    }
    @PostMapping("/projects/new")
    public String projectCreate(Model model,
                              @ModelAttribute ProjectDTO projectDTO){
        return "redirect:/projects/" + projectService.createProject(projectDTO);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/projects")
    public String projectsView(Model model){
        model.addAttribute("projects", projectService.getAll());
        return "project/projects";
    }
}
