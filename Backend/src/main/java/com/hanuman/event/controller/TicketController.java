package com.hanuman.event.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanuman.event.dtos.response.ListTicketResponseDto;
import com.hanuman.event.dtos.response.TicketResponseDto;
import com.hanuman.event.entity.domain.Ticket;
import com.hanuman.event.mappers.TicketMapper;
import com.hanuman.event.service.QRCodeService;
import com.hanuman.event.service.TicketService;
import com.hanuman.event.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/tickets")
@AllArgsConstructor
public class TicketController {

    private TicketMapper ticketMapper;
    private UserService userService;
    private TicketService ticketService;
    private QRCodeService qrCodeService;

    @GetMapping()
    public Page<ListTicketResponseDto> fetchTicketsByUser(@AuthenticationPrincipal Jwt jwt, Pageable pageable) {

        UUID userKeycloakId = userService.extractSubject(jwt);
        List<Ticket> tickets = ticketService.fetchAllTicketsByUser(userKeycloakId);

        List<ListTicketResponseDto> result = tickets.stream().map(ticketMapper::toListTicketResponseDto).toList();

        return new PageImpl<ListTicketResponseDto>(result, pageable, result.size());
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketResponseDto> fetchSingleEvent(@AuthenticationPrincipal Jwt jwt,
            @PathVariable Long ticketId) {

        UUID userKeycloakId = userService.extractSubject(jwt);

        return ResponseEntity.ok().body(ticketService.getTicketById(userKeycloakId, ticketId));
    }

    @GetMapping(path = "/{ticketId}/qr-codes")
    public ResponseEntity<byte[]> getTicketQrCode(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long ticketId) {

        byte[] qrCodeImage = qrCodeService.getQRCodeImageforTicket(ticketId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(qrCodeImage.length);
        return ResponseEntity.ok()
                .headers(headers)
                .body(qrCodeImage);
    }

}
