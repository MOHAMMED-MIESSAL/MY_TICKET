package com.projets.my_ticket.service;

import com.projets.my_ticket.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Page<User> findAll(Pageable pageable);
    User create(User user);
    User update(UUID id, User user);
    Optional<User> findById(UUID id);
    void delete(UUID id);
}
