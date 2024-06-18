package com.janiks.forumHub.dtos;

import com.janiks.forumHub.domain.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserCreationData(
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[0-9a-zA-Z$*&@#]{8,}$",message = "A senha deve começas com 8 caracteres contendo Letras maiúsculas, minusculas, números e caracteres especiais")
        String password,
        @NotNull
        UserRole role
) {
}
