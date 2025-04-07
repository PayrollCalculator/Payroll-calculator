package com.payroll.model;

import java.util.List;

/**
 * Calendar model.
 * Contains calendar-related data.
 */
public class Calendar {
    private int month;
    private int year;
    private int workingDays;
    private String paymentDate;
    private List<Integer> holidays;

    // Constructors, getters, and setters

    public Calendar() {
    }

    public Calendar(int month, int year, int workingDays, String paymentDate, List<Integer> holidays) {
        this.month = month;
        this.year = year;
        this.workingDays = workingDays;
        this.paymentDate = paymentDate;
        this.holidays = holidays;
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

    public int getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(int workingDays) {
        this.workingDays = workingDays;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public List<Integer> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<Integer> holidays) {
        this.holidays = holidays;
    }

    @Override
    public String toString() {
        return "Calendar{" +
            "month=" + month +
            ", year=" + year +
            ", workingDays=" + workingDays +
            ", paymentDate='" + paymentDate + '\'' +
            '}';
    }
}
