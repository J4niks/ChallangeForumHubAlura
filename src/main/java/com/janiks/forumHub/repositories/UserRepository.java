package com.janiks.forumHub.repositories;

import com.janiks.forumHub.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    UserDetails findByEmail(String email);

    Boolean existsByName(String name);
}
