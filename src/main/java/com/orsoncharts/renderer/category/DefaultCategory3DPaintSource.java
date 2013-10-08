/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.category;

import java.awt.Color;
import java.util.Arrays;

/**
 * A default implementation of the {@link Category3DPaintSource} interface.
 */
public class DefaultCategory3DPaintSource implements Category3DPaintSource {

    private Color[] paint;
    
    /**
     * Creates a new instance.
     */
    public DefaultCategory3DPaintSource() {
     //   this.paint = new Color[] { Color.RED, Color.BLUE, Color.YELLOW, 
     //       Color.GRAY, Color.GREEN};    
        
        this.paint = new Color[] { new Color(0, 55, 122),  
            new Color(24, 123, 58), Color.RED };
     
    }
    
    /**
     * Returns the color to use for the specified item.
     * 
     * @param series  the series index.
     * @param row  the row index.
     * @param column  the column index.
     * 
     * @return The color. 
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
     * @return The color. 
     */
    @Override
    public Color getLegendPaint(int series) {
        return this.paint[series % this.paint.length];
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DefaultCategory3DPaintSource)) {
            return false;
        }
        DefaultCategory3DPaintSource that = (DefaultCategory3DPaintSource) obj;
        if (!Arrays.equals(this.paint, that.paint)) {
            return false;
        }
        return true;
    }
}