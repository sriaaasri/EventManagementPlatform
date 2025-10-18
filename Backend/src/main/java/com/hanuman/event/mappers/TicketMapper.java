package com.hanuman.event.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.hanuman.event.dtos.response.ListEventTicketTypeResponseDto;
import com.hanuman.event.dtos.response.ListTicketResponseDto;
import com.hanuman.event.dtos.response.TicketResponseDto;
import com.hanuman.event.entity.domain.Ticket;
import com.hanuman.event.entity.domain.TicketType;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {


    ListTicketResponseDto toListTicketResponseDto(Ticket ticket);
    ListEventTicketTypeResponseDto toListEventTicketTypeResponseDto(TicketType ticketType);


    @Mapping(target = "description" , source = "ticket.ticketType.description")
    @Mapping(target = "eventName" , source = "ticket.ticketType.event.name")
    @Mapping(target = "eventStart", source = "ticket.ticketType.event.start")
    @Mapping(target = "eventEnd", source = "ticket.ticketType.event.end")
    @Mapping(target = "eventVenue" , source = "ticket.ticketType.event.venue")
    @Mapping(target = "price" , source = "ticket.ticketType.price")
    TicketResponseDto toTicketResponseDto(Ticket ticket);


}
