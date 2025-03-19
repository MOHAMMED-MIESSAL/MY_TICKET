package com.projets.my_ticket.repository;

import com.projets.my_ticket.domain.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event,UUID> {
    Page<Event> findAllByUserId(UUID userId, Pageable pageable);
    Page<Event> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
