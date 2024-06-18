package com.janiks.forumHub.dtos;

import com.janiks.forumHub.domain.user.UserRole;

public record UserUpdate(
        String name,
        String email,
        String password,
        String role
) {
}
