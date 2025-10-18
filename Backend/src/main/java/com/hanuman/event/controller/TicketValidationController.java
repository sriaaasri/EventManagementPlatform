package com.hanuman.event.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanuman.event.dtos.TicketValidationRequestIDDto;
import com.hanuman.event.dtos.response.TicketValidationRequestQRDto;
import com.hanuman.event.dtos.response.TicketValidationResponseDto;
import com.hanuman.event.entity.domain.TicketValidation;
import com.hanuman.event.mappers.TicketValidationMapper;
import com.hanuman.event.service.TicketValidationService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/ticket-validations")
@AllArgsConstructor
public class TicketValidationController {

    private TicketValidationService validationService;
    private TicketValidationMapper validationMapper;

    @PostMapping("/qr")
    public ResponseEntity<TicketValidationResponseDto> validateTicketByqr(@RequestBody TicketValidationRequestQRDto request){

        TicketValidation ticketValidation = validationService.validateByQRCode(request.getId());

        return ResponseEntity.ok(validationMapper.toTicketValidationResponseDto(ticketValidation));

    }

    @PostMapping("/id")
    public ResponseEntity<TicketValidationResponseDto> validateTicketById(@RequestBody TicketValidationRequestIDDto request){

        TicketValidation ticketValidation = validationService.validateTicketManually(request.getId());

        return ResponseEntity.ok(validationMapper.toTicketValidationResponseDto(ticketValidation));

    }

}
