package com.pragma.powerup.infrastructure.out.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "traceability")
public class TraceabilityEntity {

    @Id
    private String id;

    private Long orderId;
    private Long clientId;
    private String clientEmail;
    private LocalDateTime date;
    private String lastStatus;
    private String newStatus;
    private Long employeeId;
    private String employeeEmail;

}
