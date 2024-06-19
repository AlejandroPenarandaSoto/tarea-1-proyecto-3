package com.project.demo.rest.category;

import com.project.demo.logic.entity.category.Category;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryRestController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Category addCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(category.getName());
                    return categoryRepository.save(existingCategory);
                })
                .orElseGet(() -> {
                    category.setCategoryId(id);
                    return categoryRepository.save(category);
                });
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public void deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
    }
}
