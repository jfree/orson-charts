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

package com.orsoncharts.table;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Map;

import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ObjectUtils;
import com.orsoncharts.util.RefPt2D;

/**
 * A base class that can be used to implement a {@link TableElement}.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
@SuppressWarnings("serial")
public abstract class AbstractTableElement implements Serializable {
    
    /** The default background color. */
    private static final Color DEFAULT_BACKGROUND_COLOR 
            = new Color(255, 255, 255, 127);
    
    /** The reference point used to align the element when rendering. */
    private RefPt2D refPt;
    
    /** The insets. */
    private Insets insets;
    
    /** The background paint (this can be <code>null</code>). */
    private RectanglePainter background;
    
    /** A tag that can be used to identify the class of element. */
    private String tag;
    
    /**
     * Creates a new instance.
     */
    public AbstractTableElement() {
        this.refPt = RefPt2D.CENTER;
        this.insets = new Insets(2, 2, 2, 2);
        this.background = new StandardRectanglePainter(
                DEFAULT_BACKGROUND_COLOR);
        this.tag = "";
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
     * Returns the background painter for the element.
     * 
     * @return The background painter (possibly <code>null</code>). 
     */
    public RectanglePainter getBackground() {
        return this.background;
    }
    
    /**
     * Sets the background for the element.
     * 
     * @param background  the new background (<code>null</code> permitted).
     */
    public void setBackground(RectanglePainter background) {
        this.background = background;
    }
    
    /**
     * Sets the background painter to fill the element with the specified 
     * color.  If the color is <code>null</code>, the background painter will
     * be set to <code>null</code>.
     * 
     * @param color  the color (<code>null</code> permitted). 
     * 
     * @since 1.2
     */
    public void setBackgroundColor(Color color) {
        if (color != null) {
            this.background = new StandardRectanglePainter(color);
        } else {
            this.background = null;
        }
    }
    
    /**
     * Returns the tag for this element.  The default value is an empty string.
     * 
     * @return The tag (never <code>null</code>). 
     * 
     * @since 1.2
     */
    public String getTag() {
        return this.tag;
    }
    
    /**
     * Sets the tag.
     * 
     * @param tag  the tag (<code>null</code> not permitted).
     * 
     * @since 1.2
     */
    public void setTag(String tag) {
        ArgChecks.nullNotPermitted(tag, "tag");
        this.tag = tag;
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
        if (!ObjectUtils.equals(this.background, that.background)) {
            return false;
        }
        return true;
    }
   
}
