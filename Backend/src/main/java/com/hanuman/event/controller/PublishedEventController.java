package com.hanuman.event.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hanuman.event.dtos.response.EventDetailsResponseDto;
import com.hanuman.event.dtos.response.ListEventResponseDto;
import com.hanuman.event.entity.enums.EventStatus;
import com.hanuman.event.mappers.EventMapper;
import com.hanuman.event.service.EventService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/published-events")
@AllArgsConstructor
public class PublishedEventController {

   private EventService eventService;
   private EventMapper eventMapper;

   @GetMapping()
   public Page<ListEventResponseDto> fetchEvents(@RequestParam(required = false, value = "q") String search,
         @PageableDefault(page = 0, size = 10, sort = "start", direction = Direction.ASC) Pageable pageable) {

      // System.out.println("Logged user:- "+
      // jwt.getClaimAsString("preferred_username"));

      List<ListEventResponseDto> data = new ArrayList<>();
      if (search != null && !search.trim().isEmpty()) {
         data = eventService.fetchEventsBySearchTerm(search).stream().map(eventMapper::toListEventResponseDto).toList();
      } else {
         data = eventService.listPublishedEventsByStatus(EventStatus.PUBLISHED, pageable);
      }

      return new PageImpl<>(data, pageable, data.size());
   }

   @GetMapping("/{eventId}")
   public ResponseEntity<EventDetailsResponseDto> getSingleEventDetails(@PathVariable Long eventId) {

      return eventService.getEventById(eventId)
            .map(eventMapper::toEventDetailsResponseDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
   }

}
