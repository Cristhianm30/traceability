package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.TraceabilityRequestDto;
import com.pragma.powerup.application.dto.response.OrderEfficiencyDto;
import com.pragma.powerup.application.dto.response.TraceabilityResponseDto;

import java.util.List;

public interface ITraceabilityHandler {

    TraceabilityResponseDto saveTraceability (TraceabilityRequestDto traceabilityRequestDto);
    List<TraceabilityResponseDto> getTraceabilityByClient(Long clientId);
    List<OrderEfficiencyDto> calculateOrdersEfficiency (List<Long> orderId);
}
