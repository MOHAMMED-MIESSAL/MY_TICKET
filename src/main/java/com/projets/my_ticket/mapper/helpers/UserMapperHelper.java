package com.projets.my_ticket.mapper.helpers;

import com.projets.my_ticket.domain.User;
import com.projets.my_ticket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserMapperHelper {

   private final UserRepository userRepository;

    public User toUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
