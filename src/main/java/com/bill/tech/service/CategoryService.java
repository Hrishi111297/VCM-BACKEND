package com.bill.tech.service;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;

import com.bill.tech.enums.ApiResponse;
import com.bill.tech.payload.request.CategoryDto;

public interface CategoryService {

	ResponseEntity<EnumMap<ApiResponse, Object>> deleteCategory(Long id);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateCategory(Long id, CategoryDto categoryDto);

	ResponseEntity<EnumMap<ApiResponse, Object>> getCategoryById(Long id);

	ResponseEntity<EnumMap<ApiResponse, Object>> getAllCategories();

	ResponseEntity<EnumMap<ApiResponse, Object>> addCategory(CategoryDto categoryDto);

}
