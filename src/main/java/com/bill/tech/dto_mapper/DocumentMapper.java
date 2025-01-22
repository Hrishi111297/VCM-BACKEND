package com.bill.tech.dto_mapper;




import static com.bill.tech.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.bill.tech.entity.Document;
import com.bill.tech.payload.request.DocumentDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
/**
 * @author Hrishikesh Mahadik
 * @since 11/01/2025
 * @version 1.0
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)	
public class DocumentMapper {


	/**
	 * This function will convert AddCallDto into optional AddCall Entity. <b>This
	 * function will return null if passed AddCallDto is null</b> <br>
	 * <b>Param</b> AddCallDto <br>
	 * <b>Return</b> AddCall
	 * 
	 * @since 11-09-2023
	 * @version 1.0
	 */
	public static final Function<DocumentDto, Optional<Document>> TO_DOCUMENT = e -> evalMapper(e, Document.class);
	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<DocumentDto>, List<Document>>TO_DOCUMENT_COLLECTION = e -> e.stream()
			.map(dm -> TO_DOCUMENT.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert AddCall Entity into optional AddCallDto . <b>This
	 * function will return null if passed AddCall is null</b> <br>
	 * <b>Param</b> AddCall <br>
	 * <b>Return</b> AddCallDto
	 * 
	 * @since 11-09-2023
	 * @Version 1.0
	 */
	public static final Function<Document, Optional<DocumentDto>>TO_DOCUMENT_DTO = e -> evalMapper(e, DocumentDto.class);

	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<Document>, List<DocumentDto>> TO_DOCUMENT_DTO_COLLECTION = e -> e.stream()
			.map(dm -> TO_DOCUMENT_DTO.apply(dm).get()).collect(Collectors.toList());

	

}
