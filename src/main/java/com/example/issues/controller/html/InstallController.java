package com.example.issues.controller.html;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Optional;

@CrossOrigin
@Slf4j
@Controller
@RequiredArgsConstructor
public class InstallController {

    /*
    TODO: add installation
     */
    @GetMapping("/install")
    public String installView(Model model, @RequestParam("page") Optional<String> redirect){
        model.addAttribute("redirectTo", redirect.isPresent() ? redirect.get() : "${7*7}");
        return "alreadyInstalled";
    }

}
