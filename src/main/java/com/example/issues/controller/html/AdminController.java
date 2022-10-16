package com.example.issues.controller.html;

import com.example.issues.security.service.LogService;
import com.example.issues.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/secure/admin")
public class AdminController {

    private final UserService userService;
    private final LogService logService;

    @GetMapping("/users")
    public String adminUserView(Model model){
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }

    @GetMapping("/logs")
    public String viewLogs(Model model){
        model.addAttribute("settings", logService.getLogPath());
        model.addAttribute("logs", logService.getLogs().replace("\n", "<br>"));
        return "admin/logs";
    }


}
