/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orsoncharts.renderer;

import java.awt.Color;

/**
 * A paint source.
 */
public interface Category3DPaintSource {

    /**
     * Returns the paint for one item.  We return a Color for now, because of
     * some manipulations done in the 3D rendering.
     * 
     * @param series
     * @param row
     * @param column 
     * 
     * @return 
     */
    public Color getPaint(int series, int row, int column);
  
}

