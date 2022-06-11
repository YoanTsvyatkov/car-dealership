package com.fmi.cardealership.controller;

import com.fmi.cardealership.dto.CreateUpdateUserDto;
import com.fmi.cardealership.dto.CredentialsDto;
import com.fmi.cardealership.dto.JwtDto;
import com.fmi.cardealership.dto.UserDto;
import com.fmi.cardealership.exception.WrongParametersException;
import com.fmi.cardealership.model.User;
import com.fmi.cardealership.service.UserService;
import com.fmi.cardealership.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
public class AuthController {
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public AuthController(UserService userService, JwtUtils jwtUtils, AuthenticationManager authenticationManager, ModelMapper modelMapper) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
    }

    @PostMapping("login")
    public JwtDto login(@Valid @RequestBody CredentialsDto credentialsDto, Errors errors) {
        if (errors.hasErrors()) {
            throw new WrongParametersException("Password or username is missing");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                credentialsDto.getUsername(), credentialsDto.getPassword()));

        User user = userService.getUserByUsername(credentialsDto.getUsername());
        String token = jwtUtils.generateToken(user);

        return new JwtDto(token);
    }

    @PostMapping("register")
    public UserDto register(@Valid @RequestBody CreateUpdateUserDto user, Errors errors) {
        if (errors.hasErrors()) {
            throw new WrongParametersException("User data is incorrect");
        }

        User addedUser = userService.addUser(modelMapper.map(user, User.class));
        return modelMapper.map(addedUser, UserDto.class);
    }
}
