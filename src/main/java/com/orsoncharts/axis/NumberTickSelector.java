/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.axis;

import java.text.DecimalFormat;
import java.text.Format;

/**
 *
 */
public class NumberTickSelector implements TickSelector {

    private int power = 0;
    
    private int factor = 1;
    
    /**
     * Creates a new instance.
     */
    public NumberTickSelector() {
        this.power = 0;
        this.factor = 1;
    }
    
    @Override
    public void select(double reference) {
        this.power = (int) Math.ceil(Math.log10(reference));
        this.factor = 1;
    }

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

    @Override
    public Format getCurrentTickLabelFormat() {
        if (power == -4) {
            return dfNeg4;
        }
        if (power == -3) {
            return dfNeg3;
        }
        if (power == -2) {
            return dfNeg2;
        }
        if (power == -1) {
            return dfNeg1;
        }
        if (power >= 0 && power <= 6) {
            return df0;
        }
        return new DecimalFormat("0.0000E0");
    }
    
}
