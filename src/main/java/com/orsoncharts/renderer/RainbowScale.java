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
 * A color scale that returns all the colors of the rainbow.  Instances of 
 * this class are immutable and serializable.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 * 
 * @since 1.1
 */
@SuppressWarnings("serial")
public class RainbowScale extends AbstractColorScale implements ColorScale {
    
    /** 
     * A range to include all hues.  This can be used for the 
     * {@code hueSubrange} argument in the constructor.
     */
    public static final Range ALL_HUES = new Range(0.0, 1.0);
    
    /** 
     * A hue subrange that restricts colors to the blue to red range.  This can
     * be used for the {@code hueSubrange} argument in the constructor.
     */
    public static final Range BLUE_TO_RED_RANGE = new Range(0.0, 0.6666);
    
    /** Storage for the color samples. */
    private Color[] colors;
    
    /** 
     * The subrange of hues (useful to restrict the scale to the range from
     * blue to red, which is common in charts).
     */
    private Range hueSubrange;
    
    /**
     * Creates a new rainbow scale for the specified value range, with 256 
     * color samples in the blue to red range.
     * 
     * @param range  the range ({@code null} not permitted).
     */
    public RainbowScale(Range range) {
        this(range, 256, BLUE_TO_RED_RANGE);
    }
    
    /**
     * Creates a new rainbow scale for the specified value range, with the
     * given number of samples and hues restricted to the specified range.
     * 
     * @param range  the range ({@code null} not permitted).
     * @param samples  the number of samples.
     * @param hueSubrange  the hue sub-range.
     */
    public RainbowScale(Range range, int samples, Range hueSubrange) {
        super(range);
        ArgChecks.nullNotPermitted(hueSubrange, "hueSubrange");
        this.colors = new Color[samples];
        this.hueSubrange = hueSubrange;
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
     * Returns the sub-range of hues used in this scale.
     * 
     * @return The sub-range of hues. 
     */
    public Range getHueSubrange() {
        return this.hueSubrange;
    }
    
    /**
     * Converts a value to a color on the scale.
     * 
     * @param value  the value.
     * 
     * @return A color (never {@code null}). 
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
        int i = (int) (fraction * (this.colors.length - 1));
        if (this.colors[i] == null) {
            this.colors[i] = createRainbowColor(fraction);
        }
        return this.colors[i];
    }
    
    /**
     * Creates the rainbow color corresponding to the specified fraction along
     * the scale range.
     * 
     * @param fraction  the fraction (0.0 to 1.0).
     * 
     * @return The color. 
     */
    private Color createRainbowColor(double fraction) {
        double inv = 1.0 - fraction;
        double hue = this.hueSubrange.value(inv);
        return Color.getHSBColor((float) hue, 1.0f, 1.0f);
    }
    
    /**
     * Tests this color scale for equality with an arbitrary object.
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
        if (!(obj instanceof RainbowScale)) {
            return false;
        }
        RainbowScale that = (RainbowScale) obj;
        if (this.colors.length != that.colors.length) {
            return false;
        }
        if (!this.hueSubrange.equals(that.hueSubrange)) {
            return false;
        }
        return super.equals(obj);
    }

}
