/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts;

import java.io.Serializable;

/**
 * Represents a range of data values (instances are immutable).
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
public class Range implements Serializable {

    /** The lower bound of the range. */
    private double min;

    /** The upper bound of the range. */
    private double max;

    /**
     * Creates a new range instance.
     * 
     * @param min  the lower bound of the range.
     * @param max  the upper bound of the range.
     */
    public Range(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("Requires min <= max.");
        }
        this.min = min;
        this.max = max;
    }

    /**
     * Returns the lower bound of the range.
     * 
     * @return The lower bound of the range.
     */
    public double getMin() {
        return this.min;
    }

    /**
     * Returns the upper bound of the range.
     * 
     * @return The upper bound of the range. 
     */
    public double getMax() {
        return this.max;
    }

    /**
     * Returns the length of the range.
     *
     * @return The length of the range.
     */
    public double getLength() {
        return this.max - this.min;
    }

    /**
     * Returns <code>true</code> if the range includes the specified value,
     * and <code>false</code> otherwise.
     * 
     * @param value  the value.
     * 
     * @return A boolean. 
     */
    public boolean contains(double value) {
        return value >= this.min && value <= this.max;
    }

    /**
     * Returns either (a) the supplied value, if it falls within the range, or
     * (b) the range minimum or maximum value, whichever is closest to value.
     * 
     * @param value  the value.
     * 
     * @return The pegged value. 
     */
    public double peggedValue(double value) {
        return Math.max(this.min, Math.min(this.max, value));
    }

    /**
     * Returns <code>true</code> if the range intersects the interval defined 
     * by the two bounds (the order of the bounds is not important), and
     * <code>false</code> otherwise.
     * 
     * @param bound1  the first boundary value.
     * @param bound2  the second boundary value.
     * 
     * @return A boolean. 
     */
    public boolean intersects(double bound1, double bound2) {
        double lowerBound = Math.min(bound1, bound2);
        double upperBound = Math.max(bound1, bound2);
        if (upperBound < this.min) {
            return false;
        }
        if (lowerBound > this.max) {
            return false;
        }
        return true;
    }
    
    /**
     * Returns the value as a percentage along the range.
     * 
     * @param value  the value.
     * 
     * @return The percentage. 
     */
    public double percent(double value) {
        return (value - this.min) / getLength();
    }
    
    public double firstStandardTickValue(double tickUnit) {
        return tickUnit * Math.ceil(this.min / tickUnit);
    }

    public double gridPoint(int index, int count) {
        double fraction = (double) index / (count - 1.0);
        return this.min + fraction * (this.max - this.min);
    }
  
    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Range)) {
            return false;
        }
        Range that = (Range) obj;
        if (this.min != that.min) {
            return false;
        }
        if (this.max != that.max) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (int) (Double.doubleToLongBits(this.min) 
                ^ (Double.doubleToLongBits(this.min) >>> 32));
        hash = 43 * hash + (int) (Double.doubleToLongBits(this.max) 
                ^ (Double.doubleToLongBits(this.max) >>> 32));
        return hash;
    }
    
    @Override
    public String toString() {
        return "Range[" + this.min + ", " + this.max + "]";
    }
}
