package com.pragma.powerup.application.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRankingDto {

    private Long employeeId;
    private double averageProcessingTimeInMinutes;

}
