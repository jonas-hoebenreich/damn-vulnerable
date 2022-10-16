package com.example.issues.security.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;

@Slf4j
public class CustomCsrfToken implements CsrfToken {

    private String token;


    public CustomCsrfToken(String token) {
        this.token = token;
    }

    @Override
    public String getHeaderName() {
        return "X-XSRF-TOKEN";
    }

    @Override
    public String getParameterName() {
        return "_xsrf";
    }

    @Override
    public String getToken() {
        return token;
    }
}
