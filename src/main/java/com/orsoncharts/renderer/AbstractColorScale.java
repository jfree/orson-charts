/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer;

import java.io.Serializable;
import com.orsoncharts.Range;
import com.orsoncharts.util.ArgChecks;

/**
 * An abstract class from which a {@link ColorScale} implementation can be
 * derived.
 * 
 * @since 1.1
 */
public abstract class AbstractColorScale implements Serializable {
    
    /** The data value range for the scale. */
    private Range range;
    
    /**
     * Creates a new color scale for the specified data value range.  
     * 
     * @param range  the data value range (<code>null</code> not permitted).
     */
    protected AbstractColorScale(Range range) {
        ArgChecks.nullNotPermitted(range, "range");
        this.range = range;
    }

    /**
     * Returns the range of data values over which the scale is defined.  This
     * is specified via the constructor and once set cannot be changed.
     * 
     * @return The range (never <code>null</code>). 
     */
    public Range getRange() {
        return this.range;
    }

    /**
     * Tests this color scale for equality with an arbitrary object.
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
        if (!(obj instanceof AbstractColorScale)) {
            return false;
        }
        AbstractColorScale that = (AbstractColorScale) obj;
        if (!this.range.equals(that.range)) {
            return false;
        }
        return true;
    }

}
