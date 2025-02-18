package com.projets.my_ticket.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


/**
 * DTO for creating a new Reservation.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreateDto {

    @NotNull(message = "Number of tickets must not be null")
    @Min(value = 1, message = "You must reserve at least 1 ticket")
    @Max(value = 3, message = "You cannot reserve more than 3 tickets")
    private Integer numberOfTickets;

    @NotNull(message = "User ID must not be null")
    private UUID userId;

    @NotNull(message = "Event ID must not be null")
    private UUID eventId;


}
