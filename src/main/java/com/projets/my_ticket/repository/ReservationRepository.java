package com.projets.my_ticket.repository;

import com.projets.my_ticket.domain.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    Page<Reservation> findAll(Pageable pageable);
    Page<Reservation> findByUserId(UUID userId, Pageable pageable);
}
