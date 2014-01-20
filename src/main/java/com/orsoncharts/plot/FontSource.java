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

package com.orsoncharts.plot;

import java.awt.Font;

/**
 * An object that supplies <code>Font</code> instances associated with
 * keys.  This is used by the {@link PiePlot3D} class to obtain section label
 * fonts for each data item(segment) in the chart.  A default implementation
 * ({@link StandardFontSource}) is provided.
 */
public interface FontSource {

    /**
     * Returns a font based on the supplied key.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return A font (never <code>null</code>). 
     */
    Font getFont(Comparable<?> key);
    
    /**
     * Sets the font associated with a key.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * @param font  the font (<code>null</code> not permitted).
     */
    void setFont(Comparable<?> key, Font font);
    
    /**
     * Restyles the source using the specified font.  Refer to the implementing
     * class to confirm the precise behaviour (typically all existing font
     * settings are cleared and this font is installed as the new default
     * section label font).
     * 
     * @param font  the font (<code>null</code> not permitted). 
     * 
     * @since 1.2
     */
    void style(Font font);
 
}