package com.hanuman.event.dtos.response;

import java.util.UUID;

import com.hanuman.event.entity.enums.QRCodeStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class QRCodeResponseDto {

    private UUID id;

    @Enumerated(EnumType.STRING)
    private QRCodeStatus status;

    private String value;

}
