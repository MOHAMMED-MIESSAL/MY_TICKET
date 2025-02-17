package com.projets.my_ticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for creating a new Event.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventCreateDto {

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Description must not be blank")
    private String description;

    @NotBlank(message = "City must not be blank")
    private String city;

    @NotBlank(message = "Location must not be blank")
    private String location;

    @NotNull(message = "Date must not be null")
    private LocalDateTime date;

    @NotNull(message = "Capacity must not be null")
    private Integer capacity;

    @NotNull(message = "Available seats must not be null")
    private Integer available_seats;

    @NotNull(message = "Price must not be null")
    private Double price;

    @NotNull(message = "Category ID must not be null")
    private UUID categoryId;

    @NotNull(message = "User ID must not be null")
    private UUID userId;
}
