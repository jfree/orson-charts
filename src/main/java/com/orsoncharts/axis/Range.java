/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.axis;

/**
 * Represents a range of data values.
 */
public class Range {

    private double min;

    private double max;

    public Range(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double getMin() {
        return this.min;
    }

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

    public boolean contains(double value) {
        return value >= this.min && value <= this.max;
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
    public String toString() {
        return "Range[" + this.min + ", " + this.max + "]";
    }
}
