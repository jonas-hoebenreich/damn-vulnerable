package com.example.issues.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PROJECTS")
public class Project extends BaseEntity {

    private String description;

    @Column(unique = true)
    private String projectName;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    public String getUrl(){
        return String.format("/projects/%s", getId());
    }

}
