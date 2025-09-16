package com.codyproo.ecommerce.services.impl;

import com.codyproo.ecommerce.common.exceptions.BadRequestException;
import com.codyproo.ecommerce.common.exceptions.DuplicateException;
import com.codyproo.ecommerce.common.exceptions.NotFoundException;
import com.codyproo.ecommerce.dtos.product.CreateProductDto;
import com.codyproo.ecommerce.dtos.common.PageableEntitiesDto;
import com.codyproo.ecommerce.dtos.product.ProductDto;
import com.codyproo.ecommerce.dtos.product.UploadProductImageDto;
import com.codyproo.ecommerce.entities.Product;
import com.codyproo.ecommerce.mapper.ProductMapper;
import com.codyproo.ecommerce.repositories.ProductRepository;
import com.codyproo.ecommerce.services.CategoryService;
import com.codyproo.ecommerce.services.ProductService;
import com.codyproo.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Value("${app.upload-dir}")
    private String uploadDir;

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, UserService userService, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.userService = userService;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDto createProduct(Long userId, CreateProductDto dto) throws Exception {
        _validateNewProductExistence(dto.getName());
        var category = categoryService.getCategoryById(dto.getCategoryId());
        var user = userService.getUserById(userId);
        var product = Product.builder()
                .name(dto.getName())
                .category(category)
                .user(user)
                .price(dto.getPrice())
                .image(dto.getImage())
                .description(dto.getDescription())
                .build();
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getProducts() {
        var sort = Sort.by("createdAt").descending();
        var products = productRepository.findAll(sort);
        return productMapper.toDtos(products);
    }

    @Override
    public PageableEntitiesDto<ProductDto> getPageableProducts(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        var paginatedProducts = productRepository.findAll(pageable);
        var products = productMapper.toDtos(paginatedProducts.getContent());
        return new PageableEntitiesDto<>(paginatedProducts.getTotalElements(), paginatedProducts.getTotalPages(), products);
    }

    @Override
    public UploadProductImageDto uploadProductImage(MultipartFile inputFile) throws Exception {
        if (inputFile.isEmpty()) {
            throw new BadRequestException("file is not selected");
        }
        var pathExtIndex = inputFile.getOriginalFilename().lastIndexOf(".");
        if (pathExtIndex < 0) {
            throw new BadRequestException("file is invalid");
        }
        var extName = inputFile.getOriginalFilename().substring(pathExtIndex);
        var fileName = new Date(System.currentTimeMillis()).getTime() + "-" + UUID.randomUUID() + extName;
        var filePath = Path.of(uploadDir + fileName);
        Files.copy(inputFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return new UploadProductImageDto(fileName);
    }

    @Override
    public Product getProductById(Long productId) throws Exception {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("product not found"));
    }

    private void _validateNewProductExistence(String name) throws Exception {
        boolean isProductNameDuplicated = productRepository.existsByName(name);
        if (isProductNameDuplicated) {
            throw new DuplicateException("product name is already exists");
        }
    }
}
