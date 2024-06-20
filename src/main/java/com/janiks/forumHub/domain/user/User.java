package com.janiks.forumHub.domain.user;

import com.janiks.forumHub.dtos.UserCreationData;
import com.janiks.forumHub.dtos.UserUpdate;
import com.janiks.forumHub.infra.exception.ValidationException;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity(name = "User")
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private UserRole role;

    public User(UserCreationData data, Boolean isAdmin) {
        this.name = data.name();
        this.email = data.email();
        this.password = new BCryptPasswordEncoder().encode(data.password());
        if(data.role() != null && !isAdmin && "ADMIN".equals(data.role().name())){
            throw new ValidationException("Você não tem permissões para cadastrar um usuário com a role: " + data.role());
        }
        if(isAdmin) {
            this.role = data.role();
        }
        if(!isAdmin || data.role() == null){
            this.role = UserRole.USER;
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));}
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
