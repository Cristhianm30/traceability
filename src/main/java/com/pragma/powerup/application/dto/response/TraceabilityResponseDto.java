package com.pragma.powerup.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraceabilityResponseDto {

    private Long orderId;
    private Long clientId;
    private String clientEmail;
    private LocalDateTime date;
    private String lastStatus;
    private String newStatus;
    private Long employeeId;
    private String employeeEmail;

}
