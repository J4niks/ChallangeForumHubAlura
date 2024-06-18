package com.janiks.forumHub.controllers;

import com.janiks.forumHub.domain.user.User;
import com.janiks.forumHub.dtos.AuthData;
import com.janiks.forumHub.dtos.TokenDataJWT;
import com.janiks.forumHub.infra.security.TokenService;
import com.janiks.forumHub.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid AuthData dados){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(),dados.password());
        var authentication = manager.authenticate(authenticationToken);


        var tokenJWT = tokenService.gerarToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenDataJWT(tokenJWT));
    }


}
