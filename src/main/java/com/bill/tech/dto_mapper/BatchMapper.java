package com.bill.tech.dto_mapper;

import static com.bill.tech.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.bill.tech.entity.Batch;
import com.bill.tech.payload.request.BatchDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
/**
 * @author Hrishikesh Mahadik
 * @since 11/01/2025
 * @version 1.0
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BatchMapper {

   

    /**
     * This function will convert BatchDto into optional Batch Entity. 
     * <b>This function will return null if passed BatchDto is null</b>
     * 
     * @since 11-09-2023
     * @version 1.0
     * @param BatchDto
     * @return Optional<Batch>
     */
    public static final Function<BatchDto, Optional<Batch>> TO_BATCH = e -> evalMapper(e, Batch.class);

    /**
     * This function will convert a collection of BatchDto into a list of Batch entities. 
     * <b>This function will return null if passed collection of BatchDto is null</b>
     * 
     * @since 11-09-2023
     * @version 1.0
     * @param Collection<BatchDto>
     * @return List<Batch>
     */
    public static final Function<Collection<BatchDto>, List<Batch>> TO_BATCH_COLLECTION = e -> e.stream()
            .map(dm -> TO_BATCH.apply(dm).get())
            .collect(Collectors.toList());

    /**
     * This function will convert Batch Entity into optional BatchDto. 
     * <b>This function will return null if passed Batch entity is null</b>
     * 
     * @since 11-09-2023
     * @version 1.0
     * @param Batch
     * @return Optional<BatchDto>
     */
    public static final Function<Batch, Optional<BatchDto>> TO_BATCH_DTO = e -> evalMapper(e, BatchDto.class);

    /**
     * This function will convert a collection of Batch entities into a list of BatchDto.
     * 
     * @since 11-09-2023
     * @version 1.0
     * @param Collection<Batch>
     * @return List<BatchDto>
     */
    public static final Function<Collection<Batch>, List<BatchDto>> TO_BATCH_DTOS = e -> e.stream()
            .map(dm -> TO_BATCH_DTO.apply(dm).get())
            .collect(Collectors.toList());
}
