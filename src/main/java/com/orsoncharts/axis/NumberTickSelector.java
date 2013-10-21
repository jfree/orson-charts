/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.axis;

import java.text.DecimalFormat;
import java.text.Format;

/**
 * A {@link TickSelector} implementation that selects tick units in multiples 
 * of 1, 2 and 5.
 */
public class NumberTickSelector implements TickSelector {

    private int power = 0;
    
    private int factor = 1;
    
    /** 
     * A flag to track if the units are percentage values, in which case the
     * formatter should display less decimal places.
     */
    private boolean percentage;
    
    /**
     * Creates a new instance.
     */
    public NumberTickSelector() {
        this(false);
    }
    
    /**
     * Creates a new instance, with the option to display the tick values as
     * percentages.  The axis follows the normal convention that values in the
     * range 0.0 to 1.0 a represented as 0% to 100%.
     * 
     * @param boolean percentage  format the tick values as percentages. 
     */
    public NumberTickSelector(boolean percentage) {
        this.power = 0;
        this.factor = 1;
        this.percentage = percentage;
    }
    
    /**
     * Selects a standard tick size that is near to the specified reference
     * value.
     * 
     * @param reference  the reference value.
     * 
     * @return The selected tick size. 
     */
    @Override
    public double select(double reference) {
        this.power = (int) Math.ceil(Math.log10(reference));
        this.factor = 1;
        return getCurrentTickSize();
    }

    /**
     * 
     * @return 
     */
    @Override
    public boolean next() {
        if (factor == 1) {
            factor = 2;
            return true;
        } 
        if (factor == 2) {
            factor = 5;
            return true;  
        } 
        if (factor == 5) {
            power++;
            factor = 1;
            return true;
        } 
        throw new IllegalStateException("We should never get here.");
    }

    @Override
    public boolean previous() {
        if (factor == 1) {
            factor = 5;
            power--;
            return true;
        } 
        if (factor == 2) {
            factor = 1;
            return true;  
        } 
        if (factor == 5) {
            factor = 2;
            return true;
        } 
        throw new IllegalStateException("We should never get here.");
    }

    @Override
    public double getCurrentTickSize() {
        return this.factor * Math.pow(10.0, this.power);
    }
    
    private DecimalFormat dfNeg4 = new DecimalFormat("0.0000");
    private DecimalFormat dfNeg3 = new DecimalFormat("0.000");
    private DecimalFormat dfNeg2 = new DecimalFormat("0.00");
    private DecimalFormat dfNeg1 = new DecimalFormat("0.0");
    private DecimalFormat df0 = new DecimalFormat("#,##0");
    private DecimalFormat dfNeg4P = new DecimalFormat("0.00%");
    private DecimalFormat dfNeg3P = new DecimalFormat("0.0%");
    private DecimalFormat dfNeg2P = new DecimalFormat("0%");
    private DecimalFormat dfNeg1P = new DecimalFormat("0%");
    private DecimalFormat df0P = new DecimalFormat("#,##0%");

    @Override
    public Format getCurrentTickLabelFormat() {
        if (power == -4) {
            return this.percentage ? dfNeg4P : dfNeg4;
        }
        if (power == -3) {
            return this.percentage ? dfNeg3P : dfNeg3;
        }
        if (power == -2) {
            return this.percentage ? dfNeg2P : dfNeg2;
        }
        if (power == -1) {
            return this.percentage ? dfNeg1P : dfNeg1;
        }
        if (power >= 0 && power <= 6) {
            return this.percentage ? df0P : df0;
        }
        return this.percentage ? new DecimalFormat("0.0000E0%") 
                : new DecimalFormat("0.0000E0");
    }
    
}
