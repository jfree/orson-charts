/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
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
import com.orsoncharts.graphics3d.RenderingInfo;

/**
 * An interface that must be supported by axes for 3D plots.
 */
public interface Axis3D extends ChartElement {

    /**
     * Returns the flag that determines whether or not the axis is drawn 
     * on the chart.
     * 
     * @return A boolean.
     * 
     * @see #setVisible(boolean) 
     */
    boolean isVisible();
    
    /**
     * Sets the flag that determines whether or not the axis is drawn on the
     * chart and sends an {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param visible  the flag.
     * 
     * @see #isVisible() 
     */
    void setVisible(boolean visible);
    
    /**
     * Returns the font that is used to display the main axis label.
     * 
     * @return The font (never {@code null}). 
     */
    Font getLabelFont();
    
    /**
     * Sets the font for the axis label (the main label, not the tick labels)
     * and sends an {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param font  the font ({@code null} not permitted). 
     */
    void setLabelFont(Font font);
    
    /**
     * Returns the color used to draw the axis label.
     * 
     * @return The color (never {@code null}).
     * 
     * @since 1.2
     */
    Color getLabelColor();
    
    /**
     * Sets the color used to draw the axis label and sends an 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param color  the color ({@code null} not permitted). 
     * 
     * @since 1.2
     */
    void setLabelColor(Color color);
    
    /**
     * Returns the font that is used to display the tick labels.
     * 
     * @return The font (never {@code null}). 
     */
    Font getTickLabelFont();
    
    /**
     * Sets the font for the tick labels and sends an {@link Axis3DChangeEvent} 
     * to all registered listeners.
     * 
     * @param font  the font ({@code null} not permitted). 
     */
    void setTickLabelFont(Font font);
    
    /**
     * Returns the color used to draw the axis tick labels.
     * 
     * @return The color (never {@code null}). 
     * 
     * @since 1.2
     */
    Color getTickLabelColor();
    
    /**
     * Sets the color used to draw the axis tick labels and sends an 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param color  the color ({@code null} not permitted). 
     * 
     * @since 1.2
     */
    void setTickLabelColor(Color color);

    /**
     * Returns the axis range (the minimum and maximum values displayed on 
     * the axis).  Note that even categorical axes will have a range, although
     * since numerical values are not displayed the range is often set to
     * {@code (0.0, 1.0)} for convenience.
     * 
     * @return The axis range (never {@code null}). 
     */
    Range getRange();

    /**
     * Sets the axis range and sends an {@link Axis3DChangeEvent} to all
     * registered listeners.
     * 
     * @param range  the range ({@code null} not permitted). 
     */
    void setRange(Range range);
  
    /**
     * Sets the axis range and sends an {@link Axis3DChangeEvent} to all
     * registered listeners.
     * 
     * @param min  the lower bound for the axis.
     * @param max  the upper bound for the axis.
     */
    void setRange(double min, double max);
    
    /**
     * Returns the flag that determines whether or not the order of values on 
     * the axis is inverted.  The default value is {@code false}.
     * 
     * @return A boolean.
     * 
     * @since 1.5
     */
    boolean isInverted();
    
    /**
     * Sets the flag that determines whether or not the order of values on the
     * axis is inverted, and sends an {@link Axis3DChangeEvent} to all 
     * registered listeners.
     * 
     * @param inverted  the new flag value.
     * 
     * @since 1.5
     */
    void setInverted(boolean inverted);
    
    /**
     * Translates a data value to a world coordinate.  Since we draw the charts
     * in a box that has one corner at {@code (0, 0, 0)}, we only need to 
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
     * Draws the axis along an arbitrary line (between {@code startPt} 
     * and {@code endPt}).  The opposing point is used as a reference
     * point to know on which side of the axis to draw the labels.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param startPt  the starting point ({@code null} not permitted).
     * @param endPt  the end point ({@code null} not permitted)
     * @param opposingPt  an opposing point ({@code null} not permitted).
     * @param tickData  info about the ticks to draw ({@code null} not 
     *     permitted).
     * @param info  an object to be populated with rendering info 
     *     ({@code null} permitted).
     * @param hinting  a flag that controls whether or not element hinting 
     *     should be performed.
     * 
     * @since 1.3
     */
    void draw(Graphics2D g2, Point2D startPt, Point2D endPt, Point2D opposingPt, 
            List<TickData> tickData, RenderingInfo info, boolean hinting);

    /**
     * Registers a listener so that it receives notification of changes to the
     * axis.
     * 
     * @param listener  the listener ({@code null} not permitted).
     */
    void addChangeListener(Axis3DChangeListener listener);
    
    /**
     * Deregisters a listener so that it no longer receives notification of
     * changes to the axis.
     * 
     * @param listener  the listener ({@code null} not permitted). 
     */
    void removeChangeListener(Axis3DChangeListener listener);

}
