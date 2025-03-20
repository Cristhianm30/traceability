package com.pragma.powerup.domain.model;

public class OrderEfficiency {

    private Long orderId;
    private long processingTimeInMinutes;
    private String finalStatus;

    public OrderEfficiency() {
    }

    public OrderEfficiency(Long orderId, long processingTimeInMinutes, String finalStatus) {
        this.orderId = orderId;
        this.processingTimeInMinutes = processingTimeInMinutes;
        this.finalStatus = finalStatus;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public long getProcessingTimeInMinutes() {
        return processingTimeInMinutes;
    }

    public void setProcessingTimeInMinutes(long processingTimeInMinutes) {
        this.processingTimeInMinutes = processingTimeInMinutes;
    }

    public String getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(String finalStatus) {
        this.finalStatus = finalStatus;
    }
}
