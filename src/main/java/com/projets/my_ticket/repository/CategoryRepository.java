package com.projets.my_ticket.repository;

import com.projets.my_ticket.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Page<Category> findAll(Pageable pageable);
    Optional<Category> findByName( String name);
}
