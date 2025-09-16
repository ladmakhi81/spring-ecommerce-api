package com.codyproo.ecommerce.controllers;

import com.codyproo.ecommerce.dtos.common.SecuredUserDetailsDto;
import com.codyproo.ecommerce.dtos.product.CreateProductDto;
import com.codyproo.ecommerce.dtos.common.PageableEntitiesDto;
import com.codyproo.ecommerce.dtos.product.ProductDto;
import com.codyproo.ecommerce.dtos.product.UploadProductImageDto;
import com.codyproo.ecommerce.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody CreateProductDto dto,
            @AuthenticationPrincipal SecuredUserDetailsDto loggedInUser
    ) throws Exception {
        var response = productService.createProduct(loggedInUser.getId(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        var response = productService.getProducts();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadProductImageDto> uploadImage(
            @RequestParam("file") MultipartFile file) throws Exception {
        var response = productService.uploadProductImage(file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/page")
    public ResponseEntity<PageableEntitiesDto<ProductDto>> getProductsPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        var response = productService.getPageableProducts(page, pageSize);
        return ResponseEntity.ok().body(response);
    }
}
