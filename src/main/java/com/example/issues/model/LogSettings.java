package com.example.issues.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LOG_SETTINGS")
public class LogSettings extends BaseEntity{
    private String logFilePath;
}
