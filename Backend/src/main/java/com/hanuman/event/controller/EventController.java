package com.hanuman.event.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanuman.event.dtos.CreateEventRequest;
import com.hanuman.event.dtos.CreateEventRequestDto;
import com.hanuman.event.dtos.UpdateEventRequest;
import com.hanuman.event.dtos.UpdateEventRequestDto;
import com.hanuman.event.dtos.response.CreateEventResponseDto;
import com.hanuman.event.dtos.response.EventDetailsResponseDto;
import com.hanuman.event.dtos.response.ListEventResponseDto;
import com.hanuman.event.entity.domain.Event;
import com.hanuman.event.mappers.EventMapper;
import com.hanuman.event.service.EventService;
import com.hanuman.event.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<CreateEventResponseDto> saveEvent(@AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDto createEventRequestDto) {

        UUID organizerKeyCloakId = userService.extractSubject(jwt);

        // convert to CreateEventRequest
        CreateEventRequest createEventRequest = eventMapper.toCreateEventRequest(createEventRequestDto);

        // send to eventService to event object
        Event savedEvent = eventService.creatEvent(organizerKeyCloakId, createEventRequest);
        CreateEventResponseDto eventResponseDto = eventMapper.toCreateEventResponseDto(savedEvent);
        return new ResponseEntity<CreateEventResponseDto>(eventResponseDto, HttpStatus.CREATED);
    }

    @GetMapping()
    public Page<ListEventResponseDto> fetchEvents(@AuthenticationPrincipal Jwt jwt,
            @PageableDefault(page = 0, size = 10, sort = "start", direction = Direction.ASC) Pageable pageable) {
        System.out.println("Logged user:- " + jwt.getClaimAsString("preferred_username"));

        UUID organizerKeycloakId = userService.extractSubject(jwt);

        List<ListEventResponseDto> data = eventService.listEventsByAutherId(organizerKeycloakId, pageable);

        return new PageImpl<>(data, pageable, data.size());
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(@AuthenticationPrincipal Jwt jwt, @PathVariable Long eventId,
            @Valid @RequestBody UpdateEventRequestDto updateEventRequestDto) {
        UUID organizerKeycloakId = userService.extractSubject(jwt);
        UpdateEventRequest updateEventRequest = eventMapper.toUpdateEventRequest(updateEventRequestDto);
        Event updatedEvent = eventService.updateEventById(organizerKeycloakId, eventId, updateEventRequest);

        return ResponseEntity.ok(updatedEvent);

    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDetailsResponseDto> getSingleEventDetails(@PathVariable Long eventId) {

        return eventService.getEventById(eventId)
                .map(eventMapper::toEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEventById(@AuthenticationPrincipal Jwt jwt, @PathVariable Long eventId) {
        UUID organizerKeycloakId = userService.extractSubject(jwt);
        boolean status = eventService.deleteEventById(organizerKeycloakId, eventId);

        return status ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
