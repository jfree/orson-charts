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

