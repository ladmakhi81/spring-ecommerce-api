package com.codyproo.ecommerce.services.impl;

import com.codyproo.ecommerce.common.exceptions.DuplicateException;
import com.codyproo.ecommerce.common.exceptions.NotFoundException;
import com.codyproo.ecommerce.dtos.category.CategoryDto;
import com.codyproo.ecommerce.dtos.category.CreateCategoryDto;
import com.codyproo.ecommerce.dtos.common.PageableEntitiesDto;
import com.codyproo.ecommerce.dtos.category.UpdateCategoryDto;
import com.codyproo.ecommerce.entities.Category;
import com.codyproo.ecommerce.mapper.CategoryMapper;
import com.codyproo.ecommerce.repositories.CategoryRepository;
import com.codyproo.ecommerce.services.CategoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        var sort = Sort.by("createdAt").descending();
        var categories = categoryRepository.findAll(sort);
        return categoryMapper.toDtos(categories);
    }

    @Override
    public PageableEntitiesDto<CategoryDto> getPageableCategories(int page, int pageSize) {
        var pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        var categoriesPageable = categoryRepository.findAll(pageable);
        var categoriesDto = categoryMapper.toDtos(categoriesPageable.getContent());
        return new PageableEntitiesDto<>(categoriesPageable.getTotalElements(), categoriesPageable.getTotalPages(), categoriesDto);
    }

    @Override
    public void deleteCategoryById(Long id) throws Exception {
        var category = getCategoryById(id);
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDto updateCategoryById(Long id, UpdateCategoryDto dto) throws Exception {
        var category = getCategoryById(id);
        categoryMapper.toCategory(dto, category);
        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public Category getCategoryById(Long id) throws Exception {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("category not found"));
    }


    @Override
    public CategoryDto createCategory(CreateCategoryDto dto) throws Exception {
        _validateNewCategoryExistence(dto.getName());
        var category = new Category(dto.getName());
        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    private void _validateNewCategoryExistence(String name) throws Exception {
        boolean isCategoryDuplicated = categoryRepository.existsByName(name);
        if (isCategoryDuplicated) {
            throw new DuplicateException("category name is already exists by name");
        }
    }
}
