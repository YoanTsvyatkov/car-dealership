package com.fmi.cardealership.controller;

import com.fmi.cardealership.dto.CreateUpdateUserDto;
import com.fmi.cardealership.dto.UserDto;
import com.fmi.cardealership.model.User;
import com.fmi.cardealership.service.UserService;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController (UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService
                .getAllUsers()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{username}")
    public UserDto getUserByUsername(@PathVariable("username") String username) {
        User user = userService.getUserByUsername(username);

        return modelMapper.map(user, UserDto.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registerUser(@Valid @RequestBody CreateUpdateUserDto createUserDto) {
        User user = modelMapper.map(createUserDto, User.class);
        User addedUser = userService.addUser(user);

        logger.info(String.format("A new user with id %d was created!", addedUser.getId()));
        return modelMapper.map(addedUser, UserDto.class);
    }

    @DeleteMapping("/{username}")
    public UserDto deleteUserByUsername(@PathVariable("username") String username) {
        User removedUser = userService.deleteUserByUsername(username);

        logger.debug(String.format("The user with id %d was removed!", removedUser.getId()));
        return modelMapper.map(removedUser, UserDto.class);
    }

    @PutMapping("/{username}")
    public UserDto updateUserByUsername(@PathVariable("username") String username, @Valid @RequestBody CreateUpdateUserDto updateUserDto) {
        User user = modelMapper.map(updateUserDto, User.class);
        User updatedUser = userService.updateUserByUsername(user, username);

        logger.debug(String.format("The user with id %d was updated!", updatedUser.getId()));
        return modelMapper.map(updatedUser, UserDto.class);
    }
}
