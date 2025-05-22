/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-present, by David Gilbert.  All rights reserved.
 * 
 * https://github.com/jfree/orson-charts
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
 * license is available to sponsors (higher tiers only) of the JFree projects.
 * For details, please see visit:
 *
 * https://github.com/sponsors/jfree
 * 
 */

package org.jfree.chart3d.renderer;

import java.awt.Color;
import org.jfree.chart3d.data.Range;

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
     * @return The range (never {@code null}).
     */
    Range getRange();
    
    /**
     * Returns the color on the scale that corresponds to the specified
     * value.
     * 
     * @param value  the value.
     * 
     * @return The color (never {@code null}).
     */
    Color valueToColor(double value);

}
