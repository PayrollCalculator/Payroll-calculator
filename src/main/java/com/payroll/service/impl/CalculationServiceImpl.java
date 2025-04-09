package com.payroll.service.impl;

import com.payroll.api.CalculationService;
import com.payroll.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the payroll calculation service.
 */
public class CalculationServiceImpl implements CalculationService {
    private static final Logger logger = LoggerFactory.getLogger(CalculationServiceImpl.class);
    private static final double OVERTIME_COEFFICIENT = 1.5;
    private static final int MAX_OVERTIME_HOURS = 10; // Maximum allowed overtime hours

    @Override
    public List<PaymentResult> calculatePayroll(
        List<Employee> employees,
        Map<String, Rate> rates,
        List<Payment> calendars,
        Map<String, Overtime> overtimes,
        Map<String, TaxClass> taxClasses) {

        logger.info("Starting payroll calculation for {} employees", employees.size());
        List<PaymentResult> results = new ArrayList<>();

        if (calendars == null || calendars.isEmpty()) {
            logger.error("No calendar data available for calculations");
            return results;
        }

        // Use the first calendar entry for now - this could be enhanced to select
        // the appropriate calendar entry based on current date or other criteria
        Payment currentCalendar = calendars.get(0);
        logger.info("Using calendar entry: Month {}, Year {}", currentCalendar.getMonth(), currentCalendar.getYear());

        for (Employee employee : employees) {
            String employeeId = employee.getEmployeeId();
            Rate rate = rates.get(employeeId);
            Overtime overtime = overtimes.get(employeeId);
            TaxClass taxClass = taxClasses.get(employee.getTaxClass());

            if (rate == null) {
                logger.warn("No rate found for employee: {}", employeeId);
                continue; // Skip if no rate data is available
            }

            // Base pay calculation
            double basePay = calculateBasePay(employee, rate, currentCalendar);
            logger.debug("Base pay for employee {}: {}", employeeId, basePay);

            // Overtime calculation
            double overtimePay = calculateOvertimePay(rate, overtime);
            logger.debug("Overtime pay for employee {}: {}", employeeId, overtimePay);

            // Total pay
            double totalPay = basePay + overtimePay;
            logger.debug("Total pay for employee {}: {}", employeeId, totalPay);

            // Create result
            PaymentResult result = new PaymentResult(
                employeeId,
                totalPay,
                currentCalendar.getMonth() + "." + currentCalendar.getPaymentDate() + "." + currentCalendar.getYear(),
                generateSettlementAccount(employee),
                "EUR"
            );

            results.add(result);
            logger.info("Calculated payment for employee {}: {}", employeeId, result);
        }

        logger.info("Payroll calculation completed for {} employees", results.size());
        return results;
    }

    @Override
    public double calculateBasePay(Employee employee, Rate rate, Payment calendar) {
        // Formula: (Ndays / Ndays in month) * Monthly Rate * TaxClassCoef
        double daysRatio = (double) employee.getDaysWorked() / 30;
        double taxFactor = 1.0; // TODO: Implement tax class coefficient calculation

        logger.debug("Days ratio for employee {}: {}", employee.getEmployeeId(), daysRatio);
        logger.debug("Tax factor for employee {}: {}", employee.getEmployeeId(), taxFactor);

        return daysRatio * rate.getRate() * taxFactor;
    }

    @Override
    public double calculateOvertimePay(Rate rate, Overtime overtime) {
        if (overtime == null) {
            logger.debug("No overtime data for employee {}", rate.getEmployeeId());
            return 0;
        }

        // Enforce maximum overtime hours
        int overtimeHours = Math.min(overtime.getOvertimeHours(), MAX_OVERTIME_HOURS);
        if (overtimeHours != overtime.getOvertimeHours()) {
            logger.warn("Overtime hours for employee {} limited from {} to {}",
                rate.getEmployeeId(), overtime.getOvertimeHours(), overtimeHours);
        }

        // Formula: Overtime H * Overtime Rate * coefficient 1.5
        return overtimeHours * rate.getOvertimeRate() * OVERTIME_COEFFICIENT;
    }

    private String generateSettlementAccount(Employee employee) {
        // Generate settlement account based on employee name
        // Example: use first 4 characters of full name
        String fullName = employee.getFullName();
        if (fullName == null || fullName.length() < 4) {
            logger.warn("Cannot generate settlement account for employee {}: invalid name", employee.getEmployeeId());
            return "DEFAULT";
        }
        return fullName.substring(0, 4).toUpperCase();
    }
}