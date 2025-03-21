package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.EmployeeRankingDto;
import com.pragma.powerup.domain.model.EmployeeRanking;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRankingMapper {

    EmployeeRanking toModel(EmployeeRankingDto employeeRankingDto);
    EmployeeRankingDto toDto (EmployeeRanking employeeRanking);
    List<EmployeeRankingDto> toDtoList (List<EmployeeRanking> employeeRankings);

}

