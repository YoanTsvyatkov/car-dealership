package com.fmi.cardealership.controller;

import com.fmi.cardealership.dto.CreateUserDto;
import com.fmi.cardealership.dto.UpdateUserDto;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registerUser(@Valid @RequestBody CreateUserDto createUserDto) {
        User user = modelMapper.map(createUserDto, User.class);
        User addedUser = userService.addUser(user);

        logger.info(String.format("A new user with id %d was created!", addedUser.getId()));
        return modelMapper.map(addedUser, UserDto.class);
    }

    @DeleteMapping("/byID/{id}")
    public UserDto deleteUserById(@PathVariable("id") Long id) {
        User removedUser = userService.deleteUserById(id);

        logger.info(String.format("The user with id %d was removed!", id));
        return modelMapper.map(removedUser, UserDto.class);
    }

    @DeleteMapping("/byUsername/{username}")
    public UserDto deleteUserById(@PathVariable("username") String username) {
        User removedUser = userService.deleteUserByUsername(username);

        logger.info(String.format("The user with id %d was removed!", removedUser.getId()));
        return modelMapper.map(removedUser, UserDto.class);
    }

    @PutMapping("/byID/{id}")
    public UserDto updateUserById(@PathVariable("id") Long id, @Valid @RequestBody UpdateUserDto updateUserDto) {
        User currentVersion = userService.getUserById(id);
        User newVersion = modelMapper.map(updateUserDto, User.class);
        currentVersion.update(newVersion);

        User updatedUser = userService.updateUserByID(currentVersion, id);
        logger.info(String.format("The user with id %d was updated!", id));
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @PutMapping("/byUsername/{username}")
    public UserDto updateUserById(@PathVariable("username") String username, @Valid @RequestBody UpdateUserDto updateUserDto) {
        User currentVersion = userService.getUserByUsername(username);
        User newVersion = modelMapper.map(updateUserDto, User.class);
        currentVersion.update(newVersion);

        User updatedUser = userService.updateUserByUsername(currentVersion, username);
        logger.info(String.format("The user with id %d was updated!", updatedUser.getId()));
        return modelMapper.map(updatedUser, UserDto.class);
    }
}
