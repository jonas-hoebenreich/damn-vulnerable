package com.example.issues.security.service.impl;

import com.example.issues.model.*;
import com.example.issues.repository.*;
import com.example.issues.security.dto.*;
import com.example.issues.service.UserValidationService;
import com.example.issues.utils.GeneralMessageAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.example.issues.security.mapper.UserMapper;
import com.example.issues.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private static final String REGISTRATION_SUCCESSFUL = "registration_successful";

	private final UserRepository userRepository;
	private final IssueRepository issueRepository;
	private final AttachmentRepository attachmentRepository;
	private final ProjectRepository projectRepository;
	private final AnonUserRepository anonUserRepository;
	private final CommentRepository commentRepository;


	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final UserValidationService userValidationService;

	private final GeneralMessageAccessor generalMessageAccessor;

	@Override
	public User findByUsername(String username) {

		return userRepository.findByUsername(username);
	}



	@Override
	public User findById(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public User login(LoginRequest loginRequest){
		User user = findByUsername(loginRequest.getUsername());
		if (bCryptPasswordEncoder.encode(loginRequest.getPassword() ) == user.getPassword())
			return user;
		return null;
	}

	@Override
	public Boolean registration(RegistrationRequest registrationRequest) {

		try {
			userValidationService.validateUser(registrationRequest);
			final User user = UserMapper.INSTANCE.convertToUser(registrationRequest);
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

			userRepository.save(user);
			final String username = registrationRequest.getUsername();
			log.info("{} registered successfully!", username);
		}catch (Exception e) {
			return false;
		}

		return true;
	}

	@Override
	public List<User> findAll(){
		return userRepository.findAll();
	}

	@Override
	public void delete(Long id) throws Exception {
		Optional<User> ou = userRepository.findById(id);
		// deletion of a normal user lets the user "deleted_user" inherit all objects
		// if deleted_user get deleted everything owned by it will be lost
		// quick and dirty
		if (!ou.isPresent()) throw new Exception();
		User user = ou.get();
		List<Project> projects = projectRepository.findAllByUser(user);
		List<Issue> issues = issueRepository.findAllByOwner(user);
		User delUser = anonUserRepository.findByUsername("Anonymous");
		for (Project p: projects) {
			p.setUser(delUser); projectRepository.save(p);
		}
		for (Issue i: issues) {
			List<Comment> comments = commentRepository.findAllByIssueId(i.getId());
			for (Comment c: comments){
				c.setOwner(delUser); commentRepository.save(c);
			}
			i.setOwner(delUser); issueRepository.save(i);
		}
		if (user == delUser ) return;
		userRepository.delete(user);
	}

	@Override
	public List<String> findAllJSON() {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return userRepository.findAll()
				.stream()
				.map(UserMapper.INSTANCE::convertToAuthenticatedUserDto)
				.collect(Collectors.toList())
				.stream()
				.map(AuthenticatedUserDto::toJSON)
				.collect(Collectors.toList())
				;
	}

	@Override
	public void updateUser(ProfileDTO profileDTO){
		User currentUser = findById(profileDTO.getId());
		if (profileDTO.getNewPassword() != null || profileDTO.getNewPassword() != ""){
			if (currentUser.getPassword() == profileDTO.getOldPassword() || currentUser.getUserRole() == UserRole.ADMIN ){
				if (profileDTO.getNewPassword() != "" ) currentUser.setPassword(bCryptPasswordEncoder.encode(profileDTO.getNewPassword()));
				currentUser.setEmail(profileDTO.getEmail());
				currentUser.setName(profileDTO.getName());
				currentUser.setUsername(profileDTO.getUsername());
				userRepository.save(currentUser);

			}
		}
	}

	@Override
	public AuthenticatedUserDto findAuthenticatedUserByUsername(String username) {

		final User user = findByUsername(username);

		return UserMapper.INSTANCE.convertToAuthenticatedUserDto(user);
	}

	@Override
	public Authentication getAuthentication() {

		return SecurityContextHolder.getContext()
				.getAuthentication();
	}
	@Override
	public Long getUserId() {
		return getUser().getId();
	}

	@Override
	public User getUser(){
		return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@Override
	public String getUsername(){
		return getUser().getUsername();
	}


}
