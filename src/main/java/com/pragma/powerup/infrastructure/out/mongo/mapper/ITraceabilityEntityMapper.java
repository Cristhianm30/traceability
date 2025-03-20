package com.pragma.powerup.infrastructure.out.mongo.mapper;

import com.pragma.powerup.domain.model.Traceability;
import com.pragma.powerup.infrastructure.out.mongo.entity.TraceabilityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ITraceabilityEntityMapper {

    Traceability toModel(TraceabilityEntity traceabilityEntity);

    TraceabilityEntity toEntity(Traceability traceability);

}
