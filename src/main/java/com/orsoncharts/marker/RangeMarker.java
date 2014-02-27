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
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import com.orsoncharts.Range;
import com.orsoncharts.graphics3d.Utils2D;
import com.orsoncharts.util.Anchor2D;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ObjectUtils;
import com.orsoncharts.util.TextAnchor;
import com.orsoncharts.util.TextUtils;

/**
 * A marker that marks a range of values on an axis.
 * 
 * @since 1.2
 */
public class RangeMarker extends AbstractMarker implements ValueMarker, 
        MarkerChangeListener, Serializable {
    
    /** The start of the range. */
    private NumberMarker start;
    
    /** The end of the range. */
    private NumberMarker end;

    /** The label for the marker (optional). */
    private String label;
    
    /** The font for the label. */
    private Font font;
    
    /** The color for the label. */
    private Color labelColor;
    
    /** The anchor for the label. */
    private Anchor2D labelAnchor;

    /** The color used to fill the band representing the marker range. */
    Color fillColor;
    
    /**
     * Creates a new range marker for the given bounds.
     * 
     * @param lowerBound  the lower bound.
     * @param upperBound  the upper bound.
     */
    public RangeMarker(double lowerBound, double upperBound) {
        super();
        this.start = new NumberMarker(lowerBound);
        // TODO: add a listener
        this.end = new NumberMarker(upperBound);
        // TODO: add a listener
                this.label = null;
        this.font = DEFAULT_MARKER_FONT;
        this.labelColor = DEFAULT_LABEL_COLOR;
        this.labelAnchor = Anchor2D.CENTER;
        this.fillColor = DEFAULT_FILL_COLOR;
    }
    
    /**
     * Returns the starting point for the range marker.
     * 
     * @return The starting point.
     */
    public NumberMarker getStart() {
        return this.start;
    }
    
    /**
     * Returns the ending point for the range marker.
     * 
     * @return The ending point. 
     */
    public NumberMarker getEnd() {
        return this.end;
    }
    
    /**
     * Returns the range of values for the marker.
     * 
     * @return The range of values for the marker.
     */
    @Override
    public Range getRange() {
        return new Range(this.start.getValue(), this.end.getValue());
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
     * Sets the label and sends a change event to all registered listeners.  If
     * the label is set to <code>null</code> then no label is displayed for 
     * the marker.
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
     * Returns the color used to fill the band representing the range for 
     * the marker.  The default value is {@link Marker#DEFAULT_FILL_COLOR}.
     * 
     * @return The fill color (never <code>null</code>). 
     */
    public Color getFillColor() {
        return this.fillColor;
    }
    
    /**
     * Sets the color used to fill the band representing the range for the
     * marker and sends a change event to all registered listeners.
     * 
     * @param color  the color (<code>null</code> not permitted).
     */
    public void setFillColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.fillColor = color;
        fireChangeEvent();
    }
    
    @Override
    public void draw(Graphics2D g2, MarkerData markerData, boolean reverse) {
        MarkerLine startLine = markerData.getStartLine();
        Line2D l1 = new Line2D.Double(startLine.getStartPoint(), 
                startLine.getEndPoint());
        MarkerLine endLine = markerData.getEndLine();
        Line2D l2 = new Line2D.Double(endLine.getStartPoint(), 
                endLine.getEndPoint());

        Path2D path = new Path2D.Double();
        path.moveTo(l1.getX1(), l1.getY1());
        path.lineTo(l1.getX2(), l1.getY2());
        path.lineTo(l2.getX2(), l2.getY2());
        path.lineTo(l2.getX1(), l2.getY1());
        path.closePath();
        g2.setPaint(this.fillColor);
        g2.fill(path);

        if (!startLine.isPegged()) {
            g2.setPaint(this.start.getLineColor());
            g2.setStroke(this.start.getLineStroke());
            g2.draw(l1);
        }
        if (!endLine.isPegged()) {
            g2.setPaint(this.end.getLineColor());
            g2.setStroke(this.end.getLineStroke());
            g2.draw(l2);
        }
        
        double angle;
        if (this.labelAnchor.getRefPt().isTop()) {
            angle = Utils2D.calculateTheta(l2);
        } else if (this.labelAnchor.getRefPt().isBottom()) {
            angle = Utils2D.calculateTheta(l1);
        } else {
            angle = (Utils2D.calculateTheta(l1) + Utils2D.calculateTheta(l2)) 
                    / 2.0;
        }
        boolean vflip = false;
        if (angle > Math.PI / 2) {
            //angle -= Math.PI;
            //vflip = true;
        }
        if (angle < -Math.PI / 2) {
            //angle += Math.PI;
            //vflip = true;
        }
        if (reverse) {
            vflip = !vflip;
        }
        Point2D labelPoint = markerData.getLabelPoint(); 
        if (labelPoint != null) {
            g2.setFont(this.font);
            g2.setColor(this.labelColor);
            
            double lineLength1 = Utils2D.length(l1);
            double lineLength2 = Utils2D.length(l2);
            Rectangle2D bounds = g2.getFontMetrics().getStringBounds(
                    this.label, g2);
            if (bounds.getWidth() < Math.min(lineLength1, lineLength2)) {
                TextAnchor textAnchor = deriveTextAnchor(
                       this.labelAnchor.getRefPt(), !vflip);
                TextUtils.drawRotatedString(this.label, g2, 
                    (float) labelPoint.getX(), (float) labelPoint.getY(), 
                    textAnchor, angle, textAnchor);
            }
        }

    }

    @Override
    public void markerChanged(MarkerChangeEvent event) {
        fireChangeEvent();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + ObjectUtils.hashCode(this.start);
        hash = 97 * hash + ObjectUtils.hashCode(this.end);
        hash = 97 * hash + ObjectUtils.hashCode(this.label);
        return hash;
    }

    /**
     * Tests this marker for equality with an arbitrary object.
     * 
     * @param obj  the object to test against (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RangeMarker other = (RangeMarker) obj;
        if (!ObjectUtils.equals(this.start, other.start)) {
            return false;
        }
        if (!ObjectUtils.equals(this.end, other.end)) {
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
        if (!ObjectUtils.equals(this.fillColor, other.fillColor)) {
            return false;
        }
        return true;
    }
    
}
