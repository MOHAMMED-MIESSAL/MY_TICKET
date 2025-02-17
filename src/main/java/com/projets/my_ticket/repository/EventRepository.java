package com.projets.my_ticket.repository;

import com.projets.my_ticket.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event,UUID> {
}
