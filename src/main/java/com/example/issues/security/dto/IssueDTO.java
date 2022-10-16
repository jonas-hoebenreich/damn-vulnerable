package com.example.issues.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class IssueDTO {
    String title;
    String text;
    LocalDateTime updatedAt;
    String createdBy;
    String priority;
    Long projectId;

    Long id;
    boolean isResolved;

    public String getUrl(){
        return String.format("/projects/%d/%d", projectId, id);
    }
}
