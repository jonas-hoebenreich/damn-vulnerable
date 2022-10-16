package com.example.issues.security.service;

import com.example.issues.model.User;
import com.example.issues.security.dto.*;
import org.springframework.security.core.Authentication;

import java.util.List;


public interface UserService {

	User findByUsername(String username);


    User findById(Long id);

	List<User> findAll();

	void delete(Long id) throws Exception;

    List<String> findAllJSON();

    User login(LoginRequest loginRequest);

    Boolean registration(RegistrationRequest registrationRequest);

    void updateUser(ProfileDTO profileDTO);

    AuthenticatedUserDto findAuthenticatedUserByUsername(String username);

	Authentication getAuthentication();

	Long getUserId();

	User getUser();

	String getUsername();
}
