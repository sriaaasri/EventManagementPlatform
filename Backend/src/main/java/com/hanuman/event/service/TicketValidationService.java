package com.hanuman.event.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hanuman.event.Exception.TicketNotFoundException;
import com.hanuman.event.entity.domain.QrCode;
import com.hanuman.event.entity.domain.Ticket;
import com.hanuman.event.entity.domain.TicketValidation;
import com.hanuman.event.entity.enums.TicketValidationMethod;
import com.hanuman.event.entity.enums.TicketValidationStatus;
import com.hanuman.event.repository.TicketRepo;
import com.hanuman.event.repository.TicketValidationRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TicketValidationService {

    private TicketValidationRepo validationRepo;
    private TicketRepo ticketRepo;
    private QRCodeService qrCodeService;

    public TicketValidation validateByQRCode(UUID qrCodeId){

        QrCode qrcode = qrCodeService.validateQrCodeEntity(qrCodeId,"QRCode not found to validate ticket");
        Ticket ticket = qrcode.getTicket();

        return validateTicket(ticket , TicketValidationMethod.QR_SCAN);
    }

     public TicketValidation validateTicketManually(Long ticketId){
            Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(()-> new TicketNotFoundException("Ticket not found to validate ticket"));

            return validateTicket(ticket, TicketValidationMethod.MANUAL);
    }

    public TicketValidation validateTicket(Ticket ticket , TicketValidationMethod method){
            
        TicketValidationStatus ticketValidationStatus;
        
        if(LocalDateTime.now().isAfter(ticket.getTicketType().getEvent().getEnd())){
            ticketValidationStatus = TicketValidationStatus.EXPIRED;
        }
        else{
        ticketValidationStatus = ticket.getValidations()
                                        .stream()
                                        .filter( validation -> TicketValidationStatus.VALID.equals(validation.getStatus()))
                                        .findFirst()
                                        .map(v -> TicketValidationStatus.INVALID)
                                        .orElse(TicketValidationStatus.VALID);
        }
        TicketValidation ticketValidation = TicketValidation.builder()
                                                            .status(ticketValidationStatus)
                                                            .ticket(ticket)
                                                            .ticketValidationMethod(method)
                                                            .build();
        return validationRepo.save(ticketValidation);

    }




}
