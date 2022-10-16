package com.example.issues.controller.rest;

import com.example.issues.security.dto.ProfileDTO;
import com.example.issues.security.mapper.UserMapper;
import com.example.issues.security.service.CommentService;
import com.example.issues.security.service.LogService;
import com.example.issues.security.dto.CommentDTO;
import com.example.issues.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequiredArgsConstructor
public class ProfileRestController {

    private final UserService userService;

    @PostMapping("/profile/update")
    public String updates(Model model, @ModelAttribute ProfileDTO profile){
        log.warn("updating");
        userService.updateUser(profile);
        model.addAttribute("profile",
                UserMapper.INSTANCE.convertToProfileDTO(userService.findById(profile.getId())));
        return "success";
    }

}