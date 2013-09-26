/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.category;

import java.awt.Color;

/**
 * 
 */
public class DefaultCategory3DPaintSource implements Category3DPaintSource {

    private Color[] paint;
    
    public DefaultCategory3DPaintSource() {
        this.paint = new Color[] { Color.RED, Color.BLUE, Color.YELLOW, 
            Color.GRAY, Color.GREEN};    
    }
    
    @Override
    public Color getPaint(int series, int row, int column) {
        return this.paint[series % this.paint.length];
    }
    
}