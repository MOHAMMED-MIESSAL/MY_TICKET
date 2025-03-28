package com.projets.my_ticket.repository;

import com.projets.my_ticket.domain.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event,UUID> {
    Page<Event> findAllByUserId(UUID userId, Pageable pageable);
    Page<Event> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    List<Event> findTop3ByOrderByCreatedAtDesc();
    List<Event> findByCategoryId(UUID categoryId);
    List<Event> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
