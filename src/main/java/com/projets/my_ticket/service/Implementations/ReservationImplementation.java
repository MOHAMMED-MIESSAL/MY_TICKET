package com.projets.my_ticket.service.Implementations;

import com.projets.my_ticket.domain.Reservation;
import com.projets.my_ticket.exception.CustomValidationException;
import com.projets.my_ticket.repository.ReservationRepository;
import com.projets.my_ticket.service.EventService;
import com.projets.my_ticket.service.ReservationService;
import com.projets.my_ticket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class ReservationImplementation implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final EventService eventService;


    @Override
    public Page<Reservation> findAll(Pageable pageable) {
        return reservationRepository.findAll(pageable);
    }

    @Override
    public Reservation create(Reservation reservation) {

        // Check if the user exists
        if (userService.findById(reservation.getUser().getId()).isEmpty()) {
            throw new CustomValidationException("Event with id : " + reservation.getEvent() + " not found");
        }

        // Check if the event exists
        if (eventService.findById(reservation.getEvent().getId()).isEmpty()) {
            throw new CustomValidationException("Event with id : " + reservation.getEvent() + " not found");
        }

        // Check if the quantity is between 1 and 3
        if (reservation.getNumberOfTickets() <= 0 || reservation.getNumberOfTickets() > 3) {
            throw new CustomValidationException("Number of tickets must be between 1 and 3");
        }

        return reservationRepository.save(reservation);
    }

    @Override
    public void delete(UUID id) {
        // Check if the reservation exists
        if (reservationRepository.findById(id).isEmpty()) {
            throw new CustomValidationException("Reservation with id : " + id + " not found");
        }
        reservationRepository.deleteById(id);
    }

    @Override
    public Optional<Reservation> findById(UUID id) {
        // Check if the reservation exists
        if (reservationRepository.findById(id).isEmpty()) {
            throw new CustomValidationException("Reservation with id : " + id + " not found");
        }
        return reservationRepository.findById(id);
    }

    @Override
    public Reservation update(UUID id, Reservation reservation) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new CustomValidationException("Reservation with id : " + id + " not found"));

        // Update the existing reservation
        existingReservation.setNumberOfTickets(reservation.getNumberOfTickets());
        existingReservation.setUser(reservation.getUser());
        existingReservation.setEvent(reservation.getEvent());

        return reservationRepository.save(existingReservation);
    }

    @Override
    public Page<Reservation> findByUserId(UUID userId, Pageable pageable) {
        return reservationRepository.findByUserId(userId, pageable);
    }

}
