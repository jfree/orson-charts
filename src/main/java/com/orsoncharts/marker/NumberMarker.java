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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.orsoncharts.Range;
import com.orsoncharts.util.Anchor2D;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ObjectUtils;
import com.orsoncharts.util.SerialUtils;

/**
 * A marker used to mark one value on an axis.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 * 
 * @since 1.2
 */
@SuppressWarnings("serial")
public class NumberMarker extends AbstractMarker implements ValueMarker,
        Serializable {

    /** The data value to be marked. */
    private double value;
    
    /** The label for the marker (optional). */
    private String label;
    
    /** The font for the label. */
    private Font font;
    
    /** The color for the label. */
    private Color labelColor;
    
    /** The anchor for the label. */
    private Anchor2D labelAnchor;
    
    /** The stroke for the marker line. */
    private transient Stroke stroke;
    
    /** The color for the marker line. */
    private Color lineColor;
    
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
        this.stroke = DEFAULT_LINE_STROKE;
        this.lineColor = DEFAULT_LINE_COLOR;
        this.labelAnchor = Anchor2D.CENTER;
    }
    
    /**
     * Returns the value for the marker (the initial value comes from the 
     * constructor).
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
     * This method is used by the axis to filter out markers that do not touch 
     * the current axis range.
     * 
     * @return The range for the marker (never {@code null}). 
     */
    @Override
    public Range getRange() {
        return new Range(this.value, this.value);
    }

    /**
     * Returns the label for the marker (if this is {@code null} then no
     * label is displayed).  The default value is {@code null}.
     * 
     * @return The label (possibly {@code null}). 
     */
    public String getLabel() {
        return this.label;
    }
    
    /**
     * Sets the label and sends a change event to all registered listeners.
     * 
     * @param label  the label ({@code null} permitted).
     */
    public void setLabel(String label) {
        this.label = label;
        fireChangeEvent();
    }
    
    /**
     * Returns the font for the label.  The default value is 
     * {@link Marker#DEFAULT_MARKER_FONT}.
     * 
     * @return The font (never {@code null}). 
     */
    public Font getFont() {
        return this.font;
    }
    
    /**
     * Sets the font for the marker label and sends a change event to all 
     * registered listeners.
     * 
     * @param font  the font ({@code null} not permitted). 
     */
    public void setFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.font = font;
        fireChangeEvent();
    }
    
    /**
     * Returns the label color.  The default value is 
     * {@link Marker#DEFAULT_LABEL_COLOR}.
     * 
     * @return The label color (never {@code null}).
     */
    public Color getLabelColor() {
        return this.labelColor;
    }
    
    /**
     * Sets the label color and sends a change event to all registered
     * listeners.
     * 
     * @param color  the color ({@code null} not permitted). 
     */
    public void setLabelColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.labelColor = color;
        fireChangeEvent();
    }
    
    /**
     * Returns the anchor for the label.  The default value is 
     * {@link Anchor2D#CENTER}.
     * 
     * @return The anchor for the label. 
     */
    public Anchor2D getLabelAnchor() {
        return this.labelAnchor;
    }
    
    /**
     * Sets the anchor for the label and sends a change event to all registered
     * listeners.
     * 
     * @param anchor  the anchor ({@code null} not permitted). 
     */
    public void setLabelAnchor(Anchor2D anchor) {
        ArgChecks.nullNotPermitted(anchor, "anchor");
        this.labelAnchor = anchor;
        fireChangeEvent();
    }
     
    /**
     * Returns the stroke for the marker line.  The default value is
     * {@link Marker#DEFAULT_LINE_STROKE}.
     * 
     * @return The stroke for the marker line (never {@code null}).
     */
    public Stroke getLineStroke() {
        return this.stroke;    
    }
    
    /**
     * Sets the stroke for the marker line and sends a change event to all
     * registered listeners.
     * 
     * @param stroke  the stroke ({@code null} not permitted). 
     */
    public void setLineStroke(Stroke stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.stroke = stroke;
        fireChangeEvent();
    }
    
    /**
     * Returns the color for the marker line.  The default value is 
     * {@link Marker#DEFAULT_LINE_COLOR}.
     * 
     * @return The color for the marker line (never {@code null}). 
     */
    public Color getLineColor() {
        return this.lineColor;
    }
    
    /**
     * Sets the color for the marker line and sends a change event to all 
     * registered listeners.
     * 
     * @param color  the color ({@code null} not permitted). 
     */
    public void setLineColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.lineColor = color;
        fireChangeEvent();
    }
    
    /**
     * Draws the marker.  This method is called by the library, you won't 
     * normally call it directly.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param markerData  transient marker data ({@code null} not 
     *     permitted).
     */
    @Override
    public void draw(Graphics2D g2, MarkerData markerData, boolean reverse) {
        MarkerLine line = markerData.getValueLine();
        g2.setPaint(this.lineColor);
        g2.setStroke(this.stroke);
        Line2D l = new Line2D.Double(line.getStartPoint(), line.getEndPoint());
        g2.draw(l);
        Point2D labelPoint = markerData.getLabelPoint(); 
        if (labelPoint != null) {
            g2.setFont(this.font);
            g2.setColor(this.labelColor);
            drawMarkerLabel(g2, this.label, labelPoint.getX(), 
                    labelPoint.getY(), this.labelAnchor, l, reverse);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.value) 
                ^ (Double.doubleToLongBits(this.value) >>> 32));
        hash = 19 * hash + ObjectUtils.hashCode(this.label);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NumberMarker other = (NumberMarker) obj;
        if (Double.doubleToLongBits(this.value) 
                != Double.doubleToLongBits(other.value)) {
            return false;
        }
        if (!ObjectUtils.equals(this.label, other.label)) {
            return false;
        }
        if (!ObjectUtils.equals(this.font, other.font)) {
            return false;
        }
        if (!ObjectUtils.equals(this.labelColor, other.labelColor)) {
            return false;
        }
        if (!ObjectUtils.equals(this.labelAnchor, other.labelAnchor)) {
            return false;
        }
        if (!ObjectUtils.equals(this.stroke, other.stroke)) {
            return false;
        }
        if (!ObjectUtils.equals(this.lineColor, other.lineColor)) {
            return false;
        }
        return true;
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writeStroke(this.stroke, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.stroke = SerialUtils.readStroke(stream);
    }
   
}
