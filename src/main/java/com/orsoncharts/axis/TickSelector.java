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
     * Returns {@code true} in the case that the cursor is moved, and 
     * {@code false} where there are a finite number of tick sizes and the
     * current tick size is the largest available.
     * 
     * @return A boolean.
     */
    boolean next();
    
    /**
     * Move the cursor to the previous (smaller) tick size, if there is one.  
     * Returns {@code true} in the case that the cursor is moved, and 
     * {@code false} where there are a finite number of tick sizes and the
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
