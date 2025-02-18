package com.projets.my_ticket.mapper.helpers;


import com.projets.my_ticket.domain.Event;
import com.projets.my_ticket.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EventMapperHelper {

    private final EventRepository eventRepository;

    public Event toEvent(UUID eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }
}
