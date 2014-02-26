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

package com.orsoncharts.marker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;

/**
 * A base interface for all markers.
 * 
 * @since 1.2
 */
public interface Marker {

    /** The default line stroke for markers. */
    public static final Stroke DEFAULT_LINE_STROKE = new BasicStroke(2.0f);

    /** The default line color for markers. */
    public static final Paint DEFAULT_MARKER_PAINT = Color.DARK_GRAY;

    /** The default fill paint for markers. */
    public static final Paint DEFAULT_FILL_PAINT = new Color(128, 128, 192, 64);

    /** The default font for marker labels. */
    public static final Font DEFAULT_MARKER_FONT = new Font(Font.DIALOG, 
            Font.PLAIN, 10);
    
    /** The default color for the marker labels. */
    public static final Color DEFAULT_LABEL_COLOR = Color.BLACK;
    
    /** 
     * Draws the marker based on the <code>markerData</code>> which has been 
     * passed to the 3D engine to generate the required 2D projection points.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param markerData  transient data for the marker (<code>null</code> not 
     *     permitted).
     */
    void draw(Graphics2D g2, MarkerData markerData);
    
    /**
     * Registers a listener to receive notification of changes to the marker.
     * 
     * @param listener  the listener (<code>null</code> not permitted). 
     */
    void addChangeListener(MarkerChangeListener listener);
    
    /**
     * Deregisters a listener so that it no longer receives notification of 
     * changes to the marker.
     * 
     * @param listener  the listener (<code>null</code> not permitted). 
     */
    void removeChangeListener(MarkerChangeListener listener);
    
}
