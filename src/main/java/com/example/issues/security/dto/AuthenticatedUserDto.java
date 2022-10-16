package com.example.issues.security.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.example.issues.model.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class AuthenticatedUserDto {

	private String name;

	private String username;

	private String password;

	private String email;

	private UserRole userRole;

	public String toJSON(){

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			// convert user object to json string and return it
			return ow.writeValueAsString(this);
		}
		catch (JsonProcessingException e) {
			// catch various errors
			return "null";
		}
	}
}
