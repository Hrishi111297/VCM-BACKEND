package com.bill.tech.service;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;

import com.bill.tech.enums.ApiResponseEnum;
import com.bill.tech.payload.request.CategoryDto;

public interface CategoryService {

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> deleteCategory(Long id);

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> updateCategory(Long id, CategoryDto categoryDto);

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> getCategoryById(Long id);

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> getAllCategories();

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> addCategory(CategoryDto categoryDto);

}
