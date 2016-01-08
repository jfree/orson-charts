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

package com.orsoncharts.legend;

import com.orsoncharts.TitleAnchor;
import com.orsoncharts.util.Anchor2D;

/**
 * Predefined legend anchor points, provided for convenience.  The anchor
 * points are simply instances of the {@link Anchor2D} class.
 */
public final class LegendAnchor {
    
    /**
     * An anchor point at the top-left of the chart area. 
     */
    public static final Anchor2D TOP_LEFT = TitleAnchor.TOP_LEFT;

    /**
     * An anchor point at the top-right of the chart area. 
     */
    public static final Anchor2D TOP_RIGHT = TitleAnchor.TOP_RIGHT;

    /**
     * An anchor point at the top-center of the chart area. 
     */
    public static final Anchor2D TOP_CENTER = TitleAnchor.TOP_CENTER;

    /**
     * An anchor point at the center-left of the chart area. 
     */
    public static final Anchor2D CENTER_LEFT = TitleAnchor.CENTER_LEFT;

    /**
     * An anchor point at the center-right of the chart area. 
     */
    public static final Anchor2D CENTER_RIGHT = TitleAnchor.CENTER_RIGHT;

    /**
     * An anchor point at the bottom-center of the chart area. 
     */
    public static final Anchor2D BOTTOM_CENTER = TitleAnchor.BOTTOM_CENTER;

    /**
     * An anchor point at the bottom-left of the chart area. 
     */
    public static final Anchor2D BOTTOM_LEFT = TitleAnchor.BOTTOM_LEFT;
    
    /**
     * An anchor point at the bottom-right of the chart area. 
     */
    public static final Anchor2D BOTTOM_RIGHT = TitleAnchor.BOTTOM_RIGHT;
    
    private LegendAnchor() {
        // no need to instantiate this
    }
}
