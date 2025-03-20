package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.TraceabilityRequestDto;
import com.pragma.powerup.application.dto.response.TraceabilityResponseDto;
import com.pragma.powerup.domain.model.Traceability;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITraceabilityMapper {

    Traceability requestToModel(TraceabilityRequestDto traceabilityRequestDto);
    Traceability responseToModel(TraceabilityResponseDto traceabilityResponseDto);
    TraceabilityRequestDto modelToRequest (Traceability traceability);
    TraceabilityResponseDto modelToResponse (Traceability traceability);
    List<TraceabilityResponseDto> toResponseDtoList(List<Traceability> traceabilityList);

}
