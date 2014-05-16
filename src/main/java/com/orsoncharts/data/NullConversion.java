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

package com.orsoncharts.data;

/**
 * Options for handling <code>null</code> values during dataset extraction
 * (SKIP, CONVERT_TO_NAN, CONVERT_TO_ZERO, THROW_EXCEPTION).
 * 
 * @since 1.3
 */
public enum NullConversion {
    
    /** Skip the item that contains the <code>null</code> value. */
    SKIP, 
    
    /** Convert the <code>null</code> to <code>Double.NaN</code>. */
    CONVERT_TO_NAN, 
    
    /** Convert the <code>null</code> to zero. */
    CONVERT_TO_ZERO, 
    
    /** Throw a runtime exception. */
    THROW_EXCEPTION
}
