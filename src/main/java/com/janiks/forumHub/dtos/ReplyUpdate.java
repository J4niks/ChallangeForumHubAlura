package com.janiks.forumHub.dtos;

import jakarta.validation.constraints.NotNull;

public record ReplyUpdate(
        @NotNull
        Long reply_id,
        String message,
        Boolean soluction
) {
}
