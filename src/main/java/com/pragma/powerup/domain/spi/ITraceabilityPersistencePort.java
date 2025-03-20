package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.Traceability;

import java.util.List;

public interface ITraceabilityPersistencePort {

    Traceability saveTraceability(Traceability traceability);

    List<Traceability> getTraceabilityByClient(Long clientId);

}
