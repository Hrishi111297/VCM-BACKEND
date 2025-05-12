package com.bill.tech.dto_mapper;




import static com.bill.tech.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.bill.tech.entity.UserMaster;
import com.bill.tech.payload.request.UserMasterDataRequestDto;
import com.bill.tech.payload.response.UserProfileDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMasterMapper {


	
	public static final Function<UserMasterDataRequestDto, Optional<UserMaster>> TO_USERMASTER = e -> evalMapper(e, UserMaster.class);
	
	public static final Function<Collection<UserMasterDataRequestDto>, List<UserMaster>> TO_USERMASTER_COLLECTION = e -> e.stream()
			.map(dm -> TO_USERMASTER.apply(dm).get()).collect(Collectors.toList());

	
	public static final Function<UserMaster, Optional<UserMasterDataRequestDto>> TO_USERMASTER_DTO = e -> evalMapper(e, UserMasterDataRequestDto.class);
	public static final Function<UserMaster, Optional<UserProfileDto>> TO_USERMASTER_DTO_RESPONSE = e -> evalMapper(e, UserProfileDto.class);

	public static final Function<Collection<UserMaster>, List<UserMasterDataRequestDto>> TO_USER_MASTER_DTOS = e -> e.stream()
			.map(dm -> TO_USERMASTER_DTO.apply(dm).get()).collect(Collectors.toList());

	

}
