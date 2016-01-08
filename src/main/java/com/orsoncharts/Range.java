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

package com.orsoncharts;

import java.io.Serializable;

import com.orsoncharts.util.ArgChecks;

/**
 * Represents a range of data values (instances are immutable).
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
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
     * Returns {@code true} if the range includes the specified value,
     * and {@code false} otherwise.
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
     * Returns {@code true} if the range intersects the interval defined 
     * by the two bounds (the order of the bounds is not important), and
     * {@code false} otherwise.
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
     * Returns {@code true} if this range intersects with
     * the specified range, and {@code false} otherwise.
     * 
     * @param range  the range ({@code null} not permitted).
     * 
     * @return A boolean.
     * 
     * @since 1.2
     */
    public boolean intersects(Range range) {
        ArgChecks.nullNotPermitted(range, "range");
        return intersects(range.getMin(), range.getMax());
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
     * Returns the value as a percentage along the range, with optionally the
     * result inverted (that is, p becomes 1.0 - p).
     * 
     * @param value  the value.
     * @param inverted  invert the result?
     * 
     * @return The percentage.
     * 
     * @since 1.5
     */
    public double percent(double value, boolean inverted) {
        double p = percent(value);
        if (inverted) {
            p = 1.0 - p;
        }
        return p;
    }
    
    /**
     * Returns the value corresponding to the specified percentage.
     * 
     * @param percent  the percentage along the range.
     * 
     * @return The value.
     * 
     * @since 1.1
     */
    public double value(double percent) {
        return this.min + percent * (this.max - this.min);
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

    /**
     * Returns a hash code for this instance.
     * 
     * @return A hash code. 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (int) (Double.doubleToLongBits(this.min) 
                ^ (Double.doubleToLongBits(this.min) >>> 32));
        hash = 43 * hash + (int) (Double.doubleToLongBits(this.max) 
                ^ (Double.doubleToLongBits(this.max) >>> 32));
        return hash;
    }
    
    /**
     * Returns a string representation of this instance, primarily for
     * debugging purposes.
     * 
     * @return A string. 
     */
    @Override
    public String toString() {
        return "Range[" + this.min + ", " + this.max + "]";
    }
}
