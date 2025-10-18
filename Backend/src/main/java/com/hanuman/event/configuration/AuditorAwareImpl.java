package com.hanuman.event.configuration;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication!=null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof Jwt jwt){

            String currentUser = jwt.getClaimAsString("preferred_username");
            return Optional.of(currentUser);
        }

        return Optional.of("UNKNOWN USER");
        
    }

}
