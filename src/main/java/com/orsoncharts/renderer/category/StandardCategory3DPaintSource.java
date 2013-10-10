/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.category;

import java.awt.Color;
import java.io.Serializable;
import java.util.Arrays;
import com.orsoncharts.util.ArgChecks;

/**
 * A standard implementation of the {@link Category3DPaintSource} interface.
 * <br><br>
 * NOTE: The serialization format is subject to change in future releases and
 * should not be relied upon for persisting instances of this class.
 */
public final class StandardCategory3DPaintSource 
        implements Category3DPaintSource, Serializable {

    /** The sequence of color objects to use for each series. */
    private Color[] paint;
    
    /**
     * Creates a new instance with default colors.
     */
    public StandardCategory3DPaintSource() {
        this(new Color[] { new Color(0, 55, 122),  
            new Color(24, 123, 58), Color.RED, Color.YELLOW });
    }
    
    /**
     * Creates a new instance with the supplied sequence of colors.  The
     * supplied array must have at least one entry, and all entries must be
     * non-<code>null</code>.
     * 
     * @param colors  the colors (<code>null</code> not permitted). 
     */
    public StandardCategory3DPaintSource(Color[] colors) {
        ArgChecks.nullNotPermitted(colors, "colors");
        if (colors.length == 0) {
            throw new IllegalArgumentException(
                    "Zero length array not permitted.");
        }
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == null) {
                throw new IllegalArgumentException(
                        "Null array entries not permitted.");
            }
        }
        this.paint = colors.clone();
    }
    
    /**
     * Returns the color to use for the specified item.
     * 
     * @param series  the series index.
     * @param row  the row index.
     * @param column  the column index.
     * 
     * @return The color (never <code>null</code>). 
     */
    @Override
    public Color getPaint(int series, int row, int column) {
        return this.paint[series % this.paint.length];
    }

    /**
     * Returns the color to use in the legend for the specified series.
     * 
     * @param series  the series index.
     * 
     * @return The color (never <code>null</code>).
     */
    @Override
    public Color getLegendPaint(int series) {
        return this.paint[series % this.paint.length];
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
        if (!(obj instanceof StandardCategory3DPaintSource)) {
            return false;
        }
        StandardCategory3DPaintSource that 
                = (StandardCategory3DPaintSource) obj;
        if (!Arrays.equals(this.paint, that.paint)) {
            return false;
        }
        return true;
    }
}