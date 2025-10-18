package com.hanuman.event.configuration.filters;

import java.io.IOException;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hanuman.event.entity.domain.User;
import com.hanuman.event.repository.UserRepo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserProvisioningFilter extends OncePerRequestFilter {

      private final UserRepo userRepo;
  
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                //to extract JWT from request
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(authentication != null && authentication.isAuthenticated() 
                                      && authentication.getPrincipal() instanceof Jwt jwt){
                        
                    UUID keycloakId = UUID.fromString(jwt.getSubject());

                    if(!userRepo.existsByKeyCloakId(keycloakId)){
                        User user = User.builder().name(jwt.getClaimAsString("preferred_username"))
                                                  .email(jwt.getClaimAsString("email"))
                                                  .keyCloakId(keycloakId)
                                                  .build();
                        userRepo.save(user);
                    }
                    else{

                    }
                }
             filterChain.doFilter(request, response);
    }

}
