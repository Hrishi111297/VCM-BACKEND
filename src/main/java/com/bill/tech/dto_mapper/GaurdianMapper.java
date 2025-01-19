package com.bill.tech.dto_mapper;

import static com.bill.tech.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.bill.tech.entity.GuardianDetail;
import com.bill.tech.payload.request.GuardianDetailDto;

public class GaurdianMapper {
	GaurdianMapper() {

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
	public static final Function<GuardianDetailDto, Optional<GuardianDetail>> TO_GUARDIAN = e -> evalMapper(e, GuardianDetail.class);
	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<GuardianDetailDto>, List<GuardianDetail>> TO_GUARDIAN_COLLECTION = e -> e.stream()
			.map(dm -> TO_GUARDIAN.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert AddCall Entity into optional AddCallDto . <b>This
	 * function will return null if passed AddCall is null</b> <br>
	 * <b>Param</b> AddCall <br>
	 * <b>Return</b> AddCallDto
	 * 
	 * @since 11-09-2023
	 * @Version 1.0
	 */
	public static final Function<GuardianDetail, Optional<GuardianDetailDto>> TO_GUARDIAN_DTO = e -> evalMapper(e, GuardianDetailDto.class);

	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<GuardianDetail>, List<GuardianDetailDto>> TO_GUARDIAN_DTOS = e -> e.stream()
			.map(dm -> TO_GUARDIAN_DTO.apply(dm).get()).collect(Collectors.toList());

}
