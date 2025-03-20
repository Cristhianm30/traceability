package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.ITraceabilityServicePort;
import com.pragma.powerup.domain.spi.ITraceabilityPersistencePort;
import com.pragma.powerup.domain.usecase.TraceabilityUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    @Bean
    public ITraceabilityServicePort traceabilityServicePort(ITraceabilityPersistencePort traceabilityPersistencePort){
        return new TraceabilityUseCase(traceabilityPersistencePort);
    }


}
