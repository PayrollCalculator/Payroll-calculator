package com.payroll.service.impl;

import com.payroll.api.ValidationService;
import com.payroll.exception.ValidationException;
import com.payroll.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of the data validation service.
 */
public class ValidationServiceImpl implements ValidationService {
    private static final Logger logger = LoggerFactory.getLogger(ValidationServiceImpl.class);

    public void validateData(
        List<Employee> employees,
        Map<String, Rate> rates,
        List<Payment> calendar,
        Map<String, Overtime> overtimes,
        Map<String, TaxClass> taxClasses) throws ValidationException{

        logger.info("Starting data validation");

        // Calendar check
        if (calendar == null) {
            logger.error("Calendar data is missing");
            throw new ValidationException("Calendar data is missing");
        }

//        if (calendar.getWorkingDays() <= 0) {
//            logger.error("Invalid number of working days in calendar: {}", calendar.getWorkingDays());
//            throw new ValidationException("Invalid number of working days in calendar: " + calendar.getWorkingDays());
//        }

        // Duplicate employee check
        Set<String> duplicateIds = findDuplicateEmployeeIds(employees);
        if (!duplicateIds.isEmpty()) {
            logger.warn("Found duplicate employee IDs: {}", duplicateIds);
        }

        // Cross-file data consistency check
        for (Employee employee : employees) {
            String employeeId = employee.getEmployeeId();

            // Required employee fields check
            if (employee.getFullName() == null || employee.getFullName().isEmpty()) {
                logger.warn("Employee {} has no name", employeeId);
            }

            if (employee.getTaxClass() == null || employee.getTaxClass().isEmpty()) {
                logger.warn("Employee {} has no tax class", employeeId);
            }

            // Check if rate exists
            if (!rates.containsKey(employeeId)) {
                logger.warn("No rate found for employee {}", employeeId);
            }

            // Tax class validity check
            if (employee.getTaxClass() != null && !employee.getTaxClass().isEmpty() &&
                !taxClasses.containsKey(employee.getTaxClass())) {
                logger.warn("Invalid tax class for employee {}: {}", employeeId, employee.getTaxClass());
            }
        }

        logger.info("Data validation completed");
    }

    private Set<String> findDuplicateEmployeeIds(List<Employee> employees) {
        Set<String> seenIds = new HashSet<>();
        Set<String> duplicateIds = new HashSet<>();

        for (Employee employee : employees) {
            String employeeId = employee.getEmployeeId();
            if (!seenIds.add(employeeId)) {
                duplicateIds.add(employeeId);
            }
        }

        return duplicateIds;
    }
}
