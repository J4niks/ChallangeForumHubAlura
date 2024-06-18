package com.janiks.forumHub.dtos;

import com.janiks.forumHub.domain.user.User;
import com.janiks.forumHub.domain.user.UserRole;

public record UserDetails(
        String nome,
        String email,
        UserRole role
) {
    public UserDetails(User user) {
        this(user.getName(), user.getEmail(), user.getRole());
    }
}
