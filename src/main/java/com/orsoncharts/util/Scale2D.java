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

package com.orsoncharts.util;

/**
 * Scaling options for fitting rectangles.
 */
public enum Scale2D {
    
    /** No scaling. */
    NONE, 
    
    /** Scale horizontally (but not vertically). */
    SCALE_HORIZONTAL,
    
    /** Scale vertically (but not horizontally). */
    SCALE_VERTICAL, 
    
    /** Scale both horizontally and vertically. */
    SCALE_BOTH;
    
}
