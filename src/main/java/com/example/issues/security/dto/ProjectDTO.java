package com.example.issues.security.dto;

import com.example.issues.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProjectDTO {

    private String projectName;

    private String description;

    private List<IssueDTO> issues;

    private User user;

    private Long id;

    public String getUrl(){
        return String.format("/projects/%s", id);
    }
}
