package com.example.issues.security.service;

import java.nio.file.Path;

public interface LogService {


    String getLogs();

    String writeLogMessage(String message);


    void log(String msg);

    Path getLogPath();

    void setLogPath(String path);
}
