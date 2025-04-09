package com.payroll.model;

/**
 * Calendar model.
 * Contains calendar-related data.
 */
public class Payment {
    private int month;
    private int year;
    private String paymentDate;

    // Constructors, getters, and setters

    public Payment() {
    }

    public Payment(int month, int year, String paymentDate) {
        this.month = month;
        this.year = year;
        this.paymentDate = paymentDate;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }


    @Override
    public String toString() {
        return "Calendar{" +
            "month=" + month +
            ", year=" + year +
            ", paymentDate='" + paymentDate + '\'' +
            '}';
    }
}
