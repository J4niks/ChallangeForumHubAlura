package com.janiks.forumHub.controllers;

import com.janiks.forumHub.domain.user.User;
import com.janiks.forumHub.dtos.UserCreationData;
import com.janiks.forumHub.dtos.UserDetails;
import com.janiks.forumHub.dtos.UserUpdate;
import com.janiks.forumHub.infra.exception.ValidationException;
import com.janiks.forumHub.infra.security.SecurityValidation;
import com.janiks.forumHub.repositories.UserRepository;
import com.janiks.forumHub.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "Usuários")
public class UserController {

    @Autowired
    private UserRepository repository;
    @Autowired
    private SecurityValidation securityValidation;
    @Autowired
    private UserService userService;

    @PostMapping
    @Transactional
    @Operation(summary = "Registrar", description = "Registro de usuários (Parâmetro 'role' necessário apenas para ADMIN)")
    public ResponseEntity registrarUsuario(@RequestBody @Valid UserCreationData data, HttpServletRequest request){
        if(this.repository.findByEmail(data.email()) != null || this.repository.existsByName(data.name())){
            throw new ValidationException("Usuário com esse email e/ou nome já está cadastrado");
        }
        var user = new User(data);
        this.repository.save(user);
        return ResponseEntity.ok(new UserDetails(user));
    }

    @PutMapping("/{email}")
    @Transactional
    @Operation(summary = "Editar", description = "Edição de usuários por email")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity updateUser(@RequestBody @Valid UserUpdate data, HttpServletRequest request, @PathVariable String email){
        String token =request.getHeader("Authorization").replace("Bearer ", "");
        var dto = this.userService.update(data, token, email);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{email}")
    @Transactional
    @Operation(summary = "Apagar", description = "Apagar usuários por email")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity deleteUser(@PathVariable String email, HttpServletRequest request){
        String token =request.getHeader("Authorization").replace("Bearer ", "");
        if(userService.delete(email, token)){
            return ResponseEntity.noContent().build();
        };
        return ResponseEntity.badRequest().build();
    }
}
