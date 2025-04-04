package com.projets.my_ticket.service;

import com.projets.my_ticket.domain.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventService {
    Page<Event> findAll(Pageable pageable);
    Event create(Event event);
    Event update(UUID id ,Event event);
    Optional<Event> findById(UUID id);
    void delete(UUID id);
    Page<Event> findAllByUserId(UUID userId, Pageable pageable);
    Page<Event> findByTitle(String title, Pageable pageable);
    List<Event> findLatestEvents();
    List<Event> findByCategoryId(UUID categoryId);
    List<Event> getEventsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}
