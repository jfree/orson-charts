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

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import javax.swing.event.EventListenerList;
import com.orsoncharts.Chart3DChangeListener;
import com.orsoncharts.ChartElementVisitor;
import com.orsoncharts.graphics3d.Utils2D;
import com.orsoncharts.util.Anchor2D;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.RefPt2D;
import com.orsoncharts.util.TextAnchor;
import com.orsoncharts.util.TextUtils;

/**
 * A base class for implementing markers (includes the event notification 
 * mechanism).
 * 
 * @since 1.2
 */
public abstract class AbstractMarker implements Marker {
    
    /** Storage for registered change listeners. */
    private transient EventListenerList listenerList;
    
    /**
     * Default constructor.
     */
    AbstractMarker() {
        this.listenerList = new EventListenerList();
    }

    /**
     * Draws a marker label.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param label  the label.
     * @param x  the x-coordinate for the anchor point.
     * @param y  the y-cpordinate for the anchor point.
     * @param anchor  the label anchor ({@code null} not permitted).
     * @param refLine  a reference line that is used to determine the rotation 
     *     angle for the label ({@code null} not permitted).
     * @param reverse  a flag to indicate reverse orientation.
     */
    protected void drawMarkerLabel(Graphics2D g2, String label, 
            double x, double y, Anchor2D anchor, Line2D refLine, 
            boolean reverse) {
        double angle = Utils2D.calculateTheta(refLine);
        boolean vflip = false;
        if (angle > Math.PI / 2) {
            angle -= Math.PI;
            vflip = true;
        }
        if (angle < -Math.PI / 2) {
            angle += Math.PI;
            vflip = true;
        }
        if (reverse) {
            vflip = !vflip;
        }
        double lineLength = Utils2D.length(refLine);
        FontMetrics fm = g2.getFontMetrics();
        Rectangle2D bounds = fm.getStringBounds(label, g2);
        if (bounds.getWidth() < lineLength) {
            TextAnchor textAnchor = deriveTextAnchorForLine(anchor.getRefPt(), 
                    !vflip);
            TextUtils.drawRotatedString(label, g2, 
                    (float) x, (float) y, textAnchor, angle, textAnchor);
        }
    }
    
    /**
     * Draws a marker label.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param label  the label.
     * @param x  the x-coordinate for the anchor point.
     * @param y  the y-cpordinate for the anchor point.
     * @param anchor  the label anchor ({@code null} not permitted).
     * @param refLine1  a reference line that is used to determine the rotation 
     *     angle for the label ({@code null} not permitted).
     * @param refLine2  a reference line that is used to determine the rotation 
     *     angle for the label ({@code null} not permitted).
     * @param reverse  a flag to indicate reverse orientation.
     */
    protected void drawMarkerLabel(Graphics2D g2, String label, 
            double x, double y, Anchor2D anchor, Line2D refLine1, 
            Line2D refLine2, boolean reverse) {
        double angle;
        if (anchor.getRefPt().isTop()) {
            angle = Utils2D.calculateTheta(refLine2);
        } else if (anchor.getRefPt().isBottom()) {
            angle = Utils2D.calculateTheta(refLine1);
        } else {
            angle = (Utils2D.calculateTheta(refLine1) 
                    + Utils2D.calculateTheta(refLine2)) / 2.0;
        }
        boolean vflip = false;
        if (angle > Math.PI / 2) {
            angle -= Math.PI;
            vflip = true;
        }
        if (angle < -Math.PI / 2) {
            angle += Math.PI;
            vflip = true;
        }
        if (reverse) {
            vflip = !vflip;
        }
        double lineLength1 = Utils2D.length(refLine1);
        double lineLength2 = Utils2D.length(refLine2);
        Rectangle2D bounds = g2.getFontMetrics().getStringBounds(label, g2);
        if (bounds.getWidth() < Math.min(lineLength1, lineLength2)) {
            TextAnchor textAnchor = deriveTextAnchor(anchor.getRefPt(), !vflip);
            TextUtils.drawRotatedString(label, g2, (float) x, (float) y, 
                textAnchor, angle, textAnchor);
        }        
    }
 
    /**
     * Receives a visitor.
     * 
     * @param visitor  the visitor.
     * 
     * @since 1.2
     */
    @Override
    public void receive(ChartElementVisitor visitor) {
        visitor.visit(this);
    }
 
    /**
     * Registers a listener to receive notification of changes to the marker.
     * 
     * @param listener  the listener ({@code null} not permitted). 
     */
    @Override
    public void addChangeListener(MarkerChangeListener listener) {
        this.listenerList.add(MarkerChangeListener.class, listener);
    }
    
    /**
     * Deregisters a listener so that it no longer receives notification of 
     * changes to the marker.
     * 
     * @param listener  the listener ({@code null} not permitted). 
     */
    @Override
    public void removeChangeListener(MarkerChangeListener listener) {
        this.listenerList.remove(MarkerChangeListener.class, listener);        
    }
    
    /**
     * Sends a {@link MarkerChangeEvent} to all registered listeners.
     */
    protected void fireChangeEvent() {
        Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == Chart3DChangeListener.class) { 
                ((MarkerChangeListener) listeners[i + 1]).markerChanged(
                        new MarkerChangeEvent(this, this));
            }
        }
    }
    
    /**
     * A utility method that returns a suitable text anchor for a given
     * reference point. This is used for range marker label positioning.
     * 
     * @param refPt  the reference point ({@code null} not permitted).
     * @param vflip  is the text flipped vertically?
     * 
     * @return A text anchor (never {@code null}). 
     */
    protected static TextAnchor deriveTextAnchor(RefPt2D refPt, boolean vflip) {
        ArgChecks.nullNotPermitted(refPt, "refPt");
        if (refPt.equals(RefPt2D.TOP_LEFT)) {
            return vflip ? TextAnchor.TOP_LEFT : TextAnchor.BOTTOM_RIGHT;
        } else if (refPt.equals(RefPt2D.TOP_CENTER)) {
            return vflip ? TextAnchor.TOP_CENTER : TextAnchor.BOTTOM_CENTER;
        } else if (refPt.equals(RefPt2D.TOP_RIGHT)) {
            return vflip ? TextAnchor.TOP_RIGHT :TextAnchor.BOTTOM_LEFT;
        } if (refPt.equals(RefPt2D.CENTER_LEFT)) {
            return vflip ? TextAnchor.CENTER_LEFT : TextAnchor.CENTER_RIGHT;
        } else if (refPt.equals(RefPt2D.CENTER)) {
            return TextAnchor.CENTER;
        } else if (refPt.equals(RefPt2D.CENTER_RIGHT)) {
            return vflip ? TextAnchor.CENTER_RIGHT : TextAnchor.CENTER_LEFT;
        } else if (refPt.equals(RefPt2D.BOTTOM_LEFT)) {
            return vflip ? TextAnchor.BOTTOM_LEFT : TextAnchor.TOP_RIGHT;
        } else if (refPt.equals(RefPt2D.BOTTOM_CENTER)) {
            return vflip ? TextAnchor.BOTTOM_CENTER : TextAnchor.TOP_CENTER;
        } else if (refPt.equals(RefPt2D.BOTTOM_RIGHT)) {
            return vflip ? TextAnchor.BOTTOM_RIGHT : TextAnchor.TOP_LEFT;
        }
        throw new RuntimeException("Unknown refPt " + refPt);
    }

    /**
     * A utility method that returns a suitable text anchor for a given
     * reference point relative to a line (rather than a rectangle which is
     * the normal case).  This is used for value marker label positioning.
     * 
     * @param refPt  the reference point ({@code null} not permitted).
     * @param vflip  is the text flipped vertically?
     * 
     * @return A text anchor (never {@code null}). 
     */
    protected static TextAnchor deriveTextAnchorForLine(RefPt2D refPt, 
            boolean vflip) {
        if (refPt.equals(RefPt2D.TOP_LEFT)) {
            return vflip ? TextAnchor.BOTTOM_LEFT : TextAnchor.TOP_RIGHT;
        } else if (refPt.equals(RefPt2D.TOP_CENTER)) {
            return vflip ? TextAnchor.BOTTOM_CENTER : TextAnchor.TOP_CENTER;
        } else if (refPt.equals(RefPt2D.TOP_RIGHT)) {
            return vflip ? TextAnchor.BOTTOM_RIGHT :TextAnchor.TOP_LEFT;
        } if (refPt.equals(RefPt2D.CENTER_LEFT)) {
            return vflip ? TextAnchor.CENTER_LEFT : TextAnchor.CENTER_RIGHT;
        } else if (refPt.equals(RefPt2D.CENTER)) {
            return TextAnchor.CENTER;
        } else if (refPt.equals(RefPt2D.CENTER_RIGHT)) {
            return vflip ? TextAnchor.CENTER_RIGHT : TextAnchor.CENTER_LEFT;
        } else if (refPt.equals(RefPt2D.BOTTOM_LEFT)) {
            return vflip ? TextAnchor.TOP_LEFT : TextAnchor.BOTTOM_RIGHT;
        } else if (refPt.equals(RefPt2D.BOTTOM_CENTER)) {
            return vflip ? TextAnchor.TOP_CENTER : TextAnchor.BOTTOM_CENTER;
        } else if (refPt.equals(RefPt2D.BOTTOM_RIGHT)) {
            return vflip ? TextAnchor.TOP_RIGHT : TextAnchor.BOTTOM_LEFT;
        }
        throw new RuntimeException("Unknown refPt " + refPt);
    }

}
