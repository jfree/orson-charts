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

import com.orsoncharts.Range;

/**
 * A marker that can be used to mark a value or range of values on a 
 * {@link com.orsoncharts.axis.ValueAxis3D}.
 * 
 * @since 1.2
 */
public interface ValueMarker extends Marker {

    /**
     * Returns the range of values covered by the marker.  If the marker
     * represents a single value rather than a range of values, then the range
     * that is returned will have zero length.
     * 
     * @return The range (never <code>null</code>). 
     */
    Range getRange();
    
}
