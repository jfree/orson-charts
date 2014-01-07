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

package com.orsoncharts.util;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import com.orsoncharts.TitleAnchor;
import com.orsoncharts.graphics3d.Offset2D;
import com.orsoncharts.legend.LegendAnchor;

/**
 * Represents an anchor point for a chart title and/or legend.  The anchor
 * point is defined relative to a reference rectangle, the dimensions of which
 * are not known in advance (typically the reference rectangle is the bounding
 * rectangle of a chart that is being drawn).  Some predefined anchor points 
 * are provided in the {@link TitleAnchor} and {@link LegendAnchor} classes.  
 * <br><br>
 * Instances of this class are immutable.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
public final class Anchor2D implements Serializable {

    /** 
     * An anchor point at the top left with zero offset from the target
     * rectangle bounds.
     * 
     * @since 1.1
     */
    public static final Anchor2D TOP_LEFT = new Anchor2D(RefPt2D.TOP_LEFT, 
            Offset2D.ZERO_OFFSET);

    /** 
     * An anchor point at the top center with zero offset from the target
     * rectangle bounds.
     * 
     * @since 1.1
     */
    public static final Anchor2D TOP_CENTER = new Anchor2D(RefPt2D.TOP_CENTER, 
            Offset2D.ZERO_OFFSET);

    /** 
     * An anchor point at the top right with zero offset from the target
     * rectangle bounds.
     * 
     * @since 1.1
     */
    public static final Anchor2D TOP_RIGHT = new Anchor2D(RefPt2D.TOP_RIGHT, 
            Offset2D.ZERO_OFFSET);

    /** 
     * An anchor point at the center left with zero offset from the target
     * rectangle bounds.
     * 
     * @since 1.1
     */
    public static final Anchor2D CENTER_LEFT = new Anchor2D(RefPt2D.CENTER_LEFT,
            Offset2D.ZERO_OFFSET);
    
    /** 
     * An anchor point at the center of the target rectangle.
     * 
     * @since 1.1
     */
    public static final Anchor2D CENTER = new Anchor2D(RefPt2D.CENTER,
            Offset2D.ZERO_OFFSET);

    /** 
     * An anchor point at the center right with zero offset from the target
     * rectangle bounds.
     * 
     * @since 1.1
     */
    public static final Anchor2D CENTER_RIGHT 
            = new Anchor2D(RefPt2D.CENTER_RIGHT, Offset2D.ZERO_OFFSET);

    /** 
     * An anchor point at the bottom left with zero offset from the target
     * rectangle bounds.
     * 
     * @since 1.1
     */
    public static final Anchor2D BOTTOM_LEFT = new Anchor2D(RefPt2D.BOTTOM_LEFT, 
            Offset2D.ZERO_OFFSET);

    /** 
     * An anchor point at the bottom center with zero offset from the target
     * rectangle bounds.
     * 
     * @since 1.1
     */
    public static final Anchor2D BOTTOM_CENTER 
            = new Anchor2D(RefPt2D.BOTTOM_CENTER, Offset2D.ZERO_OFFSET);

    /** 
     * An anchor point at the bottom right with zero offset from the target
     * rectangle bounds.
     * 
     * @since 1.1
     */
    public static final Anchor2D BOTTOM_RIGHT 
            = new Anchor2D(RefPt2D.BOTTOM_RIGHT, Offset2D.ZERO_OFFSET);

    /** 
     * The reference point relative to some bounding rectangle, normally the 
     * bounds of the chart (never <code>null</code>). 
     */
    private RefPt2D refPt;
    
    /**
     * The offsets to apply (never <code>null</code>).
     */
    private Offset2D offset;
    
    /**
     * Creates a default instance.
     */
    public Anchor2D() {
        this(RefPt2D.TOP_LEFT);
    }
    
    /**
     * Creates a new <code>Anchor2D</code> instance with the specified 
     * reference point and offsets of <code>(4.0, 4.0)</code>.
     * 
     * @param refPt  the reference point (<code>null</code> not permitted).
     */
    public Anchor2D(RefPt2D refPt) {
        this(refPt, new Offset2D(4.0, 4.0));    
    }
    
    /**
     * Creates a new anchor.
     * 
     * @param refPt  the reference point (<code>null</code> not permitted).
     * @param offset  the offset (<code>null</code> not permitted).
     */
    public Anchor2D(RefPt2D refPt, Offset2D offset) {
        ArgChecks.nullNotPermitted(refPt, "refPt");
        ArgChecks.nullNotPermitted(offset, "offset");
        this.refPt = refPt;
        this.offset = offset;
    }

    /**
     * Returns the reference point.
     * 
     * @return The reference point (never <code>null</code>). 
     */
    public RefPt2D getRefPt() {
        return this.refPt;
    }
    
    /**
     * Returns the offsets.
     * 
     * @return The offsets (never <code>null</code>). 
     */
    public Offset2D getOffset() {
        return this.offset;
    }
    
    /**
     * Returns the anchor point for the given rectangle.
     * 
     * @param rect  the reference rectangle (<code>null</code> not permitted).
     * 
     * @return The anchor point. 
     */
    public Point2D getAnchorPoint(Rectangle2D rect) {
        ArgChecks.nullNotPermitted(rect, "rect");
        double x = 0.0;
        double y = 0.0;
        if (this.refPt.isLeft()) {
            x = rect.getX() + this.offset.getDX();
        } else if (this.refPt.isHorizontalCenter()) {
            x = rect.getCenterX();
        } else if (this.refPt.isRight()) {
            x = rect.getMaxX() - this.offset.getDX();
        }
        if (this.refPt.isTop()) {
            y = rect.getMinY();
        } else if (this.refPt.isVerticalCenter()) {
            y = rect.getCenterY();
        } else if (this.refPt.isBottom()) {
            y = rect.getMaxY();
        }
        return new Point2D.Double(x, y);
    }
    
    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> not permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Anchor2D)) {
            return false;
        }
        Anchor2D that = (Anchor2D) obj;
        if (!this.refPt.equals(that.refPt)) {
            return false;
        }
        if (!this.offset.equals(that.offset)) {
            return false;
        }
        return true;
    }
}
