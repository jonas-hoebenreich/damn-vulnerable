package com.example.issues.security.service.impl;

import com.example.issues.model.LogSettings;
import com.example.issues.repository.LogRepository;
import com.example.issues.security.service.LogService;
import com.example.issues.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final UserService userService;
    @Override
    public String getLogs() {
        try {
            if (Files.exists(getLogPath())){
                log.warn(String.join("\n", Files.readAllLines(getLogPath())));
                return String.join("\n", Files.readAllLines(getLogPath()));
            }

            else return "Logfile does not exist";
        } catch (IOException e) {

        }
        return String.format("Error reading logfile at %s", getLogPath());
    }


    @Override
    public String writeLogMessage(String message) {
        message = "\n" + message;
        try {
            if (!Files.exists(getLogPath()))
                Files.createFile(getLogPath());
            Files.write(getLogPath(), message.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            return "Error reading Logs";
        }
        return getLogs();
    }

    @Override
    public void log(String msg){
        writeLogMessage("User " + userService.getUser().getId() + " " + msg);
    }

    @Override
    public Path getLogPath() {
        return Paths.get(getSettings().getLogFilePath());
    }

    @Override
    public void setLogPath(String path) {
        LogSettings settings = getSettings();
        settings.setLogFilePath("./" + path);
        logRepository.save(settings);
    }

    private LogSettings getSettings(){
        List<LogSettings> res = logRepository.findAll();
        if (res.size() == 0){
            LogSettings settings = new LogSettings();
            settings.setLogFilePath("logs/issues.log");
            logRepository.save(settings);
            return settings;
        }else
            return logRepository.findAll().get(0);
    }


}
