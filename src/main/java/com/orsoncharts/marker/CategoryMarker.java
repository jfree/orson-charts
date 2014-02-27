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
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import com.orsoncharts.util.SerialUtils;
import com.orsoncharts.util.Anchor2D;
import com.orsoncharts.util.ArgChecks;

/**
 * A marker for a category on a CategoryAxis3D.  This marker could be used to
 * highlight one selected category.
 * 
 * @since 1.2
 */
public class CategoryMarker extends AbstractMarker implements Serializable {

    /** The category to mark. */
    Comparable category;

    /** 
     * The marker type (used to indicate whether the marker is represented by
     * a line or a band). 
     */
    CategoryMarkerType type;
    
    /** The label for the marker (optional). */
    private String label;
    
    /** The font for the label. */
    private Font font;
    
    /** The color for the label. */
    private Color labelColor;
    
    /** The anchor for the label. */
    private Anchor2D labelAnchor;
    
    /** The stroke for the marker line(s). */
    private transient Stroke lineStroke;
    
    /** The color for the marker line. */
    private Color lineColor;
    
    /** The fill color used when drawing a band for the marker. */
    private Color fillColor;
    
    /**
     * Creates a marker for the specified category. 
     */
    public CategoryMarker(Comparable category) {
        super();
        ArgChecks.nullNotPermitted(category, "category");
        this.category = category;
        this.type = CategoryMarkerType.BAND;
        this.font = Marker.DEFAULT_MARKER_FONT;
        this.labelColor = Marker.DEFAULT_LABEL_COLOR;
        this.labelAnchor = Anchor2D.CENTER;
        this.lineStroke = DEFAULT_LINE_STROKE;
        this.lineColor = DEFAULT_LINE_COLOR;
        this.fillColor = DEFAULT_FILL_COLOR;
    }
    
    /**
     * Returns the category.
     * 
     * @return The category (never <code>null</code>). 
     */
    public Comparable getCategory() {
        return this.category;
    }
    
    /**
     * Sets the category for the marker and sends a change event to all 
     * registered listeners.
     * 
     * @param category  the new category (<code>null</code> not permitted). 
     */
    public void setCategory(Comparable category) {
        ArgChecks.nullNotPermitted(category, "category");
        this.category = category;
        fireChangeEvent();
    }

    /**
     * Returns the marker type which determines whether the marker is drawn
     * as a band (the default) or a line.
     * 
     * @return The type (never <code>null</code>). 
     */
    public CategoryMarkerType getType() {
        return this.type;
    }
    
    /**
     * Sets the marker type and sends a change event to all registered 
     * listeners. 
     * 
     * @param type  the type (<code>null</code> not permitted). 
     */
    public void setType(CategoryMarkerType type) {
        ArgChecks.nullNotPermitted(type, "type");
        this.type = type;
        fireChangeEvent();
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
     * Returns the font for the label.  The default value is 
     * {@link Marker#DEFAULT_MARKER_FONT}.
     * 
     * @return The font (never <code>null</code>). 
     */
    public Font getFont() {
        return this.font;
    }
    
    /**
     * Sets the font for the marker label and sends a change event to all 
     * registered listeners.
     * 
     * @param font  the font (<code>null</code> not permitted). 
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
     * @return The label color (never <code>null</code>).
     */
    public Color getLabelColor() {
        return this.labelColor;
    }
    
    /**
     * Sets the label color and sends a change event to all registered
     * listeners.
     * 
     * @param color  the color (<code>null</code> not permitted). 
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
     * @param anchor  the anchor (<code>null</code> not permitted). 
     */
    public void setLabelAnchor(Anchor2D anchor) {
        ArgChecks.nullNotPermitted(anchor, "anchor");
        this.labelAnchor = anchor;
        fireChangeEvent();
    }

    /**
     * Returns the line color for the marker.
     * 
     * @return The line color (never <code>null</code>). 
     */
    public Color getLineColor() {
        return this.lineColor;
    }
    
    /**
     * Sets the line color for the marker and sends a change event to all 
     * registered listeners.
     * 
     * @param color  the color (<code>null</code> not permitted).
     */
    public void setLineColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.lineColor = color;
        fireChangeEvent();
    }
    
    /**
     * Returns the line stroke.  The default value is 
     * {@link Marker#DEFAULT_LINE_STROKE}.
     * 
     * @return The line stroke (never <code>null</code>).
     */
    public Stroke getLineStroke() {
        return this.lineStroke;
    }
    
    /**
     * Sets the line stroke and sends a change event to all registered 
     * listeners.
     * 
     * @param stroke  the stroke (<code>null</code> not permitted). 
     */
    public void setLineStroke(Stroke stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.lineStroke = stroke;
        fireChangeEvent();
    }
    
    /**
     * Returns the color used to fill the marker band.
     * 
     * @return The color (never <code>null</code>). 
     */
    public Color getFillColor() {
        return this.fillColor;
    }
    
    /**
     * Sets the color used to fill the marker band and sends a change event
     * to all registered listeners.
     * 
     * @param color  the color (<code>null</code> not permitted). 
     */
    public void setFillColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.fillColor = color;
        fireChangeEvent();
    }
    
    /**
     * Handles drawing of the marker.  This method is called by the library,
     * you won't normally call it directly.
     * 
     * @param g2  the graphics device (<code>null</code> not permitted).
     * @param markerData   the marker data (<code>null</code> not permitted).
     * @param reverse  a flag to indicate reverse orientation.
     */
    @Override
    public void draw(Graphics2D g2, MarkerData markerData, boolean reverse) {
        if (markerData.getType().equals(MarkerDataType.VALUE)) {
            MarkerLine ml = markerData.getValueLine();
            g2.setPaint(this.lineColor);
            g2.setStroke(this.lineStroke);
            Line2D l = new Line2D.Double(ml.getStartPoint(), ml.getEndPoint());
            g2.draw(l);
            Point2D labelPoint = markerData.getLabelPoint(); 
            if (labelPoint != null) {
                g2.setFont(this.font);
                g2.setColor(this.labelColor);
                drawMarkerLabel(g2, this.label, labelPoint.getX(), 
                        labelPoint.getY(), this.labelAnchor, l, reverse);
            }
        } else if (markerData.getType().equals(MarkerDataType.RANGE)) {
            MarkerLine sl = markerData.getStartLine();
            Line2D l1 = new Line2D.Double(sl.getStartPoint(), sl.getEndPoint());
            MarkerLine el = markerData.getEndLine();
            Line2D l2 = new Line2D.Double(el.getStartPoint(), el.getEndPoint());

            Path2D path = new Path2D.Double();
            path.moveTo(l1.getX1(), l1.getY1());
            path.lineTo(l1.getX2(), l1.getY2());
            path.lineTo(l2.getX2(), l2.getY2());
            path.lineTo(l2.getX1(), l2.getY1());
            path.closePath();
            g2.setPaint(this.fillColor);
            g2.fill(path);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.category);
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
        final CategoryMarker other = (CategoryMarker) obj;
        if (!Objects.equals(this.category, other.category)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (!Objects.equals(this.label, other.label)) {
            return false;
        }
        if (!Objects.equals(this.font, other.font)) {
            return false;
        }
        if (!Objects.equals(this.labelColor, other.labelColor)) {
            return false;
        }
        if (!Objects.equals(this.labelAnchor, other.labelAnchor)) {
            return false;
        }
        if (!Objects.equals(this.lineStroke, other.lineStroke)) {
            return false;
        }
        if (!Objects.equals(this.lineColor, other.lineColor)) {
            return false;
        }
        if (!Objects.equals(this.fillColor, other.fillColor)) {
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
        SerialUtils.writeStroke(this.lineStroke, stream);
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
        this.lineStroke = SerialUtils.readStroke(stream);
    }
   
}
