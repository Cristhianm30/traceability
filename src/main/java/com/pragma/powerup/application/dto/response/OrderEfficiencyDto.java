package com.pragma.powerup.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderEfficiencyDto {

    private Long orderId;
    private long processingTimeInMinutes;
    private String finalStatus;

}
