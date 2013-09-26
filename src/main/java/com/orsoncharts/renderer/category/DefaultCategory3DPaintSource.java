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
        
        this.paint = new Color[] { new Color(0, 55, 122),  new Color(24, 123, 58), 
            Color.RED };
     
    }
    
    @Override
    public Color getPaint(int series, int row, int column) {
        return this.paint[series % this.paint.length];
    }
    
}