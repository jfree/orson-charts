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
 * An offset {@code (dx, dy, dz)} in three dimensional space.  Instances 
 * of this class are immutable.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 * 
 * @since 1.3
 */
@SuppressWarnings("serial")
public final class Offset3D implements Serializable {
    
    /** Zero offset. */
    public static final Offset3D ZERO_OFFSET = new Offset3D(0, 0, 0);
    
    /** The x-offset. */
    private double dx;
    
    /** The y-offset. */
    private double dy;
    
    /** The z-offset. */
    private double dz;
    
    /**
     * Default constructor ({@code (0, 0)}).
     */
    public Offset3D() {
        this(0.0, 0.0, 0.0);
    }
    
    /**
     * Creates a new instance.
     * 
     * @param dx  the x-offset.
     * @param dy  the y-offset.
     * @param dz  the z-offset.
     */
    public Offset3D(double dx, double dy, double dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }
    
    /**
     * Returns the x-offset.
     * 
     * @return The x-offset. 
     */
    public double getDX() {
        return this.dx;
    }
    
    /**
     * Returns the y-offset.
     * 
     * @return The y-offset. 
     */
    public double getDY() {
        return this.dy;
    }
    
    /**
     * Returns the z-offset.
     * 
     * @return The z-offset. 
     */    
    public double getDZ() {
        return this.dz;
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
        if (!(obj instanceof Offset3D)) {
            return false;
        }
        Offset3D that = (Offset3D) obj;
        if (this.dx != that.dx) {
            return false;
        }
        if (this.dy != that.dy) {
            return false;
        }
        if (this.dz != that.dz) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.dx) 
                ^ (Double.doubleToLongBits(this.dx) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.dy) 
                ^ (Double.doubleToLongBits(this.dy) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.dz) 
                ^ (Double.doubleToLongBits(this.dz) >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return "[" + this.dx + ", " + this.dy + ", " + this.dz + "]";
    }
}
