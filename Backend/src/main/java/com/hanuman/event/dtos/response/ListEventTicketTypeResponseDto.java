package com.hanuman.event.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListEventTicketTypeResponseDto {   
private Long id;
private String name;
private Double price;
private String description;
private Integer totalAvailable;
}