package com.janiks.forumHub.dtos;

import jakarta.validation.constraints.NotBlank;

public record ReplyCreationData(
        @NotBlank
        String message
) {
}
