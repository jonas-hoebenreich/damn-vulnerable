package com.example.issues.controller.rest;

import com.example.issues.security.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class LogRestController {

    private final LogService logService;

    @PostMapping("/write")
    public String viewLogs(@RequestParam("msg") String message){
        logService.writeLogMessage(message);
        return "success";
    }

    @PostMapping("/settings")
    public String changeLogPath(@RequestParam("path") String path){
        logService.setLogPath(path);
        return "success";
    }
}
