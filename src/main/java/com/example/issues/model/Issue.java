package com.example.issues.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ISSUES")
public class Issue extends BaseEntity {

    private String title;
    @Type(type="text")
    private String text;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Attachment> attachments;

    @ManyToOne(cascade = CascadeType.ALL)
    private Project project;

    @ManyToOne(cascade = CascadeType.ALL)
    private User owner;

    private String priority;

    private boolean isResolved;
}
