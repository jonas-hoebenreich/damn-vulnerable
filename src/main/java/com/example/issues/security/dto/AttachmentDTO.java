package com.example.issues.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AttachmentDTO {
    private String url;
    private String filename;
    private String contentType;
}
