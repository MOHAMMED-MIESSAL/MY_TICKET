package com.projets.my_ticket.service.Implementations;

import com.projets.my_ticket.domain.Event;
import com.projets.my_ticket.exception.CustomValidationException;
import com.projets.my_ticket.repository.EventRepository;
import com.projets.my_ticket.service.CategoryService;
import com.projets.my_ticket.service.EventService;
import com.projets.my_ticket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EventImplementation implements EventService {

    private final EventRepository eventRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    @Override
    public Page<Event> findAll(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    @Override
    public Event create(Event event) {
        if (userService.findById(event.getUser().getId()).isEmpty()) {
            throw new CustomValidationException("User with id : " + event.getUser() + " not found");
        }
        if (categoryService.findById(event.getCategory().getId()).isEmpty()) {
            throw new CustomValidationException("Category with id : " + event.getCategory() + " not found");
        }

        return eventRepository.save(event);
    }

    @Override
    public Optional<Event> findById(UUID id) {
        return Optional.ofNullable(eventRepository.findById(id)
                .orElseThrow(() -> new CustomValidationException("Event with id : " + id + " not found")));
    }

    @Override
    public Event update(UUID id, Event event) {
        event.setId(id);
        return eventRepository.save(event);
    }

    @Override
    public void delete(UUID id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Page<Event> findAllByUserId(UUID userId, Pageable pageable) {
        return eventRepository.findAllByUserId(userId, pageable);
    }
}
