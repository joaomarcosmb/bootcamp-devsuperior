package com.devsuperior.bootcamp.services;

import com.devsuperior.bootcamp.dto.CategoryDTO;
import com.devsuperior.bootcamp.entities.Category;
import com.devsuperior.bootcamp.repositories.CategoryRepository;
import com.devsuperior.bootcamp.services.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    // This annotation informs that this method will be a transaction in the database,
    // following ACID principles, and betters performance
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> categories = repository.findAll();

        // Convert the list of Category to a list of CategoryDTO
        return categories.stream().map(CategoryDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Category category = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Entity not found")
        );

        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category = repository.save(category);
        return new CategoryDTO(category);
    }
}
