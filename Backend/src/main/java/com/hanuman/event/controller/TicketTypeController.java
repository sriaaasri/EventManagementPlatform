package com.hanuman.event.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanuman.event.entity.domain.Ticket;
import com.hanuman.event.service.TicketService;
import com.hanuman.event.service.UserService;

import lombok.AllArgsConstructor;

//http://localhost:5173/api/v1/events/6/ticket-types/7/tickets
@RestController
@RequestMapping("/api/v1/events/{eventId}/ticket-types")
@AllArgsConstructor
public class TicketTypeController {

    private TicketService ticketService;
    private UserService userService;

    @PostMapping("/{ticketTypeId}/tickets")
    public ResponseEntity<Ticket> purchaseTicketEndPoint(@AuthenticationPrincipal Jwt jwt 
                                                    ,  @PathVariable(name = "ticketTypeId") Long ticketTypeId){
            UUID attendeeKeycloakId = userService.extractSubject(jwt);

        Ticket purchasedTicket = ticketService.purchaseTicket(attendeeKeycloakId, ticketTypeId);

            return ResponseEntity.ok().body(purchasedTicket);

    }

}
