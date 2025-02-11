package com.projets.my_ticket.web.controllers;

import com.projets.my_ticket.domain.User;
import com.projets.my_ticket.dto.UserCreateDto;
import com.projets.my_ticket.mapper.UserMapper;
import com.projets.my_ticket.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<Page<User>> findAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(200).body(userService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<User> save(@Valid @RequestBody UserCreateDto userCreateDto) {
        User user = userMapper.toEntity(userCreateDto);
        return ResponseEntity.status(201).body(userService.create(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable UUID id, @Valid @RequestBody UserCreateDto userCreateDto) {
        User user = userMapper.toEntity(userCreateDto);
        return ResponseEntity.status(200).body(userService.update(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> findById(@PathVariable UUID id) {
        return ResponseEntity.status(200).body(userService.findById(id));
    }

}
