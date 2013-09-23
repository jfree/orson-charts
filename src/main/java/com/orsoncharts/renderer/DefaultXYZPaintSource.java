/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orsoncharts.renderer;

import java.awt.Color;
import java.awt.Paint;

/**
 * Default paint source.
 */
public class DefaultXYZPaintSource implements XYZPaintSource {

    private Paint[] paint;
    
    public DefaultXYZPaintSource() {
        this.paint = new Paint[] { Color.RED, Color.BLUE, Color.YELLOW, 
            Color.GRAY, Color.GREEN};    
    }
    
    @Override
    public Paint getPaint(int series, int item) {
        return this.paint[series % this.paint.length];
    }
    
}
