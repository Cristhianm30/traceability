package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.OrderEfficiencyDto;
import com.pragma.powerup.domain.model.OrderEfficiency;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IEfficiencyMapper {

    OrderEfficiencyDto toDto(OrderEfficiency orderEfficiency);
    List<OrderEfficiencyDto> toDtoList(List<OrderEfficiency> orderEfficiencyList);
}
