package com.projets.my_ticket.service;

import com.projets.my_ticket.domain.User;

import java.util.Optional;

public interface AuthService {
    void register(User user);
    Optional<User> login(String email, String password);
}
