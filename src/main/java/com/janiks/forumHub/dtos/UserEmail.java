package com.janiks.forumHub.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserEmail(
        @Email
        @NotBlank
        String email
) {
}
