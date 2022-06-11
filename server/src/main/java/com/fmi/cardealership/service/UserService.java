package com.fmi.cardealership.service;

import com.fmi.cardealership.exception.EntityNotFoundException;
import com.fmi.cardealership.model.User;
import com.fmi.cardealership.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final  PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

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

    public User deleteUserByUsername(String username) {
        User userToDelete = getUserByUsername(username);
        userRepository.delete(userToDelete);
        return userToDelete;
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
