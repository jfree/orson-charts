/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.xyz;

import java.awt.Color;
import java.awt.Paint;

/**
 * Default paint source.
 */
public class DefaultXYZPaintSource implements XYZPaintSource {

    private Color[] paint;
    
    public DefaultXYZPaintSource() {
        this.paint = new Color[] { Color.RED, Color.BLUE, Color.YELLOW, 
            Color.GRAY, Color.GREEN};    
    }
    
    @Override
    public Color getPaint(int series, int item) {
        return this.paint[series % this.paint.length];
    }

    @Override
    public Color getLegendPaint(int series) {
        return this.paint[series % this.paint.length];
    }
    
}
