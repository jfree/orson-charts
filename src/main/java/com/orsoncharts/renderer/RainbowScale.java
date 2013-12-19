/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer;

import java.awt.Color;
import com.orsoncharts.Range;
import com.orsoncharts.util.ArgChecks;

/**
 * A color scale that returns all the colors of the rainbow.  Instances of 
 * this class are immutable and serializable.
 * 
 * @since 1.1
 */
public class RainbowScale extends AbstractColorScale implements ColorScale {
    
    /** 
     * A range to include all hues.  This can be used for the 
     * <code>hueSubrange</code> argument in the constructor.
     */
    public static final Range ALL_HUES = new Range(0.0, 1.0);
    
    /** 
     * A hue subrange that restricts colors to the blue to red range.  This can
     * be used for the  <code>hueSubrange</code> argument in the constructor.
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
     * @param range  the range (<code>null</code> not permitted).
     */
    public RainbowScale(Range range) {
        this(range, 256, BLUE_TO_RED_RANGE);
    }
    
    /**
     * Creates a new rainbow scale for the specified value range, with the
     * given number of samples and hues restricted to the specified range.
     * 
     * @param range  the range (<code>null</code> not permitted).
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
     * @return A color (never <code>null</code>). 
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
     * @param obj  the object (<code>null</code> permitted).
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
