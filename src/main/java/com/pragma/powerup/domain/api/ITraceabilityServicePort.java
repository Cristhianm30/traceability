package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Traceability;

import java.util.List;

public interface ITraceabilityServicePort {

    Traceability saveTraceability (Traceability traceability);

    List<Traceability> getTraceabilityByClient(Long clientId);

}
