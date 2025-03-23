package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.LogsNotFoundException;
import com.pragma.powerup.domain.model.EmployeeRanking;
import com.pragma.powerup.domain.model.OrderEfficiency;
import com.pragma.powerup.domain.model.Traceability;
import com.pragma.powerup.domain.spi.ITraceabilityPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TraceabilityUseCaseTest {

    @Mock
    private ITraceabilityPersistencePort traceabilityPersistencePort;

    @InjectMocks
    private TraceabilityUseCase traceabilityUseCase;


    private List<Traceability> createTestLogs(Long orderId, String finalStatus) {
        return List.of(
                new Traceability("1", orderId, 100L, "cliente@test.com",
                        LocalDateTime.now().minusHours(2), null, "PENDIENTE", null, null),
                new Traceability("2", orderId, 100L, "cliente@test.com",
                        LocalDateTime.now().minusHours(1), "PENDIENTE", "EN_PREPARACION", 200L, "empleado@test.com"),
                new Traceability("3", orderId, 100L, "cliente@test.com",
                        LocalDateTime.now(), "EN_PREPARACION", finalStatus, 200L, "empleado@test.com")
        );
    }

    @Test
    void saveTraceability_ShouldCallPersistencePort() {

        Traceability traceability = new Traceability();
        when(traceabilityPersistencePort.saveTraceability(traceability)).thenReturn(traceability);


        Traceability result = traceabilityUseCase.saveTraceability(traceability);


        verify(traceabilityPersistencePort).saveTraceability(traceability);
        assertThat(result).isEqualTo(traceability);
    }

    @Test
    void getTraceabilityByClient_ShouldReturnClientLogs() {

        Long clientId = 100L;
        List<Traceability> expectedLogs = List.of(new Traceability());
        when(traceabilityPersistencePort.getTraceabilityByClient(clientId)).thenReturn(expectedLogs);


        List<Traceability> result = traceabilityUseCase.getTraceabilityByClient(clientId);


        assertThat(result).isEqualTo(expectedLogs);
        verify(traceabilityPersistencePort).getTraceabilityByClient(clientId);
    }

    @Test
    void calculateOrdersEfficiency_ShouldCalculateCorrectTimes() {

        List<Long> orderIds = List.of(1L, 2L);
        List<Traceability> logs1 = createTestLogs(1L, "ENTREGADO");
        List<Traceability> logs2 = createTestLogs(2L, "CANCELADO");

        when(traceabilityPersistencePort.getLogsByOrderId(1L)).thenReturn(logs1);
        when(traceabilityPersistencePort.getLogsByOrderId(2L)).thenReturn(logs2);


        List<OrderEfficiency> result = traceabilityUseCase.calculateOrdersEfficiency(orderIds);


        assertThat(result)
                .hasSize(2)
                .extracting(OrderEfficiency::getFinalStatus)
                .containsExactly("ENTREGADO", "CANCELADO");
    }

    @Test
    void calculateOrdersEfficiency_ShouldThrowExceptionWhenNoResults() {

        List<Long> orderIds = List.of(999L);
        when(traceabilityPersistencePort.getLogsByOrderId(999L)).thenReturn(Collections.emptyList());


        assertThatThrownBy(() -> traceabilityUseCase.calculateOrdersEfficiency(orderIds))
                .isInstanceOf(LogsNotFoundException.class);
    }

    @Test
    void calculateEmployeeRanking_ShouldGroupAndSortCorrectly() {

        List<Long> orderIds = List.of(1L, 2L);
        List<Traceability> logs = createTestLogs(1L, "ENTREGADO");

        when(traceabilityPersistencePort.getLogsByOrderId(any())).thenReturn(logs);


        List<EmployeeRanking> result = traceabilityUseCase.calculateEmployeeRanking(orderIds);


        assertThat(result)
                .hasSize(1)
                .first()
                .satisfies(ranking -> {
                    assertThat(ranking.getEmployeeId()).isEqualTo(200L);
                    assertThat(ranking.getAverageProcessingTimeInMinutes()).isPositive();
                });
    }

    @Test
    void calculateEmployeeRanking_ShouldHandleMultipleEmployees() {

        List<Traceability> mixedLogs = Arrays.asList(
                new Traceability("1", 1L, 100L, "cliente@test.com",
                        LocalDateTime.now().minusHours(3), null, "PENDIENTE", null, null),
                new Traceability("2", 1L, 100L, "cliente@test.com",
                        LocalDateTime.now().minusHours(1), "PENDIENTE", "ENTREGADO", 200L, "empleado1@test.com"),
                new Traceability("3", 2L, 100L, "cliente@test.com",
                        LocalDateTime.now().minusHours(4), null, "PENDIENTE", null, null),
                new Traceability("4", 2L, 100L, "cliente@test.com",
                        LocalDateTime.now().minusHours(2), "PENDIENTE", "ENTREGADO", 300L, "empleado2@test.com")
        );

        when(traceabilityPersistencePort.getLogsByOrderId(1L)).thenReturn(mixedLogs.subList(0, 2));
        when(traceabilityPersistencePort.getLogsByOrderId(2L)).thenReturn(mixedLogs.subList(2, 4));


        List<EmployeeRanking> result = traceabilityUseCase.calculateEmployeeRanking(List.of(1L, 2L));


        assertThat(result)
                .hasSize(2)
                .extracting(EmployeeRanking::getEmployeeId)
                .containsExactly(200L, 300L);
    }
}