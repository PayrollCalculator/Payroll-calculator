package com.payroll.model;

/**
 * Payment result model.
 * Contains data about the payroll calculation result.
 */
public class PaymentResult {
    private String employeeId;
    private double pay;
    private String date;
    private String settlementAccount;
    private String currency;

    // Constructors, getters, and setters

    public PaymentResult() {
    }

    public PaymentResult(String employeeId, double pay, String date, String settlementAccount, String currency) {
        this.employeeId = employeeId;
        this.pay = pay;
        this.date = date;
        this.settlementAccount = settlementAccount;
        this.currency = currency;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSettlementAccount() {
        return settlementAccount;
    }

    public void setSettlementAccount(String settlementAccount) {
        this.settlementAccount = settlementAccount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "PaymentResult{" +
            "employeeId='" + employeeId + '\'' +
            ", pay=" + pay +
            ", date='" + date + '\'' +
            ", settlementAccount='" + settlementAccount + '\'' +
            ", currency='" + currency + '\'' +
            '}';
    }
}
