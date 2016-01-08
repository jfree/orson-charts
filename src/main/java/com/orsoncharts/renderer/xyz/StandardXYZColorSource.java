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

package com.orsoncharts.renderer.xyz;

import java.awt.Color;
import java.io.Serializable;

import com.orsoncharts.Colors;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ObjectUtils;

/**
 * A standard implementation of the {@link XYZColorSource} interface.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
@SuppressWarnings("serial")
public class StandardXYZColorSource implements XYZColorSource, Serializable {

    /** The sequence of color objects to use for each series. */
    private Color[] colors;
    
    /**
     * Creates a new instance with default colors.
     */
    public StandardXYZColorSource() {
        this(Colors.getDefaultColors());    
    }
    
    /**
     * Creates a new instance with the supplied sequence of colors.  The
     * supplied array must have at least one entry, and all entries must be
     * non-{@code null}.
     * 
     * @param colors  the colors ({@code null} not permitted). 
     */
    public StandardXYZColorSource(Color... colors) {
        ArgChecks.nullNotPermitted(colors, "colors");
        if (colors.length == 0) {
            throw new IllegalArgumentException(
                    "Zero length array not permitted.");
        }
        for (Color c: colors) {
            if (c == null) { 
                throw new IllegalArgumentException(
                        "Null array entries not permitted.");
            }
        }
        this.colors = colors;
    }
    
    /**
     * Returns the color to use for the specified item.
     * 
     * @param series  the series index.
     * @param item  the item index.
     * 
     * @return The color (never {@code null}). 
     */
    @Override
    public Color getColor(int series, int item) {
        return this.colors[series % this.colors.length];
    }

    /**
     * Returns the color to use in the legend for the specified series.
     * 
     * @param series  the series index.
     * 
     * @return The color (never {@code null}).
     */
    @Override
    public Color getLegendColor(int series) {
        return this.colors[series % this.colors.length];
    }
    
    /**
     * Restyles the source using the specified colors.  Refer to the 
     * implementing class to confirm the precise behaviour (typically all 
     * existing color settings are cleared and the specified colors are 
     * installed as the new defaults).
     * 
     * @param colors  the colors.
     * 
     * @since 1.2
     */
    @Override
    public void style(Color... colors) {
        ArgChecks.nullNotPermitted(colors, "colors");
        if (colors.length == 0) {
            throw new IllegalArgumentException(
                    "Zero length array not permitted.");
        }
        for (Color c: colors) {
            if (c == null) { 
                throw new IllegalArgumentException(
                        "Null array entries not permitted.");
            }
        }
        this.colors = colors.clone();
    }

    /**
     * Tests this paint source for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StandardXYZColorSource)) {
            return false;
        }
        StandardXYZColorSource that = (StandardXYZColorSource) obj;
        if (this.colors.length != that.colors.length) {
            return false;
        }
        for (int i = 0; i < this.colors.length; i++) {
            if (!ObjectUtils.equalsPaint(this.colors[i], that.colors[i])) {
                return false;
            }
        }
        return true;
    }
    
}
