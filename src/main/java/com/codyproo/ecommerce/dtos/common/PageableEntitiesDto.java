package com.codyproo.ecommerce.dtos.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageableEntitiesDto<T> {
    private Long totalCount;
    private int totalPage;
    private List<T> categories;
}
