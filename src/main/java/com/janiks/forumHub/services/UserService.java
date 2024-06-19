package com.janiks.forumHub.services;

import com.janiks.forumHub.domain.user.User;
import com.janiks.forumHub.domain.user.UserRole;
import com.janiks.forumHub.dtos.UserDetails;
import com.janiks.forumHub.dtos.UserUpdate;
import com.janiks.forumHub.infra.exception.ValidationException;
import com.janiks.forumHub.infra.security.SecurityValidation;
import com.janiks.forumHub.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private SecurityValidation securityValidation;
    @Autowired
    private UserRepository userRepository;

    public UserDetails update(UserUpdate data, String token, String email) {
        var receivedUser = (User) this.userRepository.findByEmail(email);
        if(securityValidation.haveAuthorities(receivedUser, token)) {
            if(isItBlank(data.name())){
                receivedUser.setName(data.name());
            }
            if(isItBlank(data.email()) && userRepository.findByEmail(data.email()) == null){
                receivedUser.setEmail(data.email());
            }
            if(isItBlank(data.password())){
                validatePassword(data.password());
                receivedUser.setPassword(data.password());
            }
        }
        if(securityValidation.isAdmin(token)){
            if(isItBlank(data.role())){
                try{
                    receivedUser.setRole(UserRole.valueOf(data.role()));
                }catch (Exception ex){
                    throw new ValidationException(ex.getMessage());
                }
            }
        }
        return new UserDetails(receivedUser);
    }

    private boolean isItBlank(String str) {
        return str != null && !str.trim().isEmpty();
    }

    private void validatePassword(String password) {
        var passwordPattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[0-9a-zA-Z$*&@#]{8,}$");
        if (!passwordPattern.matcher(password).matches()) {
            throw new ValidationException("A senha deve começas com 8 caracteres contendo Letras maiúsculas, minusculas, números e caracteres especiais");
        }
    }


    public Boolean delete(String email,String token) {
        var user = (User)userRepository.findByEmail(email);
        if(securityValidation.haveAuthorities(user,token)){
            userRepository.deleteById(user.getId());
            return true;
        }
        return false;
    }
}
