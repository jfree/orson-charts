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

package com.orsoncharts.renderer;

import com.orsoncharts.legend.ColorScaleLegendBuilder;

/**
 * An interface that should be implemented by renderers that use a 
 * {@link ColorScale} - this provides a mechanism for the 
 * {@link ColorScaleLegendBuilder} to find the color scale.
 * 
 * @since 1.1
 */
public interface ColorScaleRenderer {

    /**
     * Returns the color scale used by the renderer.
     * 
     * @return The color scale. 
     */
    ColorScale getColorScale();

}
