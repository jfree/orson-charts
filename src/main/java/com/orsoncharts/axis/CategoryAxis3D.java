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
 * A category axis.
 */
public interface CategoryAxis3D extends Axis3D {

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
