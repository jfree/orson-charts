/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orsoncharts.renderer;

import java.awt.Color;
import java.awt.Paint;

/**
 *
 * @author dgilbert
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