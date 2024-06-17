package com.janiks.forumHub.dtos;

import com.janiks.forumHub.domain.topic.Status;

public record TopicUpdate(
        String title,
        String message,
        Status status,
        Long course_id
) {}