package com.bill.tech.dto_mapper;




import java.util.Collection;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.bill.tech.entity.UserMaster;
import com.bill.tech.payload.request.UserMasterDataRequestDto;
import static com.bill.tech.util.FunctionUtil.eval;
import static com.bill.tech.util.FunctionUtil.evalMapper;
import static com.bill.tech.util.FunctionUtil.evalMapperCollection;
import static com.bill.tech.util.FunctionUtil.evalMapperProjection;
public class UserMasterMapper {

	UserMasterMapper() {

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
	public static final Function<UserMasterDataRequestDto, Optional<UserMaster>> TO_USERMASTER = e -> evalMapper(e, UserMaster.class);
	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<UserMasterDataRequestDto>, List<UserMaster>> TO_USERMASTER_COLLECTION = e -> e.stream()
			.map(dm -> TO_USERMASTER.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert AddCall Entity into optional AddCallDto . <b>This
	 * function will return null if passed AddCall is null</b> <br>
	 * <b>Param</b> AddCall <br>
	 * <b>Return</b> AddCallDto
	 * 
	 * @since 11-09-2023
	 * @Version 1.0
	 */
	public static final Function<UserMaster, Optional<UserMasterDataRequestDto>> TO_USERMASTER_DTO = e -> evalMapper(e, UserMasterDataRequestDto.class);

	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<UserMaster>, List<UserMasterDataRequestDto>> TO_CALL_DTOS = e -> e.stream()
			.map(dm -> TO_USERMASTER_DTO.apply(dm).get()).collect(Collectors.toList());

	

}
