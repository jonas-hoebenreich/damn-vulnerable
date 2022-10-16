package com.example.issues.controller.html;

import com.example.issues.model.Issue;
import com.example.issues.security.dto.IssueAddRequest;
import com.example.issues.security.dto.IssueDTO;
import com.example.issues.security.service.CommentService;
import com.example.issues.security.service.IssueService;
import com.example.issues.security.dto.CommentDTO;
import com.example.issues.security.dto.ProjectDTO;
import com.example.issues.security.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Slf4j
@Controller
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;
    private final ProjectService projectService;
    private final CommentService commentService;

    @GetMapping("/projects/{projectID}/{issuesID}")
    public String issueView(@PathVariable("issuesID") Long issuesID,
                            @PathVariable("projectID") Long projectID,
                            Model model){
        IssueDTO theIssue = issueService.getIssueDTOByID(issuesID);
        ProjectDTO theProject = projectService.getProjectDTOByID(projectID);
        List<CommentDTO> comments = commentService.getCommentsDTOByIssueId(issuesID);
        CommentDTO newComment = commentService.prepareNewCommentDTO(issuesID);
        if (theProject.getId() != theIssue.getProjectId())
            return "error";
        model.addAttribute("issue", theIssue);
        model.addAttribute("project", theProject);
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", newComment);

        return "issues/issue";
    }

    @GetMapping("/projects/{projectID}/{issuesID}/edit")
    public String issueEditView(@PathVariable("issuesID") Long issuesID, Model model){
        model.addAttribute("issue", issueService.getIssueDTOByID(issuesID));
        return "issues/issueEdit";
    }

    @PostMapping("/projects/{projectID}/{issuesID}/edit")
    public String issueEditView(@PathVariable("issuesID") Long issuesID,
                                @ModelAttribute IssueDTO issueDTO){
        return  String.format("redirect:/projects/%d/%s",
                issueDTO.getProjectId(), issueService.updateIssue(issueDTO));
    }

    @GetMapping("/projects/{projectID}/newIssue")
    public String projectCreateIssueView(Model model, @PathVariable("projectID") Long projectID){
        IssueAddRequest issueAddRequest = new IssueAddRequest();
        model.addAttribute("issue", issueAddRequest);
        model.addAttribute("projectId", projectID);
        return "issues/addIssue";
    }

    @PostMapping("/projects/{projectID}/newIssue")
    public String projectCreateIssue(Model model, @PathVariable("projectID") String projectID,
                                     @ModelAttribute IssueAddRequest issueAddRequest){

        try{
            Issue issue = issueService.createIssue(issueAddRequest, Long.parseLong(projectID));
            return  String.format("redirect:/projects/%s/%s",
                    projectID, issue.getId());
        } catch (NumberFormatException e) {
            return String.format("redirect:/projects/%s",
                    projectID);
        }

    }
}
