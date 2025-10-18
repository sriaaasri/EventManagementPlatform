package com.hanuman.event.dtos.response;

import com.hanuman.event.entity.enums.TicketValidationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TicketValidationResponseDto {

    private Long ticketId;
    private TicketValidationStatus status;

}
