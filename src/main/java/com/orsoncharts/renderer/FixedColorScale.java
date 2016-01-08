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

package com.orsoncharts.renderer;

import java.awt.Color;
import java.io.Serializable;

import com.orsoncharts.Range;
import com.orsoncharts.renderer.xyz.SurfaceRenderer;
import com.orsoncharts.util.ArgChecks;

/**
 * A {@link ColorScale} that returns the same color for every value on the
 * scale.  This is used by the {@link SurfaceRenderer} when there is no need 
 * to represent different value levels by color.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 * 
 * @since 1.1
 */
@SuppressWarnings("serial")
public class FixedColorScale implements ColorScale, Serializable {
    
    /** The fixed color. */
    private Color color;
    
    /** 
     * The range (in fact this is here just to have something to return in the 
     * getRange() method, it is not used by this implementation since we 
     * always return the same color for all values).
     */
    private Range range;
    
    /**
     * Creates a new {@code FixedColorScale} instance.
     * 
     * @param color  the color ({@code null} not permitted). 
     */
    public FixedColorScale(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.color = color;
        this.range = new Range(0, 1);
    }

    /**
     * Returns the range {@code 0.0} to {@code 1.0} always.
     * 
     * @return The range (never {@code null}). 
     */
    @Override
    public Range getRange() {
        return this.range;
    }

    /**
     * Returns a single color (as specified in the constructor) for all values.
     * 
     * @param value  the value.
     * 
     * @return The fixed color.
     */
    @Override
    public Color valueToColor(double value) {
        return this.color;
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
        if (!(obj instanceof FixedColorScale)) {
            return false;
        }
        FixedColorScale that = (FixedColorScale) obj;
        if (!this.color.equals(that.color)) {
            return false;
        }
        return true;
    }
}
