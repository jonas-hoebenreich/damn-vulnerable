package com.example.issues.security.mapper;

import com.example.issues.model.Comment;
import com.example.issues.security.service.IssueService;
import com.example.issues.security.dto.CommentDTO;
import com.example.issues.security.service.UserService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "issueId", source = "issue.id")
    @Mapping(target= "userId", source = "comment.owner.id")
    @Mapping(target= "username", source = "comment.owner.username")
    CommentDTO convertToCommentDTO(Comment comment);


    @Named("convertToComment")
    static Comment convertToComment(CommentDTO commentDTO, @Context UserService userService,
                                    @Context IssueService issueService){
        Comment theComment = new Comment();
        theComment.setText(commentDTO.getText());
        theComment.setIssue(issueService.getIssueByID(commentDTO.getIssueId()));
        theComment.setOwner(userService.getUser());
        System.out.println(theComment.getText());
        return theComment;
    }

    List<CommentDTO> convertToCommentsDTO(List<Comment> comments);

}
