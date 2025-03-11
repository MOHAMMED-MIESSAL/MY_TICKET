package com.projets.my_ticket.web.controllers;


import com.projets.my_ticket.domain.Event;
import com.projets.my_ticket.dto.EventCreateDto;
import com.projets.my_ticket.mapper.EventMapper;
import com.projets.my_ticket.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

/**
 * EventController class
 * ---------------------
 * Infos:
 * I can create, read, update and delete events
 * But I used @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) to avoid infinite recursion
 * So later I will use EventResponseDto to avoid this problem and to show only the necessary information
 */

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public ResponseEntity<Page<Event>> findAll(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(eventService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Event> save(@RequestBody @Valid EventCreateDto eventCreateDto) {
        Event event = eventMapper.toEntity(eventCreateDto);
        return ResponseEntity.status(201).body(eventService.create(event));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> update(@PathVariable UUID id, @RequestBody EventCreateDto eventCreateDto) {
        Event event = eventMapper.toEntity(eventCreateDto);
        return ResponseEntity.ok(eventService.update(id, event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Event>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Event>> findAllByUserId(@PathVariable UUID userId,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(eventService.findAllByUserId(userId, pageable));
    }
}
