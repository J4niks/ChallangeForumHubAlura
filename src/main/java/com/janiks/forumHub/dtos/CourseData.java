package com.janiks.forumHub.dtos;

import com.janiks.forumHub.domain.course.Category;
import com.janiks.forumHub.domain.course.Course;

public record CourseData(
        Long id,
        String name,
        Category category) {

    public CourseData(Course course){
        this(course.getId(), course.getName(), course.getCategory());
    }
}
