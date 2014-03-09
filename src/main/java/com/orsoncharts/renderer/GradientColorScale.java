/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of these source files is prohibited.
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
     * @param range  the data value range (<code>null</code> not permitted).
     * @param lowColor  the color for the low end of the data range 
     *     (<code>null</code> not permitted).
     * @param highColor  the color for the high end of the data range 
     *     (<code>null</code> not permitted).
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
     * @return The color (never <code>null</code>). 
     */
    public Color getLowColor() {
        return this.lowColor;
    }
    
    /**
     * Returns the color for the high end of the data value range.
     * 
     * @return The color (never <code>null</code>). 
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
     * @return The color (never <code>null</code>). 
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
     * @param obj  the object (<code>null</code> permitted).
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
