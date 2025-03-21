package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.TraceabilityRequestDto;
import com.pragma.powerup.application.dto.response.EmployeeRankingDto;
import com.pragma.powerup.application.dto.response.OrderEfficiencyDto;
import com.pragma.powerup.application.dto.response.TraceabilityResponseDto;
import com.pragma.powerup.application.handler.ITraceabilityHandler;
import com.pragma.powerup.application.mapper.IEfficiencyMapper;
import com.pragma.powerup.application.mapper.IRankingMapper;
import com.pragma.powerup.application.mapper.ITraceabilityMapper;
import com.pragma.powerup.domain.api.ITraceabilityServicePort;
import com.pragma.powerup.domain.model.EmployeeRanking;
import com.pragma.powerup.domain.model.OrderEfficiency;
import com.pragma.powerup.domain.model.Traceability;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TraceabilityHandlerImpl implements ITraceabilityHandler {

    private final ITraceabilityServicePort traceabilityServicePort;
    private final ITraceabilityMapper traceabilityMapper;
    private final IEfficiencyMapper efficiencyMapper;
    private final IRankingMapper rankingMapper;


    @Override
    public TraceabilityResponseDto saveTraceability(TraceabilityRequestDto traceabilityRequestDto) {

        Traceability model = traceabilityMapper.requestToModel(traceabilityRequestDto);
        Traceability savedModel = traceabilityServicePort.saveTraceability(model);
        return traceabilityMapper.modelToResponse(savedModel);
    }

    @Override
    public List<TraceabilityResponseDto> getTraceabilityByClient(Long clientId) {
        List<Traceability> traceabilityList = traceabilityServicePort.getTraceabilityByClient(clientId);
        return traceabilityMapper.toResponseDtoList(traceabilityList);
    }

    @Override
    public List<OrderEfficiencyDto> calculateOrdersEfficiency(List<Long> orderId) {

        List<OrderEfficiency> efficiencyList = traceabilityServicePort.calculateOrdersEfficiency(orderId);

        return efficiencyMapper.toDtoList(efficiencyList);
    }

    @Override
    public List<EmployeeRankingDto> calculateEmployeeRanking(List<Long> orderId) {

        List<EmployeeRanking> rankingList = traceabilityServicePort.calculateEmployeeRanking(orderId);

        return rankingMapper.toDtoList(rankingList);

    }
}