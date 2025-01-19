package com.bill.tech.dto_mapper;

import static com.bill.tech.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.bill.tech.entity.Education;
import com.bill.tech.payload.request.EducationDto;

public class EducationMapper {

	EducationMapper() {

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
	public static final Function<EducationDto, Optional<Education>> TO_EDUCATION = e -> evalMapper(e, Education.class);
	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<EducationDto>, List<Education>> TO_EDUCATION_COLLECTION = e -> e.stream()
			.map(dm -> TO_EDUCATION.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert AddCall Entity into optional AddCallDto . <b>This
	 * function will return null if passed AddCall is null</b> <br>
	 * <b>Param</b> AddCall <br>
	 * <b>Return</b> AddCallDto
	 * 
	 * @since 11-09-2023
	 * @Version 1.0
	 */
	public static final Function<Education, Optional<EducationDto>> TO_EDUCATION_DTO = e -> evalMapper(e,
			EducationDto.class);

	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<Education>, List<EducationDto>> TO_EDUCATION_DTO_COLLECTION = e -> e
			.stream().map(dm -> TO_EDUCATION_DTO.apply(dm).get()).collect(Collectors.toList());

}
