package com.example.issues.security.service.impl;

import com.example.issues.security.service.CommentService;
import com.example.issues.model.Comment;
import com.example.issues.repository.CommentRepository;
import com.example.issues.repository.IssueRepository;
import com.example.issues.security.dto.CommentDTO;
import com.example.issues.security.mapper.CommentMapper;
import com.example.issues.security.service.IssueService;
import com.example.issues.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final IssueService issueService;
    private final IssueRepository issueRepository;
    private final UserService userService;


    @Override
    public void addComment(CommentDTO commentDTO) {
        log.warn("add comment userID=" + userService.getUserId());
        log.warn("add comment text=" + commentDTO.getText());
        commentDTO.setUserId(userService.getUserId());
        commentRepository.save(CommentMapper.convertToComment(commentDTO,userService,issueService));
    }

    @Override
    public List<Comment> getCommentsByIssueId(Long issueId) {
        //        return commentRepository.findAllByIssue(issueRepository.findById(issueId).get());
        return commentRepository.findAllByIssueId(issueId);
    }

    @Override
    public List<CommentDTO> getCommentsDTOByIssueId(Long issueId) {

        return CommentMapper.INSTANCE.convertToCommentsDTO(getCommentsByIssueId(issueId));
    }

    @Override
    public CommentDTO prepareNewCommentDTO(Long issueId) {
        CommentDTO newComment = new CommentDTO();
        newComment.setIssueId(issueId);
        log.warn("Issue ID" + newComment.getIssueId());
        return newComment;
    }
}
