package com.payroll.model;

/**
 * Overtime model.
 * Contains data about overtime hours.
 */
public class Overtime {
    private String employeeId;
    private int overtimeHours;

    // Constructors, getters, and setters

    public Overtime() {
    }

    public Overtime(String employeeId, int overtimeHours) {
        this.employeeId = employeeId;
        this.overtimeHours = overtimeHours;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(int overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    @Override
    public String toString() {
        return "Overtime{" +
            "employeeId='" + employeeId + '\'' +
            ", overtimeHours=" + overtimeHours +
            '}';
    }
}
