package com.projets.my_ticket.mapper.helpers;

import com.projets.my_ticket.domain.Category;
import com.projets.my_ticket.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CategoryMapperHelper {

    private final CategoryRepository categoryRepository;

    public Category toCategory(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
