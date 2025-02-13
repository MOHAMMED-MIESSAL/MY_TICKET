package com.projets.my_ticket.service.Implementations;

import com.projets.my_ticket.domain.Category;
import com.projets.my_ticket.exception.CustomValidationException;
import com.projets.my_ticket.repository.CategoryRepository;
import com.projets.my_ticket.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CategoryImplementation implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category create(Category category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new CustomValidationException("Category with name : " + category.getName() + " already exists");
        }
        return categoryRepository.save(category);
    }

    @Override
    public void delete(UUID id) {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Category with id : " + id + " not found");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public Optional<Category> findById(UUID id) {
        // Check if the category exists
        return Optional.ofNullable(categoryRepository.findById(id)
                .orElseThrow(() -> new CustomValidationException("Category with id : " + id + " not found")));
    }

    @Override
    public Category update(UUID id, Category category) {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Category with id : " + id + " not found");
        }
        category.setId(id);
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new CustomValidationException("Category with name : " + category.getName() + " already exists");
        }
        return categoryRepository.save(category);
    }
}
