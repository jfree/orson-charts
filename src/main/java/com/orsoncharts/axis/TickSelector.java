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

package com.orsoncharts.axis;

import java.text.Format;

/**
 * <p>Provides standard tick sizes and formatting for numerical axes.  
 * Conceptually, the selector maintains a list of standard tick sizes (ordered 
 * by size), and a pointer to the current (selected) tick size.  Clients of
 * the selector will initialise the pointer by calling the 
 * {@link #select(double)} method with a reference value (a guess, based on 
 * context, of the largest usable tick size - for example, one half of the
 * axis length) then, as required, move the pointer to a smaller or larger
 * tick size (using the {@link #next()} and {@link #previous()} methods) to 
 * find the most appropriate standard size to use.</p>
 * 
 * <p>The {@link NumberTickSelector} class provides a standard implementation, 
 * but you can create your own if necessary.</p>
 */
public interface TickSelector {
    
    /**
     * Selects and returns a standard tick size that is greater than or equal to 
     * the specified reference value and, ideally, as close to it as possible 
     * (to minimise the number of iterations used by axes to determine the tick
     * size to use).  After a call to this method, the 
     * {@link #getCurrentTickSize()} method should return the selected tick 
     * size (there is a "cursor" that points to this tick size), the 
     * {@link #next()} method should move the pointer to the next (larger) 
     * standard tick size, and the {@link #previous()} method should move the 
     * pointer to the  previous (smaller) standard tick size.
     * 
     * @param reference  the reference value (must be positive and finite).
     * 
     * @return The selected tick size. 
     */
    double select(double reference);
    
    /**
     * Move the cursor to the next (larger) tick size, if there is one.  
     * Returns <code>true</code> in the case that the cursor is moved, and 
     * <code>false</code> where there are a finite number of tick sizes and the
     * current tick size is the largest available.
     * 
     * @return A boolean.
     */
    boolean next();
    
    /**
     * Move the cursor to the previous (smaller) tick size, if there is one.  
     * Returns <code>true</code> in the case that the cursor is moved, and 
     * <code>false</code> where there are a finite number of tick sizes and the
     * current tick size is the smallest available.
     * 
     * @return A boolean.
     */
    boolean previous();
    
    /**
     * Returns the tick size that the cursor is currently referencing.
     * 
     * @return The tick size. 
     */
    double getCurrentTickSize();
    
    /**
     * Returns the tick formatter associated with the tick size that the 
     * cursor is currently referencing.
     * 
     * @return The formatter.
     */
    Format getCurrentTickLabelFormat();
    
}
