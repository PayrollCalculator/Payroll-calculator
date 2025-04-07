package com.payroll.model;

/**
 * Tax class model.
 * Contains tax class data.
 */
public class TaxClass {
    private String taxClass;
    private double factor;

    // Constructors, getters, and setters

    public TaxClass() {
    }

    public TaxClass(String taxClass, double factor) {
        this.taxClass = taxClass;
        this.factor = factor;
    }

    public String getTaxClass() {
        return taxClass;
    }

    public void setTaxClass(String taxClass) {
        this.taxClass = taxClass;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    @Override
    public String toString() {
        return "TaxClass{" +
            "taxClass='" + taxClass + '\'' +
            ", factor=" + factor +
            '}';
    }
}
