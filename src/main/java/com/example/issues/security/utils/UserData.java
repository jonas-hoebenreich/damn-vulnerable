package com.example.issues.security.utils;


import org.springframework.security.core.Authentication;

public interface UserData {
    Authentication getAuthentication();
    Long getUserId();
}

