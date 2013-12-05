/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.renderer;

import java.awt.Color;
import com.orsoncharts.Range;

/**
 * A color scale converts a data value into a color according to some
 * predefined color scale.
 * 
 * @since 1.1
 */
public interface ColorScale {

    /** 
     * Returns the range over which the scale is defined.
     * 
     * @return The range (never <code>null</code>).
     */
    Range getRange();
    
    /**
     * Returns the color on the scale that corresponds to the specified
     * value.
     * 
     * @param value  the value.
     * 
     * @return The color.
     */
    Color valueToColor(double value);

}
