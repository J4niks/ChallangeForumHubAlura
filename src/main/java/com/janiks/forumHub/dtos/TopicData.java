package com.janiks.forumHub.dtos;

import com.janiks.forumHub.domain.topic.Topic;

import java.time.LocalDateTime;

public record TopicData(
        Long id,
        String title,
        LocalDateTime creation_date,
        String message,
        String course
) {
    public TopicData(Topic topic) {
        this(topic.getId(), topic.getTitle(),topic.getCreationDate() ,topic.getMessage(), topic.getCourse().getName());
    }
}
