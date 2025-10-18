package com.hanuman.event.service;

import java.util.UUID;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.hanuman.event.Exception.UserNotFoundException;
import com.hanuman.event.entity.domain.User;
import com.hanuman.event.repository.UserRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepo userRepo;

    public UUID extractSubject(Jwt jwt){
        UUID keycloakId = UUID.fromString(jwt.getSubject());
        return keycloakId;
     }

      public User validateUser(UUID organizerId){
        return userRepo.findByKeyCloakId(organizerId)
                        .orElseThrow(()-> new UserNotFoundException("User with KeyCloakId not found: "+organizerId));
    }


}
