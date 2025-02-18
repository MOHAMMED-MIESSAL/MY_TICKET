package com.projets.my_ticket.web.controllers;


import com.projets.my_ticket.domain.Reservation;
import com.projets.my_ticket.dto.ReservationCreateDto;
import com.projets.my_ticket.mapper.ReservationMapper;
import com.projets.my_ticket.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;


    @GetMapping
    public ResponseEntity<Page<Reservation>> findAll(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(reservationService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody ReservationCreateDto reservationCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservationService.create(reservationMapper.toEntity(reservationCreateDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> update(@PathVariable UUID id, @RequestBody ReservationCreateDto reservationCreateDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(reservationService.update(id, reservationMapper.toEntity(reservationCreateDto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Reservation>> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(reservationService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        reservationService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
