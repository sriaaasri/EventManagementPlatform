package com.hanuman.event.dtos;


import com.hanuman.event.entity.enums.TicketValidationMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TicketValidationRequestIDDto {

    
    private Long id;
    private TicketValidationMethod method;
}
