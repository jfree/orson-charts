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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import com.orsoncharts.Range;
import com.orsoncharts.util.Anchor2D;
import com.orsoncharts.util.ArgChecks;

/**
 * A marker used to mark one value on an axis.
 * 
 * @since 1.2
 */
public class NumberMarker extends AbstractMarker implements ValueMarker {

    /** The data value to be marked. */
    private double value;
    
    /** The label for the marker (optional). */
    private String label;
    
    /** The font for the label. */
    private Font font;
    
    /** The color for the label. */
    private Color labelColor;
    
    /** The background color for the label (defaults to transparent). */
    private Color labelBackgroundColor;
    
    /** The anchor for the label. */
    private Anchor2D anchor;
    
    /** The stroke for the marker line. */
    private Stroke stroke;
    
    /** The paint for the marker line. */
    private Paint paint;
    
    /**
     * Creates a new marker.
     * 
     * @param value  the value. 
     */
    public NumberMarker(double value) {
        super();
        this.value = value;
        this.label = null;
        this.font = DEFAULT_MARKER_FONT;
        this.labelColor = DEFAULT_LABEL_COLOR;
        this.labelBackgroundColor = new Color(0, 0, 0, 0); // transparent
        this.stroke = DEFAULT_LINE_STROKE;
        this.paint = DEFAULT_MARKER_PAINT;
        this.anchor = Anchor2D.CENTER;
    }
    
    /**
     * Returns the value for the marker.
     * 
     * @return The value. 
     */
    public double getValue() {
        return this.value;
    }
    
    /**
     * Sets the value for the marker and sends a change event to all registered
     * listeners.
     * 
     * @param value  the value. 
     */
    public void setValue(double value) {
        this.value = value;
        fireChangeEvent();
    }
    
    /**
     * Returns the range for the marker (in this case, a single value range).
     * 
     * @return The range for the marker (never <code>null</code>). 
     */
    @Override
    public Range getRange() {
        return new Range(this.value, this.value);
    }

    /**
     * Returns the label for the marker (if this is <code>null</code> then no
     * label is displayed).
     * 
     * @return The label (possibly <code>null</code>). 
     */
    public String getLabel() {
        return this.label;
    }
    
    /**
     * Sets the label and sends a change event to all registered listeners.
     * 
     * @param label  the label (<code>null</code> permitted).
     */
    public void setLabel(String label) {
        this.label = label;
        fireChangeEvent();
    }
    
    /**
     * Returns the stroke for the marker line.
     * 
     * @return The stroke for the marker line (never <code>null</code>).
     */
    public Stroke getStroke() {
        return this.stroke;    
    }
    
    /**
     * Sets the stroke for the marker line and sends a change event to all
     * registered listeners.
     * 
     * @param stroke  the stroke (<code>null</code> not permitted). 
     */
    public void setStroke(Stroke stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.stroke = stroke;
        fireChangeEvent();
    }
    
    /**
     * Returns the paint for the marker line.
     * 
     * @return The paint for the marker line (never <code>null</code>). 
     */
    public Paint getPaint() {
        return this.paint;
    }
    
    /**
     * Sets the paint for the marker line and sends a change event to all 
     * registered listeners.
     * 
     * @param paint  the paint (<code>null</code> not permitted). 
     */
    public void setPaint(Paint paint) {
        ArgChecks.nullNotPermitted(paint, "paint");
        this.paint = paint;
        fireChangeEvent();
    }
    
    /**
     * Draws the marker.  This method is called by the library, you won't 
     * normally call it directly.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param markerData  transient marker data (<code>null</code> not 
     *     permitted).
     */
    @Override
    public void draw(Graphics2D g2, MarkerData markerData) {
        MarkerLine line = markerData.getValueLine();
        g2.setPaint(this.paint);
        g2.setStroke(this.stroke);
        Line2D l = new Line2D.Double(line.getStartPoint(), line.getEndPoint());
        g2.draw(l);
        
        // draw labels TODO
    }
   
}
