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

import com.orsoncharts.Range;
import com.orsoncharts.util.ArgChecks;

/**
 * A color scale that runs a linear gradient between two colors.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 * 
 * @since 1.1
 */
@SuppressWarnings("serial")
public class GradientColorScale extends AbstractColorScale 
        implements ColorScale {

    /** The color at the low end of the value range. */
    private Color lowColor;
    
    /** The color at the high end of the value range. */
    private Color highColor;
    
    /** Storage for the color samples. */
    private Color[] colors;

    /**
     * Creates a new instance with the specified value range and colors.
     * 
     * @param range  the data value range ({@code null} not permitted).
     * @param lowColor  the color for the low end of the data range 
     *     ({@code null} not permitted).
     * @param highColor  the color for the high end of the data range 
     *     ({@code null} not permitted).
     */
    public GradientColorScale(Range range, Color lowColor, Color highColor) {
        super(range);
        ArgChecks.nullNotPermitted(lowColor, "lowColor");
        ArgChecks.nullNotPermitted(highColor, "highColor");
        this.lowColor = lowColor;
        this.highColor = highColor;
        this.colors = new Color[255];
    }

    /**
     * Returns the color for the low end of the data value range.
     * 
     * @return The color (never {@code null}). 
     */
    public Color getLowColor() {
        return this.lowColor;
    }
    
    /**
     * Returns the color for the high end of the data value range.
     * 
     * @return The color (never {@code null}). 
     */
    public Color getHighColor() {
        return this.highColor;
    }
    
    /**
     * Returns the number of samples used by this color scale.
     * 
     * @return The number of samples. 
     */
    public int getSampleCount() {
        return this.colors.length;
    }

    /**
     * Returns the color corresponding to the specified data value.  If this
     * 
     * @param value  the data value.
     * 
     * @return The color (never {@code null}). 
     */
    @Override
    public Color valueToColor(double value) {
        Range r = getRange();
        if (value < r.getMin()) {
            return valueToColor(r.getMin());
        }
        if (value > r.getMax()) {
            return valueToColor(r.getMax());
        }
        double fraction = getRange().percent(value);
        int i = (int) (fraction * this.colors.length);
        if (this.colors[i] == null) {
            float[] lrgba = this.lowColor.getRGBComponents(null);
            float[] hrgba = this.highColor.getRGBComponents(null);
            float p = (float) fraction;
            this.colors[i] = new Color(lrgba[0] * (1 - p) + hrgba[0] * p,
                    lrgba[1] * (1 - p) + hrgba[1] * p,
                    lrgba[2] * (1 - p) + hrgba[2] * p,
                    lrgba[3] * (1 - p) + hrgba[3] * p);
        }
        return this.colors[i];
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
        if (!(obj instanceof GradientColorScale)) {
            return false;
        }
        GradientColorScale that = (GradientColorScale) obj;
        if (!this.lowColor.equals(that.lowColor)) {
            return false;
        }
        if (!this.highColor.equals(that.highColor)) {
            return false;
        }
        return super.equals(obj);
    }

}
