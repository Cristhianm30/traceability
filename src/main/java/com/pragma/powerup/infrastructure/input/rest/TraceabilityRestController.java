package com.pragma.powerup.infrastructure.input.rest;


import com.pragma.powerup.application.dto.request.TraceabilityRequestDto;
import com.pragma.powerup.application.dto.response.OrderEfficiencyDto;
import com.pragma.powerup.application.dto.response.TraceabilityResponseDto;
import com.pragma.powerup.application.dto.response.EmployeeRankingDto;
import com.pragma.powerup.application.handler.ITraceabilityHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/traceability")
@RequiredArgsConstructor
public class TraceabilityRestController {

    private final ITraceabilityHandler traceabilityHandler;


    @PostMapping
    public ResponseEntity<TraceabilityResponseDto> saveTraceability(
            @RequestBody TraceabilityRequestDto traceabilityRequestDto
    ){
        TraceabilityResponseDto save = traceabilityHandler.saveTraceability(traceabilityRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<List<TraceabilityResponseDto>> getTraceabilityByClient (
            @PathVariable Long clientId
    ){
        List<TraceabilityResponseDto> traceList = traceabilityHandler.getTraceabilityByClient(clientId);
        return ResponseEntity.status(HttpStatus.OK).body(traceList);
    }

    @PostMapping("/efficiency/orders")
    public ResponseEntity<List<OrderEfficiencyDto>> getOrderEfficiency (
            @RequestBody List<Long> orderId
    ){
        List<OrderEfficiencyDto> efficiencyList = traceabilityHandler.calculateOrdersEfficiency(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(efficiencyList);
    }

    @PostMapping("/ranking/employees")
    public ResponseEntity<List<EmployeeRankingDto>> getEmployeeRanking (
            @RequestBody List<Long> orderId
    ){
        List<EmployeeRankingDto> rankingList = traceabilityHandler.calculateEmployeeRanking(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(rankingList);
    }

}
