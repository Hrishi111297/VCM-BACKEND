package com.bill.tech.dto_mapper;

import static com.bill.tech.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.bill.tech.entity.KeyValue;
import com.bill.tech.payload.request.KeyValueDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
/**
 * @author Hrishikesh Mahadik
 * @since 11/01/2025
 * @version 1.0
 *
 */
public class KeyValueMapper {

	/**
	 * This function will convert AddCallDto into optional AddCall Entity. <b>This
	 * function will return null if passed AddCallDto is null</b> <br>
	 * <b>Param</b> AddCallDto <br>
	 * <b>Return</b> AddCall
	 * 
	 * @since 11-09-2023
	 * @version 1.0
	 */
	public static final Function<KeyValueDto, Optional<KeyValue>> TO_KEYVALUE = e -> evalMapper(e, KeyValue.class);
	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<KeyValueDto>, List<KeyValue>> TO_KEYVALUE_COLLECTION = e -> e.stream()
			.map(dm -> TO_KEYVALUE.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert AddCall Entity into optional AddCallDto . <b>This
	 * function will return null if passed AddCall is null</b> <br>
	 * <b>Param</b> AddCall <br>
	 * <b>Return</b> AddCallDto
	 * 
	 * @since 11-09-2023
	 * @Version 1.0
	 */
	public static final Function<KeyValue, Optional<KeyValueDto>> TO_KEYVALUE_DTO = e -> evalMapper(e, KeyValueDto.class);

	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<KeyValue>, List<KeyValueDto>> TO_KEYVALUE_DTOS = e -> e.stream()
			.map(dm -> TO_KEYVALUE_DTO.apply(dm).get()).collect(Collectors.toList());

}
