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

package com.orsoncharts.marker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import com.orsoncharts.ChartElement;

/**
 * A base interface for all markers.
 * 
 * @since 1.2
 */
public interface Marker extends ChartElement {

    /** The default line stroke for markers. */
    public static final Stroke DEFAULT_LINE_STROKE = new BasicStroke(0f, 
            BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

    /** The default line color for markers. */
    public static final Color DEFAULT_LINE_COLOR = Color.DARK_GRAY;

    /** The default fill color for markers. */
    public static final Color DEFAULT_FILL_COLOR = new Color(128, 128, 192, 64);

    /** The default font for marker labels. */
    public static final Font DEFAULT_MARKER_FONT = new Font(Font.DIALOG, 
            Font.PLAIN, 10);
    
    /** The default color for the marker labels. */
    public static final Color DEFAULT_LABEL_COLOR = Color.BLACK;
    
    /** 
     * Draws the marker based on the {@code markerData} which has been 
     * passed to the 3D engine to generate the required 2D projection points.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param markerData  transient data for the marker ({@code null} not 
     *     permitted).
     * @param reverse  a flag to indicate reverse orientation.
     */
    void draw(Graphics2D g2, MarkerData markerData, boolean reverse);
    
    /**
     * Registers a listener to receive notification of changes to the marker.
     * 
     * @param listener  the listener ({@code null} not permitted). 
     */
    void addChangeListener(MarkerChangeListener listener);
    
    /**
     * Deregisters a listener so that it no longer receives notification of 
     * changes to the marker.
     * 
     * @param listener  the listener ({@code null} not permitted). 
     */
    void removeChangeListener(MarkerChangeListener listener);
    
}
