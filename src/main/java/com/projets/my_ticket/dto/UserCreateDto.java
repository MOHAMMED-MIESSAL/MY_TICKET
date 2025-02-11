package com.projets.my_ticket.dto;

import com.projets.my_ticket.enums.Role;
import com.projets.my_ticket.enums.Sexe;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO for creating a new User.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Sexe must not be null")
    private Sexe sexe;

    @NotNull(message = "Role must not be null")
    @Enumerated(EnumType.STRING)
    private Role role;

}
