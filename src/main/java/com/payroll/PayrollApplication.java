package com.payroll;

import com.payroll.service.impl.CalculationServiceImpl;
import com.payroll.service.impl.FileServiceImpl;
import com.payroll.service.impl.ValidationServiceImpl;
import com.payroll.util.ApacheCommonsCsvParser;
import com.payroll.util.DefaultFileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main application class that initializes and starts the payroll calculator.
 */
public class PayrollApplication {
    private static final Logger logger = LoggerFactory.getLogger(PayrollApplication.class);

    // File paths constants
    public static final String MAIN_DATA_PATH = "data/main_data.csv";
    public static final String RATE_DATA_PATH = "data/rate.csv";
    public static final String PAYMENT_DATA_PATH = "data/payments.csv";
    public static final String OVERTIME_DATA_PATH = "data/overtime_data.csv";
    public static final String TAX_CLASS_DATA_PATH = "data/tax_class_data.csv";
    public static final String OUTPUT_PATH = "data/result/main_data_result.csv";

    public static void main(String[] args) {
        // Initialization
        new PayrollCalculator(
            new FileServiceImpl(new ApacheCommonsCsvParser(new DefaultFileReader())),
            new ValidationServiceImpl(),
            new CalculationServiceImpl()
        ).run();

    }
}