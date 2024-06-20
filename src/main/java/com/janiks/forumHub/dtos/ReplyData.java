package com.janiks.forumHub.dtos;

import com.janiks.forumHub.domain.reply.Reply;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReplyData(
        Long id,
        String message,
        LocalDateTime data,
        Boolean soluction,
        Long topic_id

) {
    public ReplyData(Reply reply) {
        this(reply.getId(), reply.getMessage(), reply.getCreationDate(), reply.getSoluction(), reply.getTopic().getId());
    }
}
