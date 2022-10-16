package com.example.issues.controller.html;

import com.example.issues.model.UserRole;
import com.example.issues.security.dto.ProfileDTO;
import com.example.issues.security.mapper.UserMapper;
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
public class ProfileController {

    private final UserService userService;

    @GetMapping("/profile")
    public String viewProfile(Model model){
        model.addAttribute("profile",
                UserMapper.INSTANCE.convertToProfileDTO(userService.getUser()));
        return "profile";
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/profile/{username}")
    public String viewSpecificProfile(@PathVariable("username") String username, Model model){
        if (userService.findByUsername(username) != null)
            model.addAttribute("profile",
                UserMapper.INSTANCE.convertToProfileDTO(userService.findByUsername(username)));
        else if (userService.findById(Long.parseLong(username)) != null)
            return "redirect:/profile/" + userService.findById(Long.parseLong(username)).getUsername();
        else return "error";
        return "profile";
    }


}
