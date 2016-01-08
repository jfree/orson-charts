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

package com.orsoncharts.table;

import java.awt.geom.Dimension2D;
import java.io.Serializable;

/**
 * An element dimension (in fact a simple implementation of the 
 * {@code Dimension2D} interface).
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
@SuppressWarnings("serial")
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
     * @param obj  the object ({@code null} permitted).
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
