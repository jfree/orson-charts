/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer.xyz;

import com.orsoncharts.util.ObjectUtils;
import java.awt.Color;

/**
 * A default implementation of the XYZPaintSource interface.
 */
public class DefaultXYZPaintSource implements XYZPaintSource {

    private Color[] paint;
    
    /**
     * Default constructor.
     */
    public DefaultXYZPaintSource() {
        this.paint = new Color[] { Color.RED, Color.BLUE, Color.YELLOW, 
            Color.GRAY, Color.GREEN};    
    }
    
    public DefaultXYZPaintSource(Color[] colors) {
        this.paint = colors;
    }
    
    @Override
    public Color getPaint(int series, int item) {
        return this.paint[series % this.paint.length];
    }

    @Override
    public Color getLegendPaint(int series) {
        return this.paint[series % this.paint.length];
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DefaultXYZPaintSource)) {
            return false;
        }
        DefaultXYZPaintSource that = (DefaultXYZPaintSource) obj;
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
