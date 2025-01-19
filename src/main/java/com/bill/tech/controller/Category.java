package com.bill.tech.controller;
import static com.bill.tech.constants.ApiConstants.CATEGORY;
import static com.bill.tech.constants.ApiConstants.CREATE_CATEGORY;
import static com.bill.tech.constants.ApiConstants.CATEGORY;
import static com.bill.tech.constants.ApiConstants.CREATE_CATEGORY;
import static com.bill.tech.constants.ApiConstants.UPDATE_CATEGORY;
import static com.bill.tech.constants.ApiConstants.DELETE_CATEGORY;
import static com.bill.tech.constants.ApiConstants.GET_CATEGORY;
import static com.bill.tech.constants.ApiConstants.GET_ALL_CATEGORIES;

import java.util.EnumMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bill.tech.enums.ApiResponse;
import com.bill.tech.payload.request.CategoryDto;
import com.bill.tech.service.CategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
/**
 * @author Hrishikesh Mahadik
 * @since 11/01/2025
 * @version 1.0
 *
 */
@RequestMapping(CATEGORY)
@Tag(name = "CATEGORY", description = "This Section Gives Us The API Endpoint Related To The CATEGORY")

@RequiredArgsConstructor
@RestController
public class Category {
    private final CategoryService categoryService;
	
    @PostMapping(CREATE_CATEGORY)
    public ResponseEntity<EnumMap<ApiResponse, Object>> createCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }

    @GetMapping(GET_ALL_CATEGORIES)
    public ResponseEntity<EnumMap<ApiResponse, Object>> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping(GET_CATEGORY)
    public ResponseEntity<EnumMap<ApiResponse, Object>> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping(UPDATE_CATEGORY)
    public ResponseEntity<EnumMap<ApiResponse, Object>> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(id, categoryDto);
    }

    @DeleteMapping(DELETE_CATEGORY)
    public ResponseEntity<EnumMap<ApiResponse, Object>> deleteCategory(@PathVariable Long id) {
      return   categoryService.deleteCategory(id);
       
    }
}







