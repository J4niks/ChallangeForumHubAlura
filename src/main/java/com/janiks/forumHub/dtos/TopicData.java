package com.janiks.forumHub.dtos;

import com.janiks.forumHub.domain.topic.Topic;

import java.time.LocalDateTime;
import java.util.UUID;

public record TopicData(
        Long id,
        String title,
        LocalDateTime creation_date,
        String message,
        String course,
        UUID user_id
) {
    public TopicData(Topic topic) {
        this(topic.getId(), topic.getTitle(),topic.getCreationDate() ,topic.getMessage(), topic.getCourse().getName(), topic.getUser().getId());
    }
}
