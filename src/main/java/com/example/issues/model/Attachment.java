package com.example.issues.model;

import lombok.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;

import javax.persistence.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ATTACHMENTS")
public class Attachment extends  BaseEntity {


    private String filename;
    @ManyToOne
    private Project project;
    private String contentType;


    public Path getPath(){
        return Paths.get("attachment/"  + filename);
    }

    public MediaType getMediaContentType() {
        try{
            return MediaType.parseMediaType(contentType);
        }catch (Exception E){
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    public String getUrl(){
        return String.format("/api/attachment/%s", this.getId());
    }

    public ByteArrayResource getContent() throws IOException {
        return new ByteArrayResource(Files.readAllBytes(this.getPath()));
    }
}
