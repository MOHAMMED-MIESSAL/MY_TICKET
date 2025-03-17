package com.projets.my_ticket.service;

import com.projets.my_ticket.domain.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ReservationService {
    Page<Reservation> findAll(Pageable pageable);
    Reservation create(Reservation reservation);
    Reservation update(UUID id, Reservation reservation);
    Optional<Reservation> findById(UUID id);
    void delete(UUID id);
    Page<Reservation> findByUserId(UUID userId, Pageable pageable);
}
