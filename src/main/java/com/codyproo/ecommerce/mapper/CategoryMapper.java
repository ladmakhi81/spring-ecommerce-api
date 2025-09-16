package com.codyproo.ecommerce.mapper;

import com.codyproo.ecommerce.dtos.category.CategoryDto;
import com.codyproo.ecommerce.dtos.category.UpdateCategoryDto;
import com.codyproo.ecommerce.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
    List<CategoryDto> toDtos(List<Category> categories);

    void toCategory(UpdateCategoryDto dto, @MappingTarget Category category);
}
