package com.janiks.forumHub.dtos;

import com.janiks.forumHub.domain.course.Category;
import com.janiks.forumHub.domain.course.Course;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseCreationData(
    @NotBlank
    String name,
    @NotNull
    Category category) {

    public CourseCreationData(Course course){
        this(course.getName(), course.getCategory());
    }

}
