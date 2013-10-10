/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.xyz;

import java.awt.Color;
import com.orsoncharts.util.ObjectUtils;

/**
 * A standard implementation of the {@link XYZPaintSource} interface.
 */
public class StandardXYZPaintSource implements XYZPaintSource {

    /** The sequence of color objects to use for each series. */
    private Color[] paint;
    
    /**
     * Creates a new instance with default colors.
     */
    public StandardXYZPaintSource() {
        this(new Color[] { Color.RED, Color.BLUE, Color.YELLOW, 
            Color.GRAY, Color.GREEN});    
    }
    
    /**
     * Creates a new instance with the supplied sequence of colors.  The
     * supplied array must have at least one entry, and all entries must be
     * non-<code>null</code>.
     * 
     * @param colors  the colors (<code>null</code> not permitted). 
     */
    public StandardXYZPaintSource(Color[] colors) {
        this.paint = colors;
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
    public Color getPaint(int series, int item) {
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
        if (!(obj instanceof StandardXYZPaintSource)) {
            return false;
        }
        StandardXYZPaintSource that = (StandardXYZPaintSource) obj;
        if (this.paint.length != that.paint.length) {
            return false;
        }
        for (int i = 0; i < this.paint.length; i++) {
            if (!ObjectUtils.equalsPaint(this.paint[i], that.paint[i])) {
                return false;
            }
        }
        return true;
    }
    
}
