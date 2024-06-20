package com.janiks.forumHub.infra.security;

import com.janiks.forumHub.domain.user.User;
import com.janiks.forumHub.infra.exception.ValidationException;
import com.janiks.forumHub.repositories.TopicRepository;
import com.janiks.forumHub.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SecurityValidation {

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;


    public Boolean haveAuthoritiesForTopic(Long id, String token) {
        var topicUser = topicRepository.getReferenceById(id).getUser();
        if(isTheSameUser(topicUser,getUser(token)) || isAdmin(token)){
            return true;
        }
        return false;
    }

    public Boolean haveAuthorities(User user, String token) {
        if(isTheSameUser(user,getUser(token)) || isAdmin(token)){
            return true;
        }
        return false;
    }

    public Boolean isTheSameUser(User topicUser, User receivedUser){
        if(topicUser == receivedUser) {return true;}
        return false;
    }

    public Boolean isAdmin(String token){
        var receivedUser = getUser(token);
        var test = receivedUser.getAuthorities().stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
        return test;
    }

    public User getUser(String token) {
        var email = tokenService.getSubject(token);
        var userDetails = this.userRepository.findByEmail(email);
        return (User) userDetails;
    }
}
