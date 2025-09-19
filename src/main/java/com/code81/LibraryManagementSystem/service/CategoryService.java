package com.code81.LibraryManagementSystem.service;

import com.code81.LibraryManagementSystem.dto.request.CategoryRequest;
import com.code81.LibraryManagementSystem.dto.response.CategoryResponse;
import com.code81.LibraryManagementSystem.entity.Category;
import com.code81.LibraryManagementSystem.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserActivityLogService userActivityLogService;

    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.name());

        if (request.parentId() != null) {
            Category parent = categoryRepository.findById(request.parentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found with id " + request.parentId()));
            category.setParent(parent);
        }

        Category saved = categoryRepository.save(category);

        userActivityLogService.saveLogAction("Adding category " + saved.getName());
        return mapToCategoryResponse(saved);

    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToCategoryResponse).toList();

    }

    @Transactional
    public CategoryResponse updateCategory(Integer categoryId, CategoryRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + categoryId));

        category.setName(request.name());


        Category updated = categoryRepository.save(category);

        userActivityLogService.saveLogAction("Updating category " + updated.getName());

        return mapToCategoryResponse(updated);
    }


    @Transactional
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + categoryId));


        if (category.getBooks() != null) {
            category.getBooks().forEach(book -> book.getCategories().remove(category));
        }
        userActivityLogService.saveLogAction("Deleting category " + category.getName());
        categoryRepository.delete(category);
    }

    private CategoryResponse mapToCategoryResponse(Category category) {
        return new CategoryResponse(
                category.getCategoryId(),
                category.getName(),
                category.getParent() != null ? category.getParent().getCategoryId() : null
        );
    }
}