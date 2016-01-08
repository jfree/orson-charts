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

package com.orsoncharts.graphics3d;

import java.io.Serializable;

/**
 * A dimension in 3D (width, height and depth).  Instances of this class
 * are immutable.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
public final class Dimension3D implements Serializable {
  
    /** The width. */
    private double width;
  
    /** The height. */
    private double height;
  
    /** The depth. */
    private double depth;
  
    /**
     * Creates a new {@code Dimension3D} instance.  Instances of this 
     * class are immutable.
     * 
     * @param width  the width.
     * @param height  the height.
     * @param depth   the depth.
     */
    public Dimension3D(double width, double height, double depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }
  
    /**
     * Returns the width.
     * 
     * @return The width. 
     */
    public double getWidth() {
        return this.width;
    }
  
    /**
     * Returns the height.
     * 
     * @return The height.
     */
    public double getHeight() {
        return this.height;
    }
  
    /**
     * Returns the depth.
     * 
     * @return The depth. 
     */
    public double getDepth() {
        return this.depth;
    }

    /**
     * Returns the length of a diagonal from one corner of the box to another.
     * 
     * @return The length.
     */
    public double getDiagonalLength() {
        return Math.sqrt(this.depth * this.depth + this.height * this.height
                + this.width * this.width);
    }
    
    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object to test against ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dimension3D other = (Dimension3D) obj;
        if (Double.doubleToLongBits(this.width) != Double.doubleToLongBits(
                other.width)) {
            return false;
        }
        if (Double.doubleToLongBits(this.height) != Double.doubleToLongBits(
                other.height)) {
            return false;
        }
        if (Double.doubleToLongBits(this.depth) != Double.doubleToLongBits(
                other.depth)) {
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
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.width) 
                ^ (Double.doubleToLongBits(this.width) >>> 32));
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.height) 
                ^ (Double.doubleToLongBits(this.height) >>> 32));
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.depth) 
                ^ (Double.doubleToLongBits(this.depth) >>> 32));
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
        return "[" + this.width + ", " + this.height + ", " + this.depth + "]";
    }
}
