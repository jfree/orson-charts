/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package com.orsoncharts.axis;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.Format;
import com.orsoncharts.util.ArgChecks;

/**
 * A {@link TickSelector} implementation that selects tick units in multiples 
 * of 1, 2 and 5.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
public class NumberTickSelector implements TickSelector, Serializable {

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
     * @param percentage  format the tick values as percentages. 
     */
    public NumberTickSelector(boolean percentage) {
        this.power = 0;
        this.factor = 1;
        this.percentage = percentage;
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
        if (!(obj instanceof NumberTickSelector)) {
            return false;
        }
        NumberTickSelector that = (NumberTickSelector) obj;
        if (this.percentage != that.percentage) {
            return false;
        }
        return true;
    }
    
}
