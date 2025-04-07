package com.payroll;

import com.payroll.api.CalculationService;
import com.payroll.api.FileService;
import com.payroll.api.ValidationService;
import com.payroll.exception.DataLoadException;
import com.payroll.exception.ValidationException;
import com.payroll.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Core calculator class that handles the payroll calculation workflow.
 */
public class PayrollCalculator {
    private static final Logger logger = LoggerFactory.getLogger(PayrollCalculator.class);

    private final FileService fileService;
    private final ValidationService validationService;
    private final CalculationService calculationService;

    /**
     * Constructs a PayrollCalculator with the required services.
     *
     * @param fileService File access service
     * @param validationService Data validation service
     * @param calculationService Calculation service
     */
    public PayrollCalculator(FileService fileService,
                             ValidationService validationService,
                             CalculationService calculationService) {
        this.fileService = fileService;
        this.validationService = validationService;
        this.calculationService = calculationService;
    }

    /**
     * Executes the payroll calculation process.
     */
    public void run() {
        logger.info("Starting payroll calculation process...");

        try {
            // Load data from files
            List<Employee> employees = fileService.loadEmployees(PayrollApplication.MAIN_DATA_PATH);
            Map<String, Rate> rates = fileService.loadRates(PayrollApplication.RATE_DATA_PATH);
            List<Calendar> calendar = fileService.loadCalendar(PayrollApplication.CALENDAR_DATA_PATH);
            Map<String, Overtime> overtimes = fileService.loadOvertimes(PayrollApplication.OVERTIME_DATA_PATH);
            Map<String, TaxClass> taxClasses = fileService.loadTaxClasses(PayrollApplication.TAX_CLASS_DATA_PATH);

            // Validate data
            validationService.validateData(employees, rates, calendar, overtimes, taxClasses);

            // Calculate payroll
            List<PaymentResult> results = calculationService.calculatePayroll(
                employees, rates, calendar, overtimes, taxClasses);

            // Save results
            fileService.saveResults(results, PayrollApplication.OUTPUT_PATH);

            logger.info("Payroll calculation completed successfully!");
            System.out.println("Payroll calculation completed successfully!");

        } catch (DataLoadException e) {
            logger.error("Error loading data: {}", e.getMessage(), e);
            System.err.println("Error loading data: " + e.getMessage());
            throw new RuntimeException("Payroll calculation failed due to data loading error", e);
        } catch (ValidationException e) {
            logger.error("Validation error: {}", e.getMessage(), e);
            System.err.println("Validation error: " + e.getMessage());
            throw new RuntimeException("Payroll calculation failed due to validation error", e);
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            System.err.println("Unexpected error: " + e.getMessage());
            throw new RuntimeException("Payroll calculation failed due to unexpected error", e);
        }
    }
}