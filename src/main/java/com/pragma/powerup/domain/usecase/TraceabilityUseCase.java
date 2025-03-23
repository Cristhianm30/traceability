package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ITraceabilityServicePort;
import com.pragma.powerup.domain.exception.LogsNotFoundException;
import com.pragma.powerup.domain.model.EmployeeRanking;
import com.pragma.powerup.domain.model.OrderEfficiency;
import com.pragma.powerup.domain.model.Traceability;
import com.pragma.powerup.domain.spi.ITraceabilityPersistencePort;

import java.time.Duration;
import java.util.*;

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


        for (Long orderId : orderIds) {

            List<Traceability> logs = traceabilityPersistencePort.getLogsByOrderId(orderId);

            if (logs == null || logs.isEmpty()) {
                continue;
            }


            Optional<Traceability> pendingLogOpt = logs.stream()
                    .filter(log -> "PENDIENTE".equalsIgnoreCase(log.getNewStatus()))
                    .min(Comparator.comparing(Traceability::getDate));


            Optional<Traceability> finalLogOpt = logs.stream()
                    .filter(log -> "ENTREGADO".equalsIgnoreCase(log.getNewStatus()) ||
                            "CANCELADO".equalsIgnoreCase(log.getNewStatus()))
                    .max(Comparator.comparing(Traceability::getDate));

            if (pendingLogOpt.isPresent() && finalLogOpt.isPresent()) {
                Traceability pendingLog = pendingLogOpt.get();
                Traceability finalLog = finalLogOpt.get();

                long minutes = Duration.between(pendingLog.getDate(), finalLog.getDate()).toMinutes();
                String finalStatus = finalLog.getNewStatus();


                result.add(new OrderEfficiency(orderId, minutes, finalStatus));
            }

        }
        if (result.isEmpty()){
            throw new LogsNotFoundException();
        }
        return result;
    }

    @Override
    public List<EmployeeRanking> calculateEmployeeRanking(List<Long> orderIds) {

        Map<Long, List<Long>> timesByEmployee = new HashMap<>();

        for (Long orderId : orderIds) {

            List<Traceability> logs = traceabilityPersistencePort.getLogsByOrderId(orderId);
            if (logs == null || logs.isEmpty()) {
                continue;
            }


            Optional<Traceability> pendingLogOpt = logs.stream()
                    .filter(log -> "PENDIENTE".equalsIgnoreCase(log.getNewStatus()))
                    .min(Comparator.comparing(Traceability::getDate));


            Optional<Traceability> finalLogOpt = logs.stream()
                    .filter(log -> "ENTREGADO".equalsIgnoreCase(log.getNewStatus())
                            || "CANCELADO".equalsIgnoreCase(log.getNewStatus()))
                    .max(Comparator.comparing(Traceability::getDate));

            if (pendingLogOpt.isPresent() && finalLogOpt.isPresent()) {
                Traceability pendingLog = pendingLogOpt.get();
                Traceability finalLog = finalLogOpt.get();


                long processingTime = Duration.between(pendingLog.getDate(), finalLog.getDate()).toMinutes();
                Long employeeId = finalLog.getEmployeeId();


                if (employeeId != null) {
                    timesByEmployee.computeIfAbsent(employeeId, k -> new ArrayList<>()).add(processingTime);

                }
            }
        }


        List<EmployeeRanking> rankingList = new ArrayList<>();
        for (Map.Entry<Long, List<Long>> entry : timesByEmployee.entrySet()) {
            Long employeeId = entry.getKey();
            List<Long> times = entry.getValue();
            double average = times.stream().mapToLong(Long::longValue).average().orElse(0);
            EmployeeRanking ranking = new EmployeeRanking(employeeId, average);
            rankingList.add(ranking);
        }


        rankingList.sort(Comparator.comparingDouble(EmployeeRanking::getAverageProcessingTimeInMinutes));

        return rankingList;
    }
}
