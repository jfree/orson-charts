/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.axis;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.Font;
import java.util.List;
import com.orsoncharts.ChartElement;
import com.orsoncharts.Range;

/**
 * An interface that must be supported by axes for 3D plots.
 */
public interface Axis3D extends ChartElement {

    /**
     * Returns the font that is used to display the main axis label.
     * 
     * @return The font (never <code>null</code>). 
     */
    Font getLabelFont();
    
    /**
     * Sets the font for the axis label (the main label, not the tick labels)
     * and sends a {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param font  the font (<code>null</code> not permitted). 
     */
    void setLabelFont(Font font);
    
    /**
     * Returns the color used to draw the axis label.
     * 
     * @return The color (never <code>null</code>).
     * 
     * @since 1.2
     */
    Color getLabelColor();
    
    /**
     * Sets the color used to draw the axis label and sends an 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param color  the color (<code>null</code> not permitted). 
     * 
     * @since 1.2
     */
    void setLabelColor(Color color);
    
    /**
     * Returns the font that is used to display the tick labels.
     * 
     * @return The font (never <code>null</code>). 
     */
    Font getTickLabelFont();
    
    /**
     * Sets the font for the tick labels and sends a {@link Axis3DChangeEvent} 
     * to all registered listeners.
     * 
     * @param font  the font (<code>null</code> not permitted). 
     */
    void setTickLabelFont(Font font);
    
    /**
     * Returns the color used to draw the axis tick labels.
     * 
     * @return The color (never <code>null</code>). 
     * 
     * @since 1.2
     */
    Color getTickLabelColor();
    
    /**
     * Sets the color used to draw the axis tick labels and sends an 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param color  the color (<code>null</code> not permitted). 
     * 
     * @since 1.2
     */
    void setTickLabelColor(Color color);

    /**
     * Returns the axis range.
     * 
     * @return The axis range (never <code>null</code>). 
     */
    Range getRange();

    /**
     * Sets the axis range and sends an {@link Axis3DChangeEvent} to all
     * registered listeners.
     * 
     * @param range  the range (<code>null</code> not permitted). 
     */
    public void setRange(Range range);
  
    /**
     * Sets the axis range and sends an {@link Axis3DChangeEvent} to all
     * registered listeners.
     * 
     * @param min  the lower bound for the axis.
     * @param max  the upper bound for the axis.
     */
    public void setRange(double min, double max);
    
    /**
     * Translates a data value to a world coordinate.  Since we draw the charts
     * in a box that has one corner at <code>(0, 0, 0)</code>, we only need to 
     * know the length of the side of the box along which we are translating in 
     * order to do the calculation.
     * 
     * @param value  the data value.
     * @param length  the box side length.
     * 
     * @return The translated value. 
     */
    double translateToWorld(double value, double length);

    /**
     * Draws the axis along an arbitrary line (between <code>startPt</code> 
     * and <code>endPt</code>).  The opposing point is used as a reference
     * point to know on which side of the axis to draw the labels.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param startPt  the starting point (<code>null</code> not permitted).
     * @param endPt  the end point (<code>null</code> not permitted)
     * @param opposingPt  an opposing point (<code>null</code> not permitted).
     * @param labels  draw labels?
     * @param tickData  info about the ticks to draw (<code>null</code> not 
     *     permitted).
     */
    void draw(Graphics2D g2, Point2D startPt, Point2D endPt, Point2D opposingPt, 
            boolean labels, List<TickData> tickData);

    /**
     * Registers a listener so that it receives notification of changes to the
     * axis.
     * 
     * @param listener  the listener (<code>null</code> not permitted).
     */
    void addChangeListener(Axis3DChangeListener listener);
    
    /**
     * Deregisters a listener so that it no longer receives notification of
     * changes to the axis.
     * 
     * @param listener  the listener (<code>null</code> not permitted). 
     */
    void removeChangeListener(Axis3DChangeListener listener);

}
