package com.hanuman.event.dtos.response;

import java.time.LocalDateTime;
import com.hanuman.event.entity.enums.TicketStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TicketResponseDto {

    
    private Long id;

    private TicketStatus status;
    private Double price;
    private String description;
    private String eventName;
    private String eventVenue;
    private LocalDateTime eventStart;
    private LocalDateTime eventEnd;

}
