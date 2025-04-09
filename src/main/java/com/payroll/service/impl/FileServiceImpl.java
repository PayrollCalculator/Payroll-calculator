package com.payroll.service.impl;

import com.payroll.api.FileService;
import com.payroll.exception.DataLoadException;
import com.payroll.model.*;
import com.payroll.util.CsvParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the file handling service.
 */
public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    private final CsvParser csvParser;

    public FileServiceImpl(CsvParser csvParser) {
        this.csvParser = csvParser;
    }

    @Override
    public List<Employee> loadEmployees(String filePath) throws DataLoadException {
        logger.info("Loading employees from {}", filePath);
        List<Employee> employees = new ArrayList<>();

        try {
            List<Map<String, String>> records = csvParser.parseWithHeaders(filePath);

            for (Map<String, String> record : records) {
                try {
                    Employee employee = new Employee(
                        record.get("FULL_NAME"),
                        record.get("LOCATION"),
                        record.get("EMPLOYEE_ID"),
                        record.get("TAX_CLASS"),
                        record.get("AT_LEVEL"),
                        record.get("STATUS"),
                        Integer.parseInt(record.get("DAYS_WORKED")),
                        record.get("PHONE"),
                        record.get("BIRTHDAY_DATE"),
                        record.get("PASSWORDS")
                    );

                    employees.add(employee);
                    logger.debug("Loaded employee: {}", employee);
                } catch (Exception e) {
                    logger.warn("Failed to parse employee data: {}", record, e);
                }
            }

            logger.info("Loaded {} employees", employees.size());
            return employees;
        } catch (Exception e) {
            logger.error("Error loading employees from {}", filePath, e);
            throw new DataLoadException("Error loading employees: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Rate> loadRates(String filePath) throws DataLoadException {
        logger.info("Loading rates from {}", filePath);
        Map<String, Rate> rates = new HashMap<>();

        try {
            List<Map<String, String>> records = csvParser.parseWithHeaders(filePath);

            for (Map<String, String> record : records) {
                try {
                    Rate rate = new Rate(
                        record.get("EMPLOYEE_ID"),
                        Double.parseDouble(record.get("RATE")),
                        Double.parseDouble(record.get("OVERTIME_RATE"))
                    );

                    rates.put(rate.getEmployeeId(), rate);
                    logger.debug("Loaded rate: {}", rate);
                } catch (Exception e) {
                    logger.warn("Failed to parse rate data: {}", record, e);
                }
            }

            logger.info("Loaded {} rates", rates.size());
            return rates;
        } catch (Exception e) {
            logger.error("Error loading rates from {}", filePath, e);
            throw new DataLoadException("Error loading rates: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Payment> loadPayments(String filePath) throws DataLoadException {
        logger.info("Loading payment data from {}", filePath);
        List<Payment> paymentList = new ArrayList<>();

        try {
            List<Map<String, String>> records = csvParser.parseWithHeaders(filePath);

            for (Map<String, String> record : records) {
                try {
                    List<Integer> holidays = new ArrayList<>();
                    String holidaysStr = record.get("HOLIDAYS");
                    if (holidaysStr != null && !holidaysStr.isEmpty()) {
                        String[] holidayStrings = holidaysStr.split(",");
                        for (String holiday : holidayStrings) {
                            holidays.add(Integer.parseInt(holiday.trim()));
                        }
                    }

                    Payment payment = new Payment(
                        Integer.parseInt(record.get("MONTH")),
                        Integer.parseInt(record.get("YEAR")),
                        record.get("PAYMENT_DATE")
                    );

                    paymentList.add(payment);
                    logger.debug("Loaded payment entry: {}", payment);
                } catch (Exception e) {
                    logger.warn("Failed to parse payment data: {}", record, e);
                }
            }

            logger.info("Loaded {} payment entries", paymentList.size());
            return paymentList;
        } catch (Exception e) {
            logger.error("Error loading payment from {}", filePath, e);
            throw new DataLoadException("Error loading payment data: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Overtime> loadOvertimes(String filePath) throws DataLoadException {
        logger.info("Loading overtimes from {}", filePath);
        Map<String, Overtime> overtimes = new HashMap<>();

        try {
            List<Map<String, String>> records = csvParser.parseWithHeaders(filePath);

            for (Map<String, String> record : records) {
                try {
                    Overtime overtime = new Overtime(
                        record.get("EMPLOYEE_ID"),
                        Integer.parseInt(record.get("OVERTIME_DATA"))
                    );

                    overtimes.put(overtime.getEmployeeId(), overtime);
                    logger.debug("Loaded overtime: {}", overtime);
                } catch (Exception e) {
                    logger.warn("Failed to parse overtime data: {}", record, e);
                }
            }

            logger.info("Loaded {} overtimes", overtimes.size());
            return overtimes;
        } catch (Exception e) {
            logger.error("Error loading overtimes from {}", filePath, e);
            throw new DataLoadException("Error loading overtimes: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, TaxClass> loadTaxClasses(String filePath) throws DataLoadException {
        logger.info("Loading tax classes from {}", filePath);
        Map<String, TaxClass> taxClasses = new HashMap<>();

        try {
            List<Map<String, String>> records = csvParser.parseWithHeaders(filePath);

            for (Map<String, String> record : records) {
                try {
                    TaxClass taxClass = new TaxClass(
                        record.get("TAX_CLASS"),
                        Double.parseDouble(record.get("FACTOR"))
                    );

                    taxClasses.put(taxClass.getTaxClass(), taxClass);
                    logger.debug("Loaded tax class: {}", taxClass);
                } catch (Exception e) {
                    logger.warn("Failed to parse tax class data: {}", record, e);
                }
            }

            logger.info("Loaded {} tax classes", taxClasses.size());
            return taxClasses;
        } catch (Exception e) {
            logger.error("Error loading tax classes from {}", filePath, e);
            throw new DataLoadException("Error loading tax classes: " + e.getMessage(), e);
        }
    }

    @Override
    public void saveResults(List<PaymentResult> results, String filePath) throws DataLoadException {
        logger.info("Saving results to {}", filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write header
            writer.write("EMPLOYEE_ID;PAY;DATE;SETTLEMENT_ACCOUNT;CURRENCY");
            writer.newLine();

            // Write data
            for (PaymentResult result : results) {
                writer.write(String.format("%s;%.2f;%s;%s;%s",
                    result.getEmployeeId(),
                    result.getPay(),
                    result.getDate(),
                    result.getSettlementAccount(),
                    result.getCurrency()));
                writer.newLine();

                logger.debug("Saved result: {}", result);
            }

            logger.info("Saved {} results", results.size());
        } catch (IOException e) {
            logger.error("Error saving results to {}", filePath, e);
            throw new DataLoadException("Error saving results: " + e.getMessage(), e);
        }
    }
}