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

package com.orsoncharts;

import com.orsoncharts.util.Anchor2D;
import com.orsoncharts.util.RefPt2D;

/**
 * Predefined title anchor points, provided for convenience.  These anchor
 * points are instances of the {@link Anchor2D} class, initialised with an 
 * offset of 4 units from each corner of the target rectangle.
 */
public final class TitleAnchor {
    
    /**
     * An anchor point at the top-left of the chart area. 
     */
    public static final Anchor2D TOP_LEFT = new Anchor2D(RefPt2D.TOP_LEFT);

    /**
     * An anchor point at the top-right of the chart area. 
     */
    public static final Anchor2D TOP_RIGHT = new Anchor2D(RefPt2D.TOP_RIGHT);

    /**
     * An anchor point at the top-center of the chart area. 
     */
    public static final Anchor2D TOP_CENTER = new Anchor2D(RefPt2D.TOP_CENTER);

    /**
     * An anchor point at the center-left of the chart area. 
     */
    public static final Anchor2D CENTER_LEFT 
            = new Anchor2D(RefPt2D.CENTER_LEFT);

    /**
     * An anchor point at the center of the chart area (provided for
     * completeness, you wouldn't normally anchor a chart title at the 
     * center).
     */
    public static final Anchor2D CENTER = new Anchor2D(RefPt2D.CENTER);

    /**
     * An anchor point at the center-right of the chart area. 
     */
    public static final Anchor2D CENTER_RIGHT 
            = new Anchor2D(RefPt2D.CENTER_RIGHT);

    /**
     * An anchor point at the bottom-center of the chart area. 
     */
    public static final Anchor2D BOTTOM_CENTER 
            = new Anchor2D(RefPt2D.BOTTOM_CENTER);

    /**
     * An anchor point at the bottom-left of the chart area. 
     */
    public static final Anchor2D BOTTOM_LEFT 
            = new Anchor2D(RefPt2D.BOTTOM_LEFT);
    
    /**
     * An anchor point at the bottom-right of the chart area. 
     */
    public static final Anchor2D BOTTOM_RIGHT 
            = new Anchor2D(RefPt2D.BOTTOM_RIGHT);
    
    private TitleAnchor() {
        // no need to instantiate this
    }
}
