package com.hanuman.event.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.hanuman.event.entity.enums.EventStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEventRequestDto {

    @NotNull(message = "Event ID must be provided")
    private Long id;
    @NotBlank(message = "Event name is required")
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    @NotBlank(message = "Venue information is required")
    private String venue;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;

    // @NotNull(message = "Event status must be provided")
    private EventStatus status;


    @NotEmpty(message = "At least one ticket type is required")
    @Valid
    private List<UpdateTicketTypeRequestDto> ticketTypes;
}
