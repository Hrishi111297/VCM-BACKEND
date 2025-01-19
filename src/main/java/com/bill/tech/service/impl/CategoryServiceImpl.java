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
import com.bill.tech.enums.ApiResponse;
import com.bill.tech.exception.ResourceNotFound;
import com.bill.tech.payload.request.CategoryDto;
import com.bill.tech.repository.CategoryRepo;
import com.bill.tech.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    @Override
    public ResponseEntity<EnumMap<ApiResponse, Object>> addCategory(CategoryDto categoryDto) {
        log.info("Inside addCategory method...{}", categoryDto);
        EnumMap<ApiResponse, Object> response = new EnumMap<>(ApiResponse.class);

        Category category = TO_CATEGORY.apply(categoryDto)
                .orElseThrow(() -> new ResourceNotFound("Category"));

        Category savedCategory = categoryRepo.save(category);

        if (savedCategory != null) {
            response.put(ApiResponse.SUCCESS, true);
            response.put(ApiResponse.DATA, TO_CATEGORY_DTO.apply(savedCategory));
            response.put(ApiResponse.MESSAGE, "Category Added Successfully");
        } else {
            response.put(ApiResponse.SUCCESS, false);
            response.put(ApiResponse.MESSAGE, "Category Not Added");
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<EnumMap<ApiResponse, Object>> getAllCategories() {
        log.info("Fetching all categories...");
        EnumMap<ApiResponse, Object> response = new EnumMap<>(ApiResponse.class);
        
        response.put(ApiResponse.SUCCESS, true);
        response.put(ApiResponse.DATA, TO_CATEGORY_DTOS.apply(categoryRepo.findAll()));
        response.put(ApiResponse.MESSAGE, "Category Fetched Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EnumMap<ApiResponse, Object>> getCategoryById(Long id) {
        log.info("Fetching category by ID...{}", id);
        EnumMap<ApiResponse, Object> response = new EnumMap<>(ApiResponse.class);

        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Category", "id", id.toString()));

        response.put(ApiResponse.SUCCESS, true);
        response.put(ApiResponse.DATA, TO_CATEGORY_DTO.apply(category));
        response.put(ApiResponse.MESSAGE, "Category Fetched Successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EnumMap<ApiResponse, Object>> updateCategory(Long id, CategoryDto categoryDto) {
        log.info("Updating category...{}", id);
        EnumMap<ApiResponse, Object> response = new EnumMap<>(ApiResponse.class);

        Category existingCategory = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Category", "id", id.toString()));

        existingCategory.setName(categoryDto.getName());
        existingCategory.setDescription(categoryDto.getDescription());

        Category updatedCategory = categoryRepo.save(existingCategory);

        response.put(ApiResponse.SUCCESS, true);
        response.put(ApiResponse.DATA, TO_CATEGORY_DTO.apply(updatedCategory));
        response.put(ApiResponse.MESSAGE, "Category Updated Successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<EnumMap<ApiResponse, Object>> deleteCategory(Long id) {
        log.info("Deleting category...{}", id);
        EnumMap<ApiResponse, Object> response = new EnumMap<>(ApiResponse.class);

        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Category", "id", id.toString()));

        categoryRepo.delete(category);

        response.put(ApiResponse.SUCCESS, true);
        response.put(ApiResponse.MESSAGE, "Category Deleted Successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
