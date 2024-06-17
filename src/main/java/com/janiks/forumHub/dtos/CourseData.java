package com.janiks.forumHub.dtos;

import com.janiks.forumHub.domain.course.Category;
import com.janiks.forumHub.domain.course.Course;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseData(
        Long id,
        @NotBlank
        String name,
        @NotNull
        Category category) {

    public CourseData(Course course){
        this(course.getId(), course.getName(), course.getCategory());
    }
}
