/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.axis;

import com.orsoncharts.plot.CategoryPlot3D;

/**
 * An axis that displays categories and is used with a {@link CategoryPlot3D}.
 */
public interface CategoryAxis3D extends Axis3D {

    /**
     * Returns a flag indicating whether or not the axis should be drawn.  
     * 
     * @return A boolean. 
     */
    boolean isVisible();
    
    /**
     * Sets the flag that controls whether or not the axis is drawn on the 
     * chart and sends an {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param visible  the new flag value.
     */
    void setVisible(boolean visible);
    
    /**
     * Configure the axis as a row axis for the specified plot.
     * 
     * @param plot  the plot (<code>null</code> not permitted). 
     */
    void configureAsRowAxis(CategoryPlot3D plot);
    
    /**
     * Configure the axis as a column axis for the specified plot.
     * 
     * @param plot  the plot (<code>null</code> not permitted). 
     */
    void configureAsColumnAxis(CategoryPlot3D plot);

    /**
     * Returns the width of a single category in units corresponding to 
     * the current axis range.
     * 
     * @return The width of a single category. 
     */
    double getCategoryWidth();
    
    /**
     * Returns the numerical value along the axis that corresponds to the
     * specified category.  If the category is unknown, this method will
     * return <code>Double.NaN</code>.
     * 
     * @param category  the category (<code>null</code> not permitted).
     * 
     * @return The axis value. 
     */
    double getCategoryValue(Comparable category);
    
}
