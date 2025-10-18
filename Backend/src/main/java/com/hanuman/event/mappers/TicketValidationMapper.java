package com.hanuman.event.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.hanuman.event.dtos.response.TicketValidationResponseDto;
import com.hanuman.event.entity.domain.TicketValidation;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketValidationMapper {

        @Mapping(target = "ticketId" , source = "validation.ticket.id")
        TicketValidationResponseDto toTicketValidationResponseDto(TicketValidation validation);
    
}