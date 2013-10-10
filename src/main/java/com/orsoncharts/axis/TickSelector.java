/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.axis;

import java.text.Format;

/**
 * Provides standard tick sizes and formatting.
 */
public interface TickSelector {
    
    /**
     * Selects a standard tick unit as close as possible to the reference 
     * value.
     * 
     * @param reference  the reference value.
     * 
     * @return The standard tick unit.
     */
    public double select(double reference);
    
    /**
     * Move the cursor to the next tick size.
     */
    public boolean next();
    
    /**
     * Move the cursor to the previous tick size.
     * 
     * @return A boolean. 
     */
    public boolean previous();
    
    /**
     * Returns the tick size that the cursor is currently referencing.
     * 
     * @return The tick size. 
     */
    public double getCurrentTickSize();
    
    /**
     * Returns the tick formatter associated with the tick size that the 
     * cursor is currently referencing.
     * 
     * @return The formatter.
     */
    public Format getCurrentTickLabelFormat();
    
}
