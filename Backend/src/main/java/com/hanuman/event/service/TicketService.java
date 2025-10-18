package com.hanuman.event.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanuman.event.Exception.TicketNotFoundException;
import com.hanuman.event.Exception.TicketTypeNotFoundException;
import com.hanuman.event.Exception.TicketsSoldOutException;
import com.hanuman.event.Exception.UserNotFoundException;
import com.hanuman.event.dtos.response.TicketResponseDto;
import com.hanuman.event.entity.domain.QrCode;
import com.hanuman.event.entity.domain.Ticket;
import com.hanuman.event.entity.domain.TicketType;
import com.hanuman.event.entity.domain.User;
import com.hanuman.event.entity.enums.TicketStatus;
import com.hanuman.event.mappers.TicketMapper;
import com.hanuman.event.repository.TicketRepo;
import com.hanuman.event.repository.TicketTypeRepo;
import com.hanuman.event.repository.UserRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TicketService {

    private UserRepo userRepo;
    private TicketTypeRepo ticketTypeRepo;
    private TicketRepo ticketRepo;
    private QRCodeService qrCodeService;
    private UserService userService;
    private TicketMapper ticketMapper;

    @Transactional
    public Ticket purchaseTicket(UUID userId, Long ticketTypeId) {

        // validate user
        User user = userRepo.findByKeyCloakId(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User not found with id %d ", userId)));

        // fetch TicketType and lock the row
        TicketType ticketType = ticketTypeRepo.findByIdWithLock(ticketTypeId)
                .orElseThrow(() -> new TicketTypeNotFoundException("TicketType not found with id: " + ticketTypeId));

        // find if any available tickets
        Long purchasedTickets = ticketRepo.countByTicketTypeId(ticketTypeId);
        Integer totalAvailableTicket = ticketType.getTotalAvailable();

        if (purchasedTickets + 1 > totalAvailableTicket) {
            throw new TicketsSoldOutException("Sold off all the tickets");
        }

        Ticket ticket = Ticket.builder()
                .status(TicketStatus.PURCHASED)
                .purchaser(user)
                .ticketType(ticketType)
                .build();

        Ticket savedTicket = ticketRepo.save(ticket);
        QrCode qrCode = qrCodeService.generateQrCode(savedTicket);
        return savedTicket;
        // return ticketRepo.save(savedTicket);
        // savedTicket.setQrCodes(List.of(qrCode));

    }

    public List<Ticket> fetchAllTicketsByUser(UUID userKeycloakId) {

        User user = userService.validateUser(userKeycloakId);

        return ticketRepo.getTicketsBypurchaserId(user.getId());

    }

    public TicketResponseDto getTicketById(UUID userKeycloakId, Long ticketId) {
        User user = userService.validateUser(userKeycloakId);

        Ticket ticket = ticketRepo.findByPurchaserAndId(user, ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with Id " + ticketId));

        return ticketMapper.toTicketResponseDto(ticket);

    }

}
