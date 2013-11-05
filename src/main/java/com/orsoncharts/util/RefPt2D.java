/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
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
     * Returns <code>true</code> if the reference point is at the left, and
     * <code>false</code> otherwise.
     * 
     * @return A boolean. 
     */
    public boolean isLeft() {
        return (this == TOP_LEFT || this == CENTER_LEFT || this == BOTTOM_LEFT);
    }
    
    /**
     * Returns <code>true</code> if the reference point is at the right, and
     * <code>false</code> otherwise.
     * 
     * @return A boolean. 
     */
    public boolean isRight() {
        return (this == TOP_RIGHT || this == CENTER_RIGHT 
                || this == BOTTOM_RIGHT);        
    }
    
    /**
     * Returns <code>true</code> if the reference point is at the center 
     * horizontally, and <code>false</code> otherwise.
     * 
     * @return A boolean. 
     */
    public boolean isHorizontalCenter() {
        return (this == TOP_CENTER || this == CENTER 
                || this == BOTTOM_CENTER);                
    }
    
    /**
     * Returns <code>true</code> if the reference point is at the top, and 
     * <code>false</code> otherwise.
     * 
     * @return A boolean. 
     */
    public boolean isTop() {
        return (this == TOP_LEFT || this == TOP_CENTER || this == TOP_RIGHT);
    }

    /**
     * Returns <code>true</code> if the reference point is at the bottom, and 
     * <code>false</code> otherwise.
     * 
     * @return A boolean. 
     */
    public boolean isBottom() {
        return (this == BOTTOM_LEFT || this == BOTTOM_CENTER 
                || this == BOTTOM_RIGHT);
    }
    
    /**
     * Returns <code>true</code> if the reference point is at the center 
     * vertically, and <code>false</code> otherwise.
     * 
     * @return A boolean. 
     */
    public boolean isVerticalCenter() {
        return (this == CENTER_LEFT || this == CENTER 
                || this == CENTER_RIGHT);
    }
}
