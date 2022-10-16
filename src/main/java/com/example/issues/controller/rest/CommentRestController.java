package com.example.issues.controller.rest;

import com.example.issues.security.service.CommentService;
import com.example.issues.security.service.LogService;
import com.example.issues.security.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;
    final LogService logService;

    @PostMapping("/new")
    String addComment(@ModelAttribute CommentDTO newComment){
        try {
            log.warn("getting add comment request, issue id:" + newComment.getIssueId());
            commentService.addComment(newComment);
        }catch (Exception E){
            log.warn(newComment.getText());
            return "Error adding comment: %s";
        }
        logService.log(String.format("created comment: %s on issue %d", newComment.getText(),newComment.getIssueId()));
        return "success";
    }

}
