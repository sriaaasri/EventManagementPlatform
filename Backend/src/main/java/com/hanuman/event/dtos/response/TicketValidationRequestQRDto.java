package com.hanuman.event.dtos.response;

import java.util.UUID;

import com.hanuman.event.entity.enums.TicketValidationMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TicketValidationRequestQRDto {

    private UUID id;
private TicketValidationMethod method;
}
