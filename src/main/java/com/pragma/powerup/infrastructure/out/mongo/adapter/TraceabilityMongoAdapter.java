package com.pragma.powerup.infrastructure.out.mongo.adapter;

import com.pragma.powerup.domain.model.Traceability;
import com.pragma.powerup.domain.spi.ITraceabilityPersistencePort;
import com.pragma.powerup.infrastructure.out.mongo.entity.TraceabilityEntity;
import com.pragma.powerup.infrastructure.out.mongo.mapper.ITraceabilityEntityMapper;
import com.pragma.powerup.infrastructure.out.mongo.repository.ITraceabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TraceabilityMongoAdapter implements ITraceabilityPersistencePort {

    private final ITraceabilityRepository traceabilityRepository;
    private final ITraceabilityEntityMapper traceabilityEntityMapper;


    @Override
    public Traceability saveTraceability(Traceability traceability) {

        TraceabilityEntity entity = traceabilityEntityMapper.toEntity(traceability);
        TraceabilityEntity savedEntity = traceabilityRepository.save(entity);

        return traceabilityEntityMapper.toModel(savedEntity);
    }

    @Override
    public List<Traceability> getTraceabilityByClient(Long clientId) {
        List<TraceabilityEntity> entityList = traceabilityRepository.findByClientId(clientId);
        return entityList.stream()
                .map(traceabilityEntityMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Traceability> getLogsByOrderId(Long orderId) {
        List<TraceabilityEntity> entityList = traceabilityRepository.findByOrderId(orderId);
        return entityList.stream()
                .map(traceabilityEntityMapper::toModel)
                .collect(Collectors.toList());
    }
}
