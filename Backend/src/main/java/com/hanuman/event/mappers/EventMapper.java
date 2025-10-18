package com.hanuman.event.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.hanuman.event.dtos.CreateEventRequest;
import com.hanuman.event.dtos.CreateEventRequestDto;
import com.hanuman.event.dtos.CreateTicketTypeRequest;
import com.hanuman.event.dtos.CreateTicketTypeRequestDto;
import com.hanuman.event.dtos.UpdateEventRequest;
import com.hanuman.event.dtos.UpdateEventRequestDto;
import com.hanuman.event.dtos.UpdateTicketTypeRequest;
import com.hanuman.event.dtos.UpdateTicketTypeRequestDto;
import com.hanuman.event.dtos.response.CreateEventResponseDto;
import com.hanuman.event.dtos.response.EventDetailsResponseDto;
import com.hanuman.event.dtos.response.ListEventResponseDto;
import com.hanuman.event.dtos.response.ListEventTicketTypeResponseDto;
import com.hanuman.event.dtos.response.TicketDetailsResponseDto;
import com.hanuman.event.entity.domain.Event;
import com.hanuman.event.entity.domain.TicketType;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest toCreateTicketTypeRequest(CreateTicketTypeRequestDto createTicketTypeRequestDto);
    CreateEventRequest toCreateEventRequest(CreateEventRequestDto createEventRequestDto);
    CreateEventResponseDto toCreateEventResponseDto(Event event);
    ListEventResponseDto toListEventResponseDto(Event event);
    ListEventTicketTypeResponseDto toListEventTicketTypeResponseDto(TicketType ticketType);

    EventDetailsResponseDto toEventDetailsResponseDto(Event event);
    TicketDetailsResponseDto toTicketDetailsResponseDto(TicketType type);

    UpdateEventRequest toUpdateEventRequest(UpdateEventRequestDto updateEventRequestDto);
    UpdateTicketTypeRequest toUpdateTicketTypeRequest(UpdateTicketTypeRequestDto updateTicketTypeRequestDto);

    UpdateEventRequestDto toUpdateEventRequestDto(UpdateEventRequest updateEventRequest);
    UpdateTicketTypeRequestDto toUpdateTicketTypeRequestDto(UpdateTicketTypeRequest updateTicketTypeRequest);


}
