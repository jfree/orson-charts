/**
 * ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */
package com.orsoncharts.renderer;

import java.awt.Paint;

/**
 * A paint source.
 */
public interface XYZPaintSource {

    public Paint getPaint(int series, int item);
  
}
