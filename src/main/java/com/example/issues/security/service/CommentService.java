package com.example.issues.security.service;

import com.example.issues.model.Comment;
import com.example.issues.security.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    void addComment(CommentDTO comment);
    List<Comment> getCommentsByIssueId(Long issueId);
    List<CommentDTO> getCommentsDTOByIssueId(Long issueId);
    CommentDTO prepareNewCommentDTO(Long issueId);

}
