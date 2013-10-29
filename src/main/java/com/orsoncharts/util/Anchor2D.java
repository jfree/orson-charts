/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.util;

import java.io.Serializable;
import com.orsoncharts.graphics3d.Offset2D;

/**
 * Represents an anchor point for a chart title and/or legend.  Instances
 * of this class are immutable.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
public final class Anchor2D implements Serializable {
    
    /** 
     * The reference point relative to some bounding rectangle (normally the 
     * bounds of the chart, never <code>null</code>). 
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
