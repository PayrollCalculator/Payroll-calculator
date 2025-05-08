
PRCALC-17359 (Task 4) - Add Support for Tax Class Handling in Payroll Calculations

As part of enhancing the Payroll Calculator, we need to introduce support for handling employee tax classes. The tax class should influence the calculation of payroll deductions and net salary. This change ensures better accuracy and compliance with German payroll requirements.

**Tax Class Mapping (Simplified):**

| Tax Class | Description                      | Approx. Tax Rate |
|-----------|----------------------------------|------------------|
| 1         | Single, not in a partnership     | 20%              |
| 3         | Married, higher earner           | 10%              |
| 4         | Married, both earn similar       | 15%              |

**Scope:**
- Accept tax class as input per employee
- Map tax class to corresponding tax rate
- Apply rate during payroll calculations to determine tax deductions
- Ensure compatibility with existing calculations and outputs
