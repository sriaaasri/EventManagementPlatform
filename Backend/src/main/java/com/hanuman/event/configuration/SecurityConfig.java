package com.hanuman.event.configuration;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.hanuman.event.configuration.filters.UserProvisioningFilter;

@Configuration
public class SecurityConfig {

  
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http ,
                                                    UserProvisioningFilter userProvisioningFilter ,
                                                     JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception{

        return http.csrf(csrf -> csrf.disable())
                   .cors(source -> source.configurationSource(configurationSource()))
                   .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/published-events/**").permitAll()
                                                      .requestMatchers("/api/v1/events").hasRole("ORGANIZER")
                                                      .requestMatchers("/api/v1/ticket-validations/**").hasRole("STAFF")
                                                      .anyRequest().authenticated())  
                   .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)))
                   .addFilterAfter(userProvisioningFilter, BearerTokenAuthenticationFilter.class)
                   .build();

    }

     @Bean
     public CorsConfigurationSource configurationSource(){

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(List.of("**"));
        corsConfiguration.setAllowedMethods(List.of("POST","GET" , "DELETE" , "PUT"));
        corsConfiguration.setAllowedOriginPatterns(List.of("**"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization" ,"Content-type"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }

}
