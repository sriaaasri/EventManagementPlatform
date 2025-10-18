package com.hanuman.event.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hanuman.event.Exception.EventNotFoundException;
import com.hanuman.event.Exception.TicketTypeNotFoundException;
import com.hanuman.event.Exception.UserNotFoundException;
import com.hanuman.event.dtos.CreateEventRequest;
import com.hanuman.event.dtos.UpdateEventRequest;
import com.hanuman.event.dtos.UpdateTicketTypeRequest;
import com.hanuman.event.dtos.response.ListEventResponseDto;
import com.hanuman.event.entity.domain.Event;
import com.hanuman.event.entity.domain.TicketType;
import com.hanuman.event.entity.domain.User;
import com.hanuman.event.entity.enums.EventStatus;
import com.hanuman.event.mappers.EventMapper;
import com.hanuman.event.repository.EventRepo;
import com.hanuman.event.repository.UserRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventService {

    private UserRepo userRepo;
    private EventRepo eventRepo;
    private EventMapper eventMapper;
    
    private User validateUser(UUID organizerId){
        return userRepo.findByKeyCloakId(organizerId)
                        .orElseThrow(()-> new UserNotFoundException("User with KeyCloakId not found: "+organizerId));
    }

    public Event creatEvent(UUID organizerkeycloakId , CreateEventRequest eventRequest){

        //validate the organizer with id
        User organizer = validateUser(organizerkeycloakId);

        //create Event object to store its reference 
        Event event = new Event();

        //create TicketTypes using dto

        List<TicketType> ticketTypes = eventRequest.getTicketTypes()
                                                   .stream()
                                                   .map(typeDto -> TicketType.builder()
                                                                              .name(typeDto.getName())
                                                                              .description(typeDto.getDescription())
                                                                              .price(typeDto.getPrice())
                                                                              .totalAvailable(typeDto.getTotalAvailable())
                                                                              .event(event)
                                                                              .build())
                                                    .toList();
        
        event.setName(eventRequest.getName());
        event.setStart(eventRequest.getStart());
        event.setEnd(eventRequest.getEnd());
        event.setVenue(eventRequest.getVenue());
        event.setSalesEnd(eventRequest.getSalesEnd());
        event.setEventStatus(eventRequest.getStatus());
        event.setTicketTypes(ticketTypes);
        event.setOrganizer(organizer);

        return eventRepo.save(event);
        
    }

     public List<ListEventResponseDto> listEventsByAutherId(UUID organizerKeyCloakId , Pageable pageable){

        User organizer = validateUser(organizerKeyCloakId);
        List<ListEventResponseDto> result = eventRepo.findAllByOrganizer(organizer).stream()
                                                                                   .map(event -> eventMapper.toListEventResponseDto(event))
                                                                                   .toList();
        return result;
    }

    public List<ListEventResponseDto> listPublishedEventsByAutherId(UUID organizerKeyCloakId , Pageable pageable){

        User organizer = validateUser(organizerKeyCloakId);
        List<Event> organizerEvents = eventRepo.findAllByOrganizerAndEventStatus(organizer,EventStatus.PUBLISHED , pageable);
        List<ListEventResponseDto> result = organizerEvents.stream().map(event -> eventMapper.toListEventResponseDto(event)).toList();
        return result;
    }

    public List<ListEventResponseDto> listPublishedEventsByStatus(EventStatus eventStatus , Pageable pageable){

        // User organizer = validateUser(organizerKeyCloakId);
        List<Event> organizerEvents = eventRepo.findAllByEventStatus(eventStatus , pageable);
        List<ListEventResponseDto> result = organizerEvents.stream().map(event -> eventMapper.toListEventResponseDto(event)).toList();
        return result;
    }

    public Optional<Event> getEventById(Long eventId){
        return eventRepo.findById(eventId);
    }

    
    private void updateTickets(List<UpdateTicketTypeRequest> requestTypes , Event existingEvent){
        //collect new Ids
        Set<Long> requestIds = requestTypes.stream()
                                            .map(UpdateTicketTypeRequest::getId)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.toSet());
        //update matched Ids -> collect common Ids and check whether current id is present or not
        //creat new ticketypes -> if not present , current type is new one create new Entity with event maped

        existingEvent.getTicketTypes().removeIf( existingTicketType ->  !requestIds.contains(existingTicketType.getId()));

        Map<Long , TicketType> existingTypes = existingEvent.getTicketTypes()
                                                            .stream()
                                                            .collect(Collectors.toMap(TicketType::getId, Function.identity()));
        
        for(UpdateTicketTypeRequest request : requestTypes){

            //exisitng ticketType
            if(request.getId()!=null && requestIds.contains(request.getId())){
                TicketType targetType = existingTypes.get(request.getId());
                targetType.setName(request.getName());
                targetType.setPrice(request.getPrice());
                targetType.setDescription(request.getDescription());
                targetType.setTotalAvailable(request.getTotalAvailable());
            }

            //create new Tickettype
            else if(null == request.getId()){
                TicketType newTicketType = TicketType.builder()
                                                     .name(request.getName())
                                                     .price(request.getPrice())
                                                     .totalAvailable(request.getTotalAvailable())
                                                     .description(request.getDescription())
                                                     .event(existingEvent)
                                                     .build();
                existingEvent.getTicketTypes().add(newTicketType);
            }

            else{
                throw new TicketTypeNotFoundException(String.format("TicketType with ID :%s not found", request.getId()));
            }
        }
    }

    private void updateEventDetails(UpdateEventRequest event , Event existingEvent){

        existingEvent.setName(event.getName());
        existingEvent.setName(event.getName());
        existingEvent.setStart(event.getStart());
        existingEvent.setEnd(event.getEnd());
        existingEvent.setVenue(event.getVenue());
        existingEvent.setSalesStart(event.getSalesStart());
        existingEvent.setSalesEnd(event.getSalesEnd());
        existingEvent.setEventStatus(event.getStatus());

        updateTickets(event.getTicketTypes(), existingEvent);
    }
    
    public Event updateEventById(UUID organizerKeyCloakId , Long eventId , UpdateEventRequest event){

        User organizer = validateUser(organizerKeyCloakId);
        Event existingEvent = eventRepo.findByOrganizerAndId(organizer, eventId)
                                  .orElseThrow(()-> new EventNotFoundException("Event not found for organizer "+ organizer.getName() + " Event Id:- "+ eventId));
        
        updateEventDetails(event, existingEvent);
        return eventRepo.save(existingEvent);

    }

    public boolean deleteEventById(UUID organizerKeycloakId , Long eventId){

        User organizer = validateUser(organizerKeycloakId);
        //Functional interface style

        eventRepo.findByOrganizerAndId(organizer, eventId).ifPresent(eventRepo::delete);
        return true;

    }


    public List<Event> fetchEventsBySearchTerm(String searchTerm){
        return eventRepo.findPublishedEventsFromQuery(searchTerm);
    }


    

}

