/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.axis;

import com.orsoncharts.Range;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 * An interface that must be supported by axes for 3D plots.
 */
public interface Axis3D {

    /**
     * Returns the current axis range.
     * 
     * @return The current axis range (never <code>null</code>). 
     */
    Range getRange();

    /**
     * Sets the axis range.
     * 
     * @param range  the range (<code>null</code> not permitted). 
     */
    public void setRange(Range range);
  
    /**
     * Translates a data value to a world coordinate.  Since we draw the charts
     * in a box that has one corner at (0, 0, 0), we only need to know the
     * length of the side of the box along which we are translating in order
     * to do the calculation.
     * 
     * @param value  the data value.
     * @param length  the box side length.
     * 
     * @return The translated value. 
     */
    double translateToWorld(double value, double length);

    /**
     * Renders the axis along an arbitrary line (between <code>startPt</code> 
     * and <code>endPt</code>).  The opposing point is used as a reference
     * point to know on which side of the axis to draw the labels.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param startPt  the starting point (<code>null</code> not permitted).
     * @param endPt  the end point (<code>null</code> not permitted)
     * @param opposingPt  an opposing point (<code>null</code> not permitted).
     * @param labels  draw labels?
     */
    void render(Graphics2D g2, Point2D startPt, Point2D endPt, Point2D opposingPt, 
            boolean labels);

    void addChangeListener(Axis3DChangeListener listener);
    
    void removeChangeListener(Axis3DChangeListener listener);

}
