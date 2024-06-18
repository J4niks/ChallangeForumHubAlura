package com.janiks.forumHub.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReplyUpdate(
        @NotNull
        Long reply_id,
        @NotBlank
        String message,
        Boolean soluction
) {
}
