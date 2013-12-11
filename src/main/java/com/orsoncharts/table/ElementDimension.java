/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.table;

import java.awt.geom.Dimension2D;
import java.io.Serializable;

/**
 * An element dimension (in fact a simple implementation of the 
 * <code>Dimension2D</code> interface).
 */
public final class ElementDimension extends Dimension2D 
        implements Serializable {

    /** The width (in Java2D units). */
    private double width;
    
    /** The height (in Java2D units). */
    private double height;
  
    /**
     * Creates a new dimension object.
     * 
     * @param width  the width.
     * @param height  the height.
     */
    public ElementDimension(double width, double height) {
        super();
        this.width = width;
        this.height = height;
    }
    
    /**
     * Returns the width.
     * 
     * @return The width. 
     */
    @Override
    public double getWidth() {
        return this.width;
    }

    /**
     * Returns the height.
     * 
     * @return The height.
     */
    @Override
    public double getHeight() {
        return this.height;
    }

    /**
     * Sets the size.
     * 
     * @param width  the width.
     * @param height  the height.
     */
    @Override
    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    /**
     * Tests this dimension for equality with an arbitrary object.
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
        if (!(obj instanceof ElementDimension)) {
            return false;
        }
        ElementDimension that = (ElementDimension) obj;
        if (this.width != that.width) {
            return false;
        }
        if (this.height != that.height) {
            return false;
        }
        return true;
    }
 
    /**
     * Returns a string representation of this dimension, primarily for
     * debugging purposes.
     * 
     * @return A string. 
     */
    @Override
    public String toString() {
        return "ElementDimension(" + this.width + ", " + this.height + ")";
    }
}
