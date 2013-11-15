/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data.xyz;

import java.io.Serializable;

/**
 * Represents a single <code>(x, y, z)</code> data item, which can be added to 
 * a {@link XYZSeries}.  Instances of this class are immutable.
 */
public class XYZDataItem implements Serializable {

    /** The x-value. */
    private double x;

    /** The y-value. */
    private double y;

    /** The z-value. */
    private double z;

    /**
     * Creates a new (immutable) instance.
     * 
     * @param x  the x-value.
     * @param y  the y-value.
     * @param z  the z-value.
     */
    public XYZDataItem(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns the x-value.
     * 
     * @return The x-value. 
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y-value.
     * 
     * @return The y-value.
     */
    public double getY() {
        return y;
    }

    /**
     * Returns the z-value.
     * 
     * @return The z-value. 
     */
    public double getZ() {
        return z;
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
        if (!(obj instanceof XYZDataItem)) {
            return false;
        }
        XYZDataItem that = (XYZDataItem) obj;
        if (this.x != that.x) {
            return false;
        }
        if (this.y != that.y) {
            return false;
        }
        if (this.z != that.z) {
            return false;
        }
        return true;
    }

}
