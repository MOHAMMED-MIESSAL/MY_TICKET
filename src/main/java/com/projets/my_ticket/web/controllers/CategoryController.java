package com.projets.my_ticket.web.controllers;

import com.projets.my_ticket.domain.Category;
import com.projets.my_ticket.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<Category>> findAll(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(categoryService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Category> save(@Valid  @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.create(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable UUID id, @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.update(id, category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Category>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

}
