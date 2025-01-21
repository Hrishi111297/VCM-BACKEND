package com.bill.tech.service.impl;

import static com.bill.tech.dto_mapper.CategoryMapper.TO_CATEGORY;
import static com.bill.tech.dto_mapper.CategoryMapper.TO_CATEGORY_DTO;
import static com.bill.tech.dto_mapper.CategoryMapper.TO_CATEGORY_DTOS;

import java.util.EnumMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bill.tech.entity.Category;
import com.bill.tech.enums.ApiResponseEnum;
import com.bill.tech.exception.ResourceNotFound;
import com.bill.tech.payload.request.CategoryDto;
import com.bill.tech.payload.response.ApiResponse;
import com.bill.tech.repository.CategoryRepo;
import com.bill.tech.service.CategoryService;
import static com.bill.tech.constants.ApiMessages.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepo categoryRepo;

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> addCategory(CategoryDto categoryDto) {
		log.info("Inside addCategory method...{}", categoryDto);
		Category category = TO_CATEGORY.apply(categoryDto).orElseThrow(() -> new ResourceNotFound(CATEGORY));
		Category savedCategory = categoryRepo.save(category);
		return ApiResponse.buildApiResponse(TO_CATEGORY_DTO.apply(savedCategory), CATEGORY + ADDED, HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getAllCategories() {
		log.info("Fetching all categories...");
		return ApiResponse.buildApiResponse(TO_CATEGORY_DTOS.apply(categoryRepo.findAll()), CATEGORY + FETCHED,
				HttpStatus.OK);

	}

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getCategoryById(Long id) {
		log.info("Fetching category by ID...{}", id);
		Category category = getCatogory(id);
		return ApiResponse.buildApiResponse(TO_CATEGORY_DTO.apply(category), CATEGORY + FETCHED, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> updateCategory(Long id, CategoryDto categoryDto) {
		log.info("Updating category...{}", id);
		Category existingCategory = getCatogory(id);
		existingCategory.setName(categoryDto.getName());
		existingCategory.setDescription(categoryDto.getDescription());
		Category updatedCategory = categoryRepo.save(existingCategory);
		return ApiResponse.buildApiResponse(TO_CATEGORY_DTO.apply(updatedCategory), CATEGORY + UPDATED, HttpStatus.OK);

	}

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> deleteCategory(Long id) {
		log.info("Deleting category...{}", id);
		Category category = getCatogory(id);
		categoryRepo.delete(category);
		return ApiResponse.buildApiResponse(null, CATEGORY + DELETED, HttpStatus.OK);

	}

	private Category getCatogory(Long id) {
		return categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFound(CATEGORY, "id", id.toString()));
	}
}
