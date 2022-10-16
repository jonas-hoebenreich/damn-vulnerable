package com.example.issues.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProfileDTO {
    private String email;
    private String name;
    private String username;
    private String oldPassword;
    private String newPassword;
    private Long id;
}
