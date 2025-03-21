package com.pragma.powerup.domain.model;

public class EmployeeRanking {

    private Long employeeId;

    private double averageProcessingTimeInMinutes;

    public EmployeeRanking() {
    }

    public EmployeeRanking(Long employeeId,  double averageProcessingTimeInMinutes) {
        this.employeeId = employeeId;

        this.averageProcessingTimeInMinutes = averageProcessingTimeInMinutes;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }


    public double getAverageProcessingTimeInMinutes() {
        return averageProcessingTimeInMinutes;
    }

    public void setAverageProcessingTimeInMinutes(double averageProcessingTimeInMinutes) {
        this.averageProcessingTimeInMinutes = averageProcessingTimeInMinutes;
    }
}
