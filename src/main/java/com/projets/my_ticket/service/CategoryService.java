package com.projets.my_ticket.service;

import com.projets.my_ticket.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CategoryService {
    Page<Category> findAll(Pageable pageable);
    Category create(Category category);
    Category update(UUID id,Category category);
    Optional<Category> findById(UUID id);
    void delete(UUID id);
}
