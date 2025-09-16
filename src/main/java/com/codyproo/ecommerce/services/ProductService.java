package com.codyproo.ecommerce.services;

import com.codyproo.ecommerce.dtos.product.CreateProductDto;
import com.codyproo.ecommerce.dtos.common.PageableEntitiesDto;
import com.codyproo.ecommerce.dtos.product.ProductDto;
import com.codyproo.ecommerce.dtos.product.UploadProductImageDto;
import com.codyproo.ecommerce.entities.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(Long userId, CreateProductDto dto) throws Exception;

    List<ProductDto> getProducts();

    PageableEntitiesDto<ProductDto> getPageableProducts(int page, int pageSize);

    UploadProductImageDto uploadProductImage(MultipartFile file) throws Exception;

    Product getProductById(Long productId) throws Exception;
}
