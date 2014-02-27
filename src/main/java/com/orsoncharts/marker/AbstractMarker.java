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
     * Receives a visitor.
     * 
     * @param visitor 
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
     * @param listener  the listener (<code>null</code> not permitted). 
     */
    @Override
    public void addChangeListener(MarkerChangeListener listener) {
        this.listenerList.add(MarkerChangeListener.class, listener);
    }
    
    /**
     * Deregisters a listener so that it no longer receives notification of 
     * changes to the marker.
     * 
     * @param listener  the listener (<code>null</code> not permitted). 
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
     * @param refPt  the reference point (<code>null</code> not permitted).
     * @param vflip  is the text flipped vertically?
     * 
     * @return A text anchor (never <code>null</code>). 
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
     * @param refPt  the reference point (<code>null</code> not permitted).
     * @param vflip  is the text flipped vertically?
     * 
     * @return A text anchor (never <code>null</code>). 
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
