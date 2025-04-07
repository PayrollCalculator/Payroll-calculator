package com.payroll.model;

/**
 * Rate model.
 * Contains payment rate data.
 */
public class Rate {
    private String employeeId;
    private double rate;
    private double overtimeRate;

    // Constructors, getters, and setters

    public Rate() {
    }

    public Rate(String employeeId, double rate, double overtimeRate) {
        this.employeeId = employeeId;
        this.rate = rate;
        this.overtimeRate = overtimeRate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getOvertimeRate() {
        return overtimeRate;
    }

    public void setOvertimeRate(double overtimeRate) {
        this.overtimeRate = overtimeRate;
    }

    @Override
    public String toString() {
        return "Rate{" +
            "employeeId='" + employeeId + '\'' +
            ", rate=" + rate +
            ", overtimeRate=" + overtimeRate +
            '}';
    }
}
