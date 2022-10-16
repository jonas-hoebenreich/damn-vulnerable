package com.example.issues.controller.html;

import com.example.issues.security.dto.RegistrationRequest;
import com.example.issues.security.service.UserService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;
import java.util.Optional;


@CrossOrigin
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

	private final UserService userService;

	@GetMapping
	public String registrationForm(Model model, @RequestParam("error") Optional<String> error){
		model.addAttribute("user", new RegistrationRequest());
		log.warn(String.valueOf(error.isPresent()));
		if (error.isPresent()) {
			log.warn(error.get());
			model.addAttribute("error","Something went wrong, please try again");
		}

		return "RegistrationRequest";
	}


	@PostMapping
	public String registrationRequest(@ModelAttribute RegistrationRequest registrationRequest, Model model, RedirectAttributes redir) {
		final Boolean valid = userService.registration(registrationRequest);
		HttpHeaders responseHeaders = new HttpHeaders();
		String message;
		if (!valid){
			redir.addFlashAttribute("error", true);
			responseHeaders.setLocation(URI.create("/register"));
			log.error("error in controller" );
			return "redirect:/register?error";
		} else {
			responseHeaders.setLocation(URI.create("/login?error"));
			message = "success";
		}

		return "redirect:/login";
	}

}
