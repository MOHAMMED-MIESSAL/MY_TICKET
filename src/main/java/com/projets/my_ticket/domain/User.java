package com.projets.my_ticket.domain;

import com.projets.my_ticket.enums.Role;
import com.projets.my_ticket.enums.Sexe;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Username must not be null")
    @NotBlank(message = "Username must not be blank")
    private String username;

    @Column(unique = true)
    @NotBlank(message = "Email must not be blank")
    @NotNull(message = "Email must not be null")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @NotNull(message = "Password must not be null")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Sexe must not be null")
    private Sexe sexe;

    @NotNull(message = "Role must not be null")
    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities()
                .stream()
                .map(authority -> (GrantedAuthority) () -> authority)
                .toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
