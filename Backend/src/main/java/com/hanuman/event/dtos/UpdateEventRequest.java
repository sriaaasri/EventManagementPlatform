package com.hanuman.event.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.hanuman.event.entity.enums.EventStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEventRequest {

private Long id;
private String name;
private LocalDateTime start;
private LocalDateTime end;
private String venue;
private LocalDateTime salesStart;
private LocalDateTime salesEnd;
private EventStatus status;
@Builder.Default
private List<UpdateTicketTypeRequest> ticketTypes = new ArrayList<>();
}
