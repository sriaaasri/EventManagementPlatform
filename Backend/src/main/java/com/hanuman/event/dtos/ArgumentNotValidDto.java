package com.hanuman.event.dtos;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArgumentNotValidDto {

    private int errorCode;
    private String error;
    private List<String> errorMsg;
}
