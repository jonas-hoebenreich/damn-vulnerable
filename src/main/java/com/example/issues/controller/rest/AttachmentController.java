package com.example.issues.controller.rest;

import com.example.issues.security.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.Optional;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/attachment")
@RequiredArgsConstructor
public class AttachmentController {

    String attachmentPath = "attachment/";

    private final AttachmentService attachmentService;


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/")
    public String uploadAttachment(@RequestParam("file") MultipartFile file,
                                   @RequestParam("overwrite") Optional<Boolean> overwrite,
                                   @RequestParam("projectId") Long projectId){
        try {
            attachmentService.createAttachment(overwrite, file, projectId);
        }catch(Exception e){
            return "Something went wrong";
        }
        return "success";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable("id") Long attachmentId ){
        return attachmentService.getAttachment(attachmentId);
    }

}
