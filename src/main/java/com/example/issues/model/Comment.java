package com.example.issues.model;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COMMENTS")
public class Comment extends BaseEntity {


    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private Issue issue;

    private String text;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private User owner;

}
