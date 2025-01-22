package com.bill.tech.dto_mapper;

import static com.bill.tech.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.bill.tech.entity.Course;
import com.bill.tech.payload.request.CourseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
/**
 * @author Hrishikesh Mahadik
 * @since 11/01/2025
 * @version 1.0
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseMapper {

   

    /**
     * This function will convert CourseDto into optional Course Entity. 
     * <b>This function will return null if passed CourseDto is null</b>
     * 
     * @since 11-09-2023
     * @version 1.0
     * @param CourseDto
     * @return Optional<Course>
     */
    public static final Function<CourseDto, Optional<Course>> TO_COURSE = e -> evalMapper(e, Course.class);

    /**
     * This function will convert a collection of CourseDto into a list of Course entities. 
     * <b>This function will return null if passed collection of CourseDto is null</b>
     * 
     * @since 11-09-2023
     * @version 1.0
     * @param Collection<CourseDto>
     * @return List<Course>
     */
    public static final Function<Collection<CourseDto>, List<Course>> TO_COURSE_COLLECTION = e -> e.stream()
            .map(dm -> TO_COURSE.apply(dm).get())
            .collect(Collectors.toList());

    /**
     * This function will convert Course Entity into optional CourseDto. 
     * <b>This function will return null if passed Course entity is null</b>
     * 
     * @since 11-09-2023
     * @version 1.0
     * @param Course
     * @return Optional<CourseDto>
     */
    public static final Function<Course, Optional<CourseDto>> TO_COURSE_DTO = e -> evalMapper(e, CourseDto.class);

    /**
     * This function will convert a collection of Course entities into a list of CourseDto.
     * 
     * @since 11-09-2023
     * @version 1.0
     * @param Collection<Course>
     * @return List<CourseDto>
     */
    public static final Function<Collection<Course>, List<CourseDto>> TO_COURSE_DTOS = e -> e.stream()
            .map(dm -> TO_COURSE_DTO.apply(dm).get())
            .collect(Collectors.toList());
}
