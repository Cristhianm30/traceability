package com.pragma.powerup.domain.model;

import java.time.LocalDateTime;

public class Traceability {

    private String id;
    private Long orderId;
    private Long clientId;
    private String clientEmail;
    private LocalDateTime date;
    private String lastStatus;
    private String newStatus;
    private Long employeeId;
    private String employeeEmail;

    public Traceability() {
    }

    public Traceability(String id, Long orderId, Long clientId, String clientEmail, LocalDateTime date, String lastStatus, String newStatus, Long employeeId, String employeeEmail) {
        this.id = id;
        this.orderId = orderId;
        this.clientId = clientId;
        this.clientEmail = clientEmail;
        this.date = date;
        this.lastStatus = lastStatus;
        this.newStatus = newStatus;
        this.employeeId = employeeId;
        this.employeeEmail = employeeEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }
}
