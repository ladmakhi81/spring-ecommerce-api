package com.codyproo.ecommerce.mapper;

import com.codyproo.ecommerce.dtos.product.ProductDto;
import com.codyproo.ecommerce.entities.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);

    List<ProductDto> toDtos(List<Product> products);
}
