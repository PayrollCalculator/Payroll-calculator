package com.payroll.api;

import com.payroll.model.*;

import java.util.List;
import java.util.Map;

/**
 * Interface for payroll calculation.
 * Defines methods for performing calculations.
 */
public interface CalculationService {

    /**
     * Calculates payroll for a list of employees.
     *
     * @param employees List of employees
     * @param rates Payment rates
     * @param calendar Calendar data
     * @param overtimes Overtime hours
     * @param taxClasses Tax classes
     * @return List of calculation results
     */
    List<PaymentResult> calculatePayroll(
        List<Employee> employees,
        Map<String, Rate> rates,
        List<Calendar> calendar,
        Map<String, Overtime> overtimes,
        Map<String, TaxClass> taxClasses);

    /**
     * Calculates base pay for an employee.
     *
     * @param employee Employee
     * @param rate Payment rate
     * @param calendar Calendar data
     * @param taxClass Tax class
     * @return Base pay amount
     */
    double calculateBasePay(Employee employee, Rate rate, Calendar calendar, TaxClass taxClass);

    /**
     * Calculates overtime pay.
     *
     * @param rate Payment rate
     * @param overtime Overtime hours
     * @return Overtime pay amount
     */
    double calculateOvertimePay(Rate rate, Overtime overtime);
}
