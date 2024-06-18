package com.janiks.forumHub.controllers;

import com.janiks.forumHub.domain.user.User;
import com.janiks.forumHub.dtos.UserCreationData;
import com.janiks.forumHub.dtos.UserDetails;
import com.janiks.forumHub.dtos.UserUpdate;
import com.janiks.forumHub.infra.security.SecurityValidation;
import com.janiks.forumHub.repositories.UserRepository;
import com.janiks.forumHub.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    @Autowired
    private UserRepository repository;
    @Autowired
    private SecurityValidation securityValidation;
    @Autowired
    private UserService userService;

    @PostMapping
    @Transactional
    public ResponseEntity registrarUsuario(@RequestBody @Valid UserCreationData data, HttpServletRequest request){
        String token =request.getHeader("Authorization").replace("Bearer ", "");
        if(this.repository.findByEmail(data.email()) != null|| data.role() == null || data.email() == null || data.password() ==null){
            return ResponseEntity.badRequest().build();
        }
        var isAdmin = securityValidation.isAdmin(token);
        var user = new User(data, isAdmin);
        this.repository.save(user);
        return ResponseEntity.ok(new UserDetails(user));
    }

    @PutMapping("/{email}")
    @Transactional
    public ResponseEntity updateUser(@RequestBody @Valid UserUpdate data, HttpServletRequest request, @PathVariable String email){
        String token =request.getHeader("Authorization").replace("Bearer ", "");
        var dto = this.userService.update(data, token, email);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{email}")
    @Transactional
    public ResponseEntity deleteUser(@PathVariable String email, HttpServletRequest request){
        String token =request.getHeader("Authorization").replace("Bearer ", "");
        if(userService.delete(email, token)){
            return ResponseEntity.noContent().build();
        };
        return ResponseEntity.badRequest().build();
    }
}
