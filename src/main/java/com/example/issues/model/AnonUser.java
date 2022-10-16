package com.example.issues.model;

import lombok.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;

import javax.persistence.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Entity
@Getter
@Setter
@Table(name = "anonUser")
public class AnonUser extends User {
    public AnonUser(){
        super();
    }
}
