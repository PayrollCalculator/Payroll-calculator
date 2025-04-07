package com.payroll.api;

import com.payroll.exception.DataLoadException;
import com.payroll.model.*;

import java.util.List;
import java.util.Map;

/**
 * Interface for working with data files.
 * Defines methods for loading and saving data.
 */
public interface FileService {

    /**
     * Loads employee data from a file.
     *
     * @param filePath Path to the data file
     * @return List of employees
     * @throws DataLoadException If an error occurs while loading data
     */
    List<Employee> loadEmployees(String filePath) throws DataLoadException;

    /**
     * Loads rate data from a file.
     *
     * @param filePath Path to the data file
     * @return Map of rates, where the key is the employee ID
     * @throws DataLoadException If an error occurs while loading data
     */
    Map<String, Rate> loadRates(String filePath) throws DataLoadException;

    /**
     * Loads calendar data from a file.
     *
     * @param filePath Path to the data file
     * @return List of calendar entries
     * @throws DataLoadException If an error occurs while loading data
     */
    List<Calendar> loadCalendar(String filePath) throws DataLoadException;

    /**
     * Loads overtime data from a file.
     *
     * @param filePath Path to the data file
     * @return Map of overtimes, where the key is the employee ID
     * @throws DataLoadException If an error occurs while loading data
     */
    Map<String, Overtime> loadOvertimes(String filePath) throws DataLoadException;

    /**
     * Loads tax class data from a file.
     *
     * @param filePath Path to the data file
     * @return Map of tax classes, where the key is the tax class ID
     * @throws DataLoadException If an error occurs while loading data
     */
    Map<String, TaxClass> loadTaxClasses(String filePath) throws DataLoadException;

    /**
     * Saves calculation results to a file.
     *
     * @param results Calculation results
     * @param filePath Path to the file for saving
     * @throws DataLoadException If an error occurs while saving data
     */
    void saveResults(List<PaymentResult> results, String filePath) throws DataLoadException;
}
