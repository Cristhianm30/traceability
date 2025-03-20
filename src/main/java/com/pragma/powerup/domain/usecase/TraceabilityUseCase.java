package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ITraceabilityServicePort;
import com.pragma.powerup.domain.exception.LogsNotFoundException;
import com.pragma.powerup.domain.model.OrderEfficiency;
import com.pragma.powerup.domain.model.Traceability;
import com.pragma.powerup.domain.spi.ITraceabilityPersistencePort;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<OrderEfficiency> calculateOrdersEfficiency(List<Long> orderIds) {

        List<OrderEfficiency> result = new ArrayList<>();

        // Para cada orderId
        for (Long orderId : orderIds) {
            // Obtén todos los logs para este orderId
            List<Traceability> logs = traceabilityPersistencePort.getLogsByOrderId(orderId);

            if (logs == null || logs.isEmpty()) {
                continue; // Si no hay logs para ese orderId, se omite
            }

            // Buscar el log de inicio: newStatus "PENDIENTE"
            Optional<Traceability> pendingLogOpt = logs.stream()
                    .filter(log -> "PENDIENTE".equalsIgnoreCase(log.getNewStatus()))
                    .min(Comparator.comparing(Traceability::getDate));

            // Buscar el log final: newStatus "ENTREGADO" o "CANCELADO"
            Optional<Traceability> finalLogOpt = logs.stream()
                    .filter(log -> "ENTREGADO".equalsIgnoreCase(log.getNewStatus()) ||
                            "CANCELADO".equalsIgnoreCase(log.getNewStatus()))
                    .max(Comparator.comparing(Traceability::getDate));

            if (pendingLogOpt.isPresent() && finalLogOpt.isPresent()) {
                Traceability pendingLog = pendingLogOpt.get();
                Traceability finalLog = finalLogOpt.get();

                long minutes = Duration.between(pendingLog.getDate(), finalLog.getDate()).toMinutes();
                String finalStatus = finalLog.getNewStatus();

                // Se crea el objeto OrderEfficiency y se agrega a la lista
                result.add(new OrderEfficiency(orderId, minutes, finalStatus));
            }

        }
        if (result.isEmpty()){
            throw new LogsNotFoundException();
        }
        return result;
    }
}
