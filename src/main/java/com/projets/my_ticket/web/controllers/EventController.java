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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
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

    @GetMapping("/search")
    public ResponseEntity<Page<Event>> findByTitle(@RequestParam(required = false) String title,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (title == null || title.isEmpty()) {
            return ResponseEntity.ok(eventService.findAll(pageable));
        } else {
            return ResponseEntity.ok(eventService.findByTitle(title, pageable));
        }
    }

    @GetMapping("/getLast3events")
    public List<Event> getLast3events() {
        return eventService.findLatestEvents();
    }

    @GetMapping("/getEventsByCategory")
    public ResponseEntity<List<Event>> getEventsByCategory(@RequestParam(required = false) UUID categoryId) {
        List<Event> events;
        if (categoryId == null) {
            events = eventService.findAll(PageRequest.of(0, 10)).getContent();
        } else {
            events = eventService.findByCategoryId(categoryId);
        }
        return ResponseEntity.ok(events);
    }

    @GetMapping("/getEventsByDate")
    public ResponseEntity<List<Event>> getEventsByDateRange(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        List<Event> events = eventService.getEventsByDateRange(startDate, endDate);
        return ResponseEntity.ok(events);
    }



}
