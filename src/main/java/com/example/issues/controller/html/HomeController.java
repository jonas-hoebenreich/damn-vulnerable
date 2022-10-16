package com.example.issues.controller.html;

import com.example.issues.security.service.IssueService;
import com.example.issues.security.service.ProjectService;
import com.example.issues.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin
@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProjectService projectService;
    private final IssueService issueService;
    private  final  UserService userService;

    @GetMapping("/")
    public String index(Model model){
        if (userService.getUser() == null)
            return "redirect:/login";

        model.addAttribute("issues", issueService.getIssuesDTOFromUser());
        model.addAttribute("projects", projectService.getProjectsDTOFromUser());

        return "index";
    }
}
