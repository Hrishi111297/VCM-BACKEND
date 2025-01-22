package com.bill.tech.dto_mapper;

import static com.bill.tech.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.bill.tech.entity.Address;
import com.bill.tech.payload.request.AddressDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
/**
 * @author Hrishikesh Mahadik
 * @since 11/01/2025
 * @version 1.0
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdressMapper {


	/**
	 * This function will convert AddCallDto into optional AddCall Entity. <b>This
	 * function will return null if passed AddCallDto is null</b> <br>
	 * <b>Param</b> AddCallDto <br>
	 * <b>Return</b> AddCall
	 * 
	 * @since 11-09-2023
	 * @version 1.0
	 */
	public static final Function<AddressDto, Optional<Address>> TO_ADDRESS = e -> evalMapper(e, Address.class);
	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<AddressDto>, List<Address>> TO_ADDRESS_COLLECTION = e -> e.stream()
			.map(dm -> TO_ADDRESS.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert AddCall Entity into optional AddCallDto . <b>This
	 * function will return null if passed AddCall is null</b> <br>
	 * <b>Param</b> AddCall <br>
	 * <b>Return</b> AddCallDto
	 * 
	 * @since 11-09-2023
	 * @Version 1.0
	 */
	public static final Function<Address, Optional<AddressDto>> TO_ADDRESS_DTO = e -> evalMapper(e, AddressDto.class);

	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<Address>, List<AddressDto>> TO_ADDRESS_DTOS = e -> e.stream()
			.map(dm -> TO_ADDRESS_DTO.apply(dm).get()).collect(Collectors.toList());

}
