package com.hanuman.event.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTicketTypeRequest {
private Long id;
private String name;
private Double price;
private String description;
private Integer totalAvailable;
}
