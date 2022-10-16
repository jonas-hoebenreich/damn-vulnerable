package com.example.issues.security.mapper;

import com.example.issues.model.User;
import com.example.issues.security.dto.AuthenticatedUserDto;
import com.example.issues.security.dto.ProfileDTO;
import com.example.issues.security.dto.RegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@Mapping(target = "userRole", defaultValue = "USER")
    User convertToUser(RegistrationRequest registrationRequest);

	@Mapping(target="id", source = "user.id")
    ProfileDTO convertToProfileDTO(User user);
	AuthenticatedUserDto convertToAuthenticatedUserDto(User user);

	User convertToUser(AuthenticatedUserDto authenticatedUserDto);



}
