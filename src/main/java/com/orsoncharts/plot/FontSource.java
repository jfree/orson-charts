/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package com.orsoncharts.plot;

import java.awt.Font;

/**
 * An object that supplies {@code Font} instances associated with
 * keys.  This is used by the {@link PiePlot3D} class to obtain section label
 * fonts for each data item(segment) in the chart.  A default implementation
 * ({@link StandardFontSource}) is provided.
 */
public interface FontSource<K> {

    /**
     * Returns a font based on the supplied key.
     * 
     * @param key  the key ({@code null} not permitted).
     * 
     * @return A font (never {@code null}). 
     */
    Font getFont(K key);
    
    /**
     * Sets the font associated with a key.
     * 
     * @param key  the key ({@code null} not permitted).
     * @param font  the font ({@code null} not permitted).
     */
    void setFont(K key, Font font);
    
    /**
     * Restyles the source using the specified font.  Refer to the implementing
     * class to confirm the precise behaviour (typically all existing font
     * settings are cleared and this font is installed as the new default
     * section label font).
     * 
     * @param font  the font ({@code null} not permitted). 
     * 
     * @since 1.2
     */
    void style(Font font);
 
}