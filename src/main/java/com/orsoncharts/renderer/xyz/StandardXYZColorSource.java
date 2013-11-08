/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.xyz;

import java.awt.Color;
import java.io.Serializable;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ObjectUtils;

/**
 * A standard implementation of the {@link XYZPaintSource} interface.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
public class StandardXYZColorSource implements XYZColorSource, Serializable {

    /** The sequence of color objects to use for each series. */
    private Color[] colors;
    
    /**
     * Creates a new instance with default colors.
     */
    public StandardXYZColorSource() {
        this(new Color[] { Color.RED, Color.BLUE, Color.YELLOW, 
            Color.GRAY, Color.GREEN});    
    }
    
    /**
     * Creates a new instance that returns a single color for all series and
     * items.
     * 
     * @param color  the color (<code>null</code> not permitted).
     */
    public StandardXYZColorSource(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.colors = new Color[] { color };
    }
    
    /**
     * Creates a new instance with the supplied sequence of colors.  The
     * supplied array must have at least one entry, and all entries must be
     * non-<code>null</code>.
     * 
     * @param colors  the colors (<code>null</code> not permitted). 
     */
    public StandardXYZColorSource(Color[] colors) {
        ArgChecks.nullNotPermitted(colors, "colors");
        this.colors = colors;
    }
    
    /**
     * Returns the color to use for the specified item.
     * 
     * @param series  the series index.
     * @param item  the item index.
     * 
     * @return The color (never <code>null</code>). 
     */
    @Override
    public Color getColor(int series, int item) {
        return this.colors[series % this.colors.length];
    }

    /**
     * Returns the color to use in the legend for the specified series.
     * 
     * @param series  the series index.
     * 
     * @return The color (never <code>null</code>).
     */
    @Override
    public Color getLegendColor(int series) {
        return this.colors[series % this.colors.length];
    }
    
    /**
     * Tests this paint source for equality with an arbitrary object.
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
        if (!(obj instanceof StandardXYZColorSource)) {
            return false;
        }
        StandardXYZColorSource that = (StandardXYZColorSource) obj;
        if (this.colors.length != that.colors.length) {
            return false;
        }
        for (int i = 0; i < this.colors.length; i++) {
            if (!ObjectUtils.equalsPaint(this.colors[i], that.colors[i])) {
                return false;
            }
        }
        return true;
    }
    
}
