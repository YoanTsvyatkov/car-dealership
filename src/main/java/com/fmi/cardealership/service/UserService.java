package com.fmi.cardealership.service;

import com.fmi.cardealership.exception.EntityNotFoundException;
import com.fmi.cardealership.model.User;
import com.fmi.cardealership.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        boolean existsEmail = userRepository
                .findByEmail(user.getEmail())
                .isPresent(),
        existsUsername = userRepository
                .findByUsername(user.getUsername())
                .isPresent();

        if (existsEmail) {
            throw new IllegalStateException("Email already registered!");
        }
        if (existsUsername) {
            throw new IllegalStateException("Username already registered!");
        }

        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("User with id: %d, does not exist", id)));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("User with username: %s, does not exist", username)));
    }

    public User deleteUserById(Long id) {
        User userToDelete = getUserById(id);
        userRepository.delete(userToDelete);
        return userToDelete;
    }

    public User deleteUserByUsername(String username) {
        User userToDelete = getUserByUsername(username);
        userRepository.delete(userToDelete);
        return userToDelete;
    }

    public User updateUserByID(User user, Long id) {
        return userRepository.findById(id)
                .map(dbUser -> updateUser(dbUser, user))
                .orElseGet(() -> userRepository.save(user));
    }

    public User updateUserByUsername(User user, String username) {
        return userRepository.findByUsername(username)
                .map(dbUser -> updateUser(dbUser, user))
                .orElseGet(() -> userRepository.save(user));
    }

    private User updateUser(User dbUser, User request) {
        dbUser.update(request);
        return userRepository.save(dbUser);
    }
}
