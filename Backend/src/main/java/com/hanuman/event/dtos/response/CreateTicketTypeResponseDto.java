package com.hanuman.event.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTicketTypeResponseDto {
    
    private Long id;
    private String name;
    private Double price;
    private String description;
    private Integer totalAvailable;
}
