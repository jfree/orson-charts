/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of these source files is prohibited.
 * 
 */

package com.orsoncharts.table;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ObjectUtils;
import com.orsoncharts.util.RefPt2D;

/**
 * A base class that can be used to implement a {@link TableElement}.
 */
public abstract class AbstractTableElement {
    
    /** The reference point used to align the element when rendering. */
    private RefPt2D refPt;
    
    /** The insets. */
    private Insets insets;
    
    /** The foreground paint. */
    private Paint foregroundPaint;
    
    /** The background paint (this can be <code>null</code>). */
    private Paint backgroundPaint;
    
    /**
     * Creates a new instance.
     */
    public AbstractTableElement() {
        this.refPt = RefPt2D.CENTER;
        this.insets = new Insets(2, 2, 2, 2);
        this.foregroundPaint = Color.BLACK;
        this.backgroundPaint = new Color(255, 255, 255, 127);
    }

    /**
     * Returns the anchor point used to align the element with the bounding
     * rectangle within which it is drawn.  The default value is 
     * {@link RefPt2D#CENTER}.
     * 
     * @return The anchor point (never <code>null</code>). 
     * 
     * @since 1.1
     */
    public RefPt2D getRefPoint() {
        return this.refPt;
    }
    
    /**
     * Sets the reference point.
     * 
     * @param refPt  the reference point (<code>null</code> not permitted).
     * 
     * @since 1.1
     */
    public void setRefPoint(RefPt2D refPt) {
        ArgChecks.nullNotPermitted(refPt, "refPt");
        this.refPt = refPt;
    }

    /**
     * Returns the insets.  The default value is 
     * <code>Insets(2, 2, 2, 2)</code>.
     * 
     * @return The insets (never <code>null</code>).
     */
    public Insets getInsets() {
        return this.insets;
    }
    
    /**
     * Sets the insets.
     * 
     * @param insets  the insets (<code>null</code> not permitted). 
     */
    public void setInsets(Insets insets) {
        ArgChecks.nullNotPermitted(insets, "insets");
        this.insets = insets;
    }
    
    /**
     * Returns the foreground paint.  The default value is <code>BLACK</code>.
     * 
     * @return The foreground paint (never <code>null</code>). 
     */
    public Paint getForegroundPaint() {
        return this.foregroundPaint;
    }

    /**
     * Sets the foreground paint.
     * 
     * @param paint  the paint (<code>null</code> not permitted). 
     */
    public void setForegroundPaint(Paint paint) {
        ArgChecks.nullNotPermitted(paint, "paint");
        this.foregroundPaint = paint;
    }
    
    /**
     * Returns the background paint.  The default value is <code>WHITE</code>.
     * 
     * @return The background paint (never <code>null</code>). 
     */
    public Paint getBackgroundPaint() {
        return this.backgroundPaint;
    }

    /**
     * Sets the background paint.
     * 
     * @param paint  the paint (<code>null</code> not permitted). 
     */
    public void setBackgroundPaint(Paint paint) {
        ArgChecks.nullNotPermitted(paint, "paint");
        this.backgroundPaint = paint;
    }
    
    /**
     * Returns the preferred size of the element (including insets).
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * 
     * @return The preferred size. 
     */
    public Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds) {
        return preferredSize(g2, bounds, null);
    }

    /**
     * Returns the preferred size of the element (including insets).
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * @param constraints  the constraints (ignored for now).
     * 
     * @return The preferred size. 
     */
    public abstract Dimension2D preferredSize(Graphics2D g2, Rectangle2D bounds, 
            Map<String, Object> constraints);
    
    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractTableElement)) {
            return false;
        }
        AbstractTableElement that = (AbstractTableElement) obj;
        if (!this.insets.equals(that.insets)) {
            return false;
        }
        if (!ObjectUtils.equalsPaint(this.backgroundPaint, 
                that.backgroundPaint)) {
            return false;
        }
        if (!ObjectUtils.equalsPaint(this.foregroundPaint, 
                that.foregroundPaint)) {
            return false;
        }
        return true;
    }
   
}
