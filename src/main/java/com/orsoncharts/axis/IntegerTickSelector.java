/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.axis;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.Format;
import com.orsoncharts.util.ArgChecks;

/**
 * A {@link TickSelector} implementation that selects tick units in multiples 
 * of 1, 2 and 5 and only displays integer values.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 * 
 * @since 1.5
 */
@SuppressWarnings("serial")
public class IntegerTickSelector implements TickSelector, Serializable {

    private int power = 0;
    
    private int factor = 1;
    
    /**
     * Creates a new instance.
     */
    public IntegerTickSelector() {
        this.power = 0;
        this.factor = 1;
    }
    
    /**
     * Selects and returns a standard tick size that is greater than or equal to 
     * the specified reference value and, ideally, as close to it as possible 
     * (to minimise the number of iterations used by axes to determine the tick
     * size to use).  After a call to this method, the 
     * {@link #getCurrentTickSize()} method should return the selected tick 
     * size (there is a "pointer" to this tick size), the {@link #next()} 
     * method should move the pointer to the next (larger) standard tick size, 
     * and the {@link #previous()} method should move the pointer to the 
     * previous (smaller) standard tick size.
     * 
     * @param reference  the reference value (must be positive and finite).
     * 
     * @return The selected tick size. 
     */
    @Override
    public double select(double reference) {
        ArgChecks.finitePositiveRequired(reference, "reference");
        this.power = (int) Math.ceil(Math.log10(reference));
        this.factor = 1;
        return getCurrentTickSize();
    }

    /**
     * Move the cursor to the next (larger) tick size, if there is one.  
     * Returns {@code true} in the case that the cursor is moved, and 
     * {@code false} where there are a finite number of tick sizes and the
     * current tick size is the largest available.
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

    /**
     * Move the cursor to the previous (smaller) tick size, if there is one.  
     * Returns {@code true} in the case that the cursor is moved, and 
     * {@code false} where there are a finite number of tick sizes and the
     * current tick size is the smallest available.
     */
    @Override
    public boolean previous() {
        if (factor == 1) {
            if (power == 0) {
                return false;
            }
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
    
    private DecimalFormat df0 = new DecimalFormat("#,##0");

    @Override
    public Format getCurrentTickLabelFormat() {
        if (power >= 0 && power <= 6) {
            return df0;
        }
        return new DecimalFormat("0.0000E0");
    }
    
    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj ==  this) {
            return true;
        }
        if (!(obj instanceof IntegerTickSelector)) {
            return false;
        }
        return true;
    }
    
}
