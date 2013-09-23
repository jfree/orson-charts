/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.axis;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 * An interface that must be supported by axes for XYZPlot.
 */
public interface Axis3D {

  /**
   * Returns the current axis range.
   * 
   * @return The current axis range (never <code>null</code>). 
   */
  Range getRange();
  
  public void setRange(Range range);
  
  double translateToWorld(double value, double length);

  /**
   * Renders the axis along an arbitrary line.
   * 
   * @param g2
   * @param pt0
   * @param pt1
   * @param opposingPt
   * @param labels 
   */
  void render(Graphics2D g2, Point2D pt0, Point2D pt1, Point2D opposingPt, 
      boolean labels);

}
