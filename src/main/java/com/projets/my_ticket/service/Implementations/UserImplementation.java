package com.projets.my_ticket.service.Implementations;

import com.projets.my_ticket.domain.User;
import com.projets.my_ticket.exception.CustomValidationException;
import com.projets.my_ticket.repository.UserRepository;
import com.projets.my_ticket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserImplementation implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User create(User user) {
        // Check if the email is already used by another user
        userRepository.findByEmail(user.getEmail())
                .ifPresent(existingUser -> {
                    throw new CustomValidationException("User with email : " + user.getEmail() + " already exists");
                });
        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void delete(UUID id) {
        // Check if the user exists
        userRepository.findById(id)
                .orElseThrow(() -> new CustomValidationException("User with id : " + id + " not found"));
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findById(UUID id) {
        // Check if the user exists
        return Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new CustomValidationException("User with id : " + id + " not found")));
    }

    @Override
    public User update(UUID id, User user) {
        // Check if the user exists
        userRepository.findById(id)
                .orElseThrow(() -> new CustomValidationException("User with id : " + id + " not found"));

        // Check if the email is already used by another user
        userRepository.findByEmail(user.getEmail()).ifPresent(otherUser -> {
            if (!otherUser.getId().equals(id)) {
                throw new CustomValidationException("User with email : " + user.getEmail() + " already exists");
            }
        });

        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
