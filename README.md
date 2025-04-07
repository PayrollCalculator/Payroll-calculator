# Payroll Calculator

A console application for calculating employee salaries based on CSV file data.

## Project Description

Payroll Calculator processes employee data, rates, calendar information, overtime hours, and tax classes to calculate salaries according to a specified formula. The calculation results are saved to a CSV file.

## Requirements

- Java 21 or higher
- Maven

## Project Structure

```
PayrollCalculator/
├── src/              # Source code
├── data/             # Input CSV files
│   ├── main_data.csv
│   ├── rate.csv
│   ├── calendar_data.csv
│   ├── overtime_data.csv
│   └── tax_class_data.csv
├── data/result/      # Output directory
├── target/           # Compiled files (created during build)
├── logs/             # Execution logs
├── pom.xml           # Maven configuration
└── README.md         # This file
```

## Building and Running the Application

Maven provides a simple way to build and run the application.

1. Compile:
```
mvn clean compile
```

2. Run:
```
mvn exec:java
```

## Input and Output

The program reads CSV files from the `data/` directory and outputs results to `data/result/main_data_result.csv`.

## Calculation Logic

Salary is calculated using the formula:

```
Salary = (Dworked / Dtotal) × Rmonthly × Tcoef + Σ(Hovertime × Rovertime × 1.5)
```

Where:
- **Dworked** - Number of days worked by the employee
- **Dtotal** - Total number of working days in the month
- **Rmonthly** - Employee's monthly rate
- **Tcoef** - Tax class coefficient
- **Hovertime** - Overtime hours (maximum 10 hours)
- **Rovertime** - Overtime hourly rate
- **1.5** - Overtime multiplier

## Detailed Requirements

For detailed information about:
- Input file formats
- Output file format
- Calculation logic
- Error handling
- Special cases

Please see the [detailed requirements document](REQUIREMENTS.md).

## Preparing the Environment

Before running the application:

1. Make sure the `data` directory exists and contains all required CSV files
2. Create the output directory:
   ```
   mkdir -p data/result
   ```

## Troubleshooting

If you encounter issues:

1. Check the log files in the `logs/` directory
2. Ensure all required CSV files are present in the `data/` directory
3. Verify that the CSV files have the correct format and headers
4. Make sure you have the correct Java version installed:
   ```
   java -version
   ```
5. Confirm Maven is installed correctly:
   ```
   mvn -version
   ```
6. Check that output directory exists:
   ```
   mkdir -p data/result
   ```