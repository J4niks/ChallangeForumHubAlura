package com.janiks.forumHub.dtos;

import com.janiks.forumHub.domain.topic.Topic;

public record TopicData(
        Long id,
        String title,
        String message,
        String course
) {
    public TopicData(Topic topic) {
        this(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getCourse().getName());
    }
}
