package com.example.service;


import com.projets.my_ticket.domain.Category;
import com.projets.my_ticket.exception.CustomValidationException;
import com.projets.my_ticket.repository.CategoryRepository;
import com.projets.my_ticket.service.Implementations.CategoryImplementation;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// This annotation is used to tell JUnit to use Mockito extension
@ExtendWith(MockitoExtension.class)
public class CategoryImplementationTest {

    // This annotation is used to create a mock object for the CategoryRepository
    @Mock
    private CategoryRepository categoryRepository;

    // This annotation is used to create an instance of the CategoryImplementation and inject the mock objects into it
    @InjectMocks
    private CategoryImplementation categoryService;

    private UUID categoryId;
    private Category category;

    // This annotation is used to run the method before each test
    @BeforeEach
    void setUp() {
        categoryId = UUID.randomUUID();
        category = new Category();
        category.setId(categoryId);
        category.setName("Test Category");
    }


    // This annotation is used to define the test method
    @Test
    void findAll_ShouldReturnPageOfCategories() { // This method is used to test the findAll method of the CategoryImplementation class
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> page = new PageImpl<>(List.of(category));

        // This is used to mock the findAll method of the CategoryRepository
        when(categoryRepository.findAll(pageable)).thenReturn(page);

        // This is used to call the findAll method of the CategoryImplementation
        Page<Category> result = categoryService.findAll(pageable);

        // This is used to verify the result
        assertEquals(1, result.getTotalElements());

        // This is used to verify the findAll method of the CategoryRepository
        verify(categoryRepository).findAll(pageable);
    }


    @Test
    void create_ShouldSaveCategory_WhenNameIsUnique() {
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.empty()); // This is used to mock the findByName method of the CategoryRepository
        when(categoryRepository.save(any(Category.class))).thenReturn(category); // This is used to mock the save method of the CategoryRepository

        Category savedCategory = categoryService.create(category); // This is used to call the create method of the CategoryImplementation

        assertNotNull(savedCategory); // This is used to verify that the savedCategory is not null
        assertEquals(category.getName(), savedCategory.getName()); // This is used to verify that the name of the savedCategory is equal to the name of the category
        verify(categoryRepository).save(category); // This is used to verify the save method of the CategoryRepository
    }


    @Test
    void create_ShouldThrowException_WhenNameAlreadyExists() {
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(category)); // This is used to mock the findByName method of the CategoryRepository

        assertThrows(CustomValidationException.class, () -> categoryService.create(category)); // This is used to assert that the create method of the CategoryImplementation throws a CustomValidationException

        verify(categoryRepository, never()).save(any(Category.class)); // This is used to verify that the save method of the CategoryRepository is never called
    }

    @Test
    void delete_ShouldDeleteCategory_WhenExists() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category)); // This is used to mock the findById method of the CategoryRepository
        doNothing().when(categoryRepository).deleteById(categoryId); // This is used to mock the deleteById method of the CategoryRepository

        categoryService.delete(categoryId); // This is used to call the delete method of the CategoryImplementation

        verify(categoryRepository).deleteById(categoryId); // This is used to verify the deleteById method of the CategoryRepository
    }

    @Test
    void delete_ShouldThrowException_WhenCategoryNotFound() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty()); // This is used to mock the findById method of the CategoryRepository

        assertThrows(EntityNotFoundException.class, () -> categoryService.delete(categoryId)); // This is used to assert that the delete method of the CategoryImplementation throws an EntityNotFoundException

        verify(categoryRepository, never()).deleteById(any(UUID.class)); // This is used to verify that the deleteById method of the CategoryRepository is never called
    }

    @Test
    void findById_ShouldReturnCategory_WhenExists() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category)); // This is used to mock the findById method of the CategoryRepository

        Optional<Category> result = categoryService.findById(categoryId); // This is used to call the findById method of the CategoryImplementation

        assertTrue(result.isPresent()); // This is used to verify that the result is present
        assertEquals(category.getName(), result.get().getName()); // This is used to verify that the name of the result is equal to the name of the category
        verify(categoryRepository).findById(categoryId); // This is used to verify the findById method of the CategoryRepository
    }

    @Test
    void findById_ShouldThrowException_WhenCategoryNotFound() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty()); // This is used to mock the findById method of the CategoryRepository

        assertThrows(CustomValidationException.class, () -> categoryService.findById(categoryId)); // This is used to assert that the findById method of the CategoryImplementation throws a CustomValidationException
    }

    @Test
    void update_ShouldUpdateCategory_WhenExistsAndNameIsUnique() {
        Category updatedCategory = new Category();
        updatedCategory.setId(categoryId);
        updatedCategory.setName("Updated Name");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category)); // This is used to mock the findById method of the CategoryRepository
        when(categoryRepository.findByName(updatedCategory.getName())).thenReturn(Optional.empty()); // This is used to mock the findByName method of the CategoryRepository
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory); // This is used to mock the save method of the CategoryRepository

        Category result = categoryService.update(categoryId, updatedCategory);

        assertNotNull(result); // This is used to verify that the result is not null
        assertEquals(updatedCategory.getName(), result.getName()); // This is used to verify that the name of the result is equal to the name of the updatedCategory
        verify(categoryRepository).save(updatedCategory); // This is used to verify the save method of the CategoryRepository
    }

    @Test
    void update_ShouldThrowException_WhenCategoryNotFound() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty()); // This is used to mock the findById method of the CategoryRepository

        assertThrows(EntityNotFoundException.class, () -> categoryService.update(categoryId, category)); // This is used to assert that the update method of the CategoryImplementation throws an EntityNotFoundException

        verify(categoryRepository, never()).save(any(Category.class)); // This is used to verify that the save method of the CategoryRepository is never called
    }

    @Test
    void update_ShouldThrowException_WhenNameAlreadyExists() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category)); // This is used to mock the findById method of the CategoryRepository
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(new Category())); // This is used to mock the findByName method of the CategoryRepository

        assertThrows(CustomValidationException.class, () -> categoryService.update(categoryId, category)); // This is used to assert that the update method of the CategoryImplementation throws a CustomValidationException

        verify(categoryRepository, never()).save(any(Category.class)); // This is used to verify that the save method of the CategoryRepository is never called
    }

}
