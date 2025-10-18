package com.hanuman.event.dtos.response;

import com.hanuman.event.entity.enums.TicketStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListTicketResponseDto {

    private Long id;
    private TicketStatus ticketStatus;
    private ListEventTicketTypeResponseDto ticketType;

}
