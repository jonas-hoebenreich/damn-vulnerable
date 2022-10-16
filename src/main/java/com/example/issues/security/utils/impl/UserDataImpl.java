package com.example.issues.security.utils.impl;

import com.example.issues.model.User;
import com.example.issues.security.utils.UserData;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserDataImpl implements UserData {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication()).getId();
    }

}
