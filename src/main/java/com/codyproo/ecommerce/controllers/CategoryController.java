package com.codyproo.ecommerce.controllers;

import com.codyproo.ecommerce.dtos.category.CategoryDto;
import com.codyproo.ecommerce.dtos.category.CreateCategoryDto;
import com.codyproo.ecommerce.dtos.common.PageableEntitiesDto;
import com.codyproo.ecommerce.dtos.category.UpdateCategoryDto;
import com.codyproo.ecommerce.services.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CreateCategoryDto dto) throws Exception {
        var response = categoryService.createCategory(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
        var response = categoryService.getAllCategories();
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/page")
    public ResponseEntity<PageableEntitiesDto<CategoryDto>> getCategoriesPage(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        var response = categoryService.getPageableCategories(page, pageSize);
        return ResponseEntity.ok().body(response);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(name = "id") Long id) throws Exception {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<CategoryDto> editCategory(@PathVariable(name = "id") Long id, @Valid @RequestBody UpdateCategoryDto dto) throws Exception {
        var response = categoryService.updateCategoryById(id, dto);
        return ResponseEntity.ok().body(response);
    }
}
