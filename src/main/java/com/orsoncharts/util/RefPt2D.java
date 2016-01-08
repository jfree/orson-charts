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

package com.orsoncharts.util;

/**
 * An enumeration of reference points within a rectangle.  These reference 
 * points are used to place titles, legends and other labels.
 * 
 * @see Anchor2D
 */
public enum RefPt2D {

    /** The top-left corner of a rectangle. */
    TOP_LEFT,
  
    /** The middle of a rectangle at the top. */
    TOP_CENTER,
  
    /** The top-right corner of a rectangle. */
    TOP_RIGHT,
  
    /** The middle of a rectangle at the left side. */
    CENTER_LEFT,
  
    /** The center of a rectangle. */
    CENTER,
  
    /** The middle of a rectangle at the right side. */
    CENTER_RIGHT,
  
    /** The bottom-left corner of a rectangle. */
    BOTTOM_LEFT, 
  
    /** The middle of a rectangle at the bottom. */
    BOTTOM_CENTER,
  
    /** The bottom-right corner of a rectangle. */
    BOTTOM_RIGHT;
    
    /**
     * Returns {@code true} if the reference point is at the left, and
     * {@code false} otherwise.
     * 
     * @return A boolean. 
     */
    public boolean isLeft() {
        return (this == TOP_LEFT || this == CENTER_LEFT || this == BOTTOM_LEFT);
    }
    
    /**
     * Returns {@code true} if the reference point is at the right, and
     * {@code false} otherwise.
     * 
     * @return A boolean. 
     */
    public boolean isRight() {
        return (this == TOP_RIGHT || this == CENTER_RIGHT 
                || this == BOTTOM_RIGHT);        
    }
    
    /**
     * Returns {@code true} if the reference point is at the center 
     * horizontally, and {@code false} otherwise.
     * 
     * @return A boolean. 
     */
    public boolean isHorizontalCenter() {
        return (this == TOP_CENTER || this == CENTER 
                || this == BOTTOM_CENTER);                
    }
    
    /**
     * Returns {@code true} if the reference point is at the top, and 
     * {@code false} otherwise.
     * 
     * @return A boolean. 
     */
    public boolean isTop() {
        return (this == TOP_LEFT || this == TOP_CENTER || this == TOP_RIGHT);
    }

    /**
     * Returns {@code true} if the reference point is at the bottom, and 
     * {@code false} otherwise.
     * 
     * @return A boolean. 
     */
    public boolean isBottom() {
        return (this == BOTTOM_LEFT || this == BOTTOM_CENTER 
                || this == BOTTOM_RIGHT);
    }
    
    /**
     * Returns {@code true} if the reference point is at the center 
     * vertically, and {@code false} otherwise.
     * 
     * @return A boolean. 
     */
    public boolean isVerticalCenter() {
        return (this == CENTER_LEFT || this == CENTER 
                || this == CENTER_RIGHT);
    }
}
