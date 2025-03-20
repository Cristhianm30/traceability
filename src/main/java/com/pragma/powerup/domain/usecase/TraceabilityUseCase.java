package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ITraceabilityServicePort;
import com.pragma.powerup.domain.model.Traceability;
import com.pragma.powerup.domain.spi.ITraceabilityPersistencePort;

import java.util.List;

public class TraceabilityUseCase  implements ITraceabilityServicePort {

    private final ITraceabilityPersistencePort traceabilityPersistencePort;

    public TraceabilityUseCase(ITraceabilityPersistencePort traceabilityPersistencePort) {
        this.traceabilityPersistencePort = traceabilityPersistencePort;
    }


    @Override
    public Traceability saveTraceability(Traceability traceability) {

        return traceabilityPersistencePort.saveTraceability(traceability);

    }

    @Override
    public List<Traceability> getTraceabilityByClient(Long clientId) {
        return traceabilityPersistencePort.getTraceabilityByClient(clientId);
    }
}
