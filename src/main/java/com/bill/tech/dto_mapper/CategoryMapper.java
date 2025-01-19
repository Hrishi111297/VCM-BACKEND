package com.bill.tech.dto_mapper;

import static com.bill.tech.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.bill.tech.entity.Category;
import com.bill.tech.payload.request.CategoryDto;

public class CategoryMapper {
	CategoryMapper() {

	}

	/**
	 * This function will convert AddCallDto into optional AddCall Entity. <b>This
	 * function will return null if passed AddCallDto is null</b> <br>
	 * <b>Param</b> AddCallDto <br>
	 * <b>Return</b> AddCall
	 * 
	 * @since 11-09-2023
	 * @version 1.0
	 */
	public static final Function<CategoryDto, Optional<Category>> TO_CATEGORY = e -> evalMapper(e, Category.class);
	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<CategoryDto>, List<Category>> TO_CATEGORY_COLLECTION = e -> e.stream()
			.map(dm -> TO_CATEGORY.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert AddCall Entity into optional AddCallDto . <b>This
	 * function will return null if passed AddCall is null</b> <br>
	 * <b>Param</b> AddCall <br>
	 * <b>Return</b> AddCallDto
	 * 
	 * @since 11-09-2023
	 * @Version 1.0
	 */
	public static final Function<Category, Optional<CategoryDto>> TO_CATEGORY_DTO = e -> evalMapper(e, CategoryDto.class);

	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<Category>, List<CategoryDto>> TO_CATEGORY_DTOS = e -> e.stream()
			.map(dm -> TO_CATEGORY_DTO.apply(dm).get()).collect(Collectors.toList());
}
