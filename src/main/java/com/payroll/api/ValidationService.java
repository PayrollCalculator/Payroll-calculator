package com.payroll.api;

import com.payroll.exception.ValidationException;
import com.payroll.model.*;

import java.util.List;
import java.util.Map;

/**
 * Interface for data validation.
 * Defines methods for checking data correctness.
 */
public interface ValidationService {

    /**
     * Validates the correctness of all data.
     *
     * @param employees List of employees
     * @param rates Payment rates
     * @param calendar Calendar data
     * @param overtimes Overtime hours
     * @param taxClasses Tax classes
     * @throws ValidationException If invalid data is found
     */
    void validateData(
        List<Employee> employees,
        Map<String, Rate> rates,
        List<Calendar> calendar,
        Map<String, Overtime> overtimes,
        Map<String, TaxClass> taxClasses) throws ValidationException;
}
