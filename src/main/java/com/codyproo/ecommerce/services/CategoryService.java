package com.codyproo.ecommerce.services;

import com.codyproo.ecommerce.dtos.category.CategoryDto;
import com.codyproo.ecommerce.dtos.category.CreateCategoryDto;
import com.codyproo.ecommerce.dtos.common.PageableEntitiesDto;
import com.codyproo.ecommerce.dtos.category.UpdateCategoryDto;
import com.codyproo.ecommerce.entities.Category;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CreateCategoryDto dto) throws Exception;

    List<CategoryDto> getAllCategories();

    PageableEntitiesDto<CategoryDto> getPageableCategories(int page, int pageSize);

    void deleteCategoryById(Long id) throws Exception;

    CategoryDto updateCategoryById(Long id, UpdateCategoryDto dto) throws Exception;

    Category getCategoryById(Long id) throws Exception;
}
