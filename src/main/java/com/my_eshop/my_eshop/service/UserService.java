package com.my_eshop.my_eshop.service;

import com.my_eshop.my_eshop.entity.User;
import com.my_eshop.my_eshop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        // default role αν δεν έχει σταλεί
        if (user.getRole() == null) {
            user.setRole(1);
        }
        return userRepository.save(user);
    }

    public User updateUser(Long id, User newUser) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setEmail(newUser.getEmail());
                    existing.setUsername(newUser.getUsername());
                    existing.setPasswordHash(newUser.getPasswordHash());
                    existing.setRole(newUser.getRole() != null ? newUser.getRole() : existing.getRole());
                    return userRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
