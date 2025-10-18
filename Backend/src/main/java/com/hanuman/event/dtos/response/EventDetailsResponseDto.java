package com.hanuman.event.dtos.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.hanuman.event.entity.enums.EventStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class EventDetailsResponseDto {

    private Long id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private EventStatus eventStatus;
    @Builder.Default
    private List<TicketDetailsResponseDto> ticketTypes = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
