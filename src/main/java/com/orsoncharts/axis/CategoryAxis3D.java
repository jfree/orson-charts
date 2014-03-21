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

import java.util.List;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.marker.CategoryMarker;
import com.orsoncharts.marker.MarkerData;
import com.orsoncharts.plot.CategoryPlot3D;

/**
 * An axis that displays categories and is used with a {@link CategoryPlot3D}.
 */
public interface CategoryAxis3D extends Axis3D {
    
    /**
     * Returns <code>true</code> if this axis is being used as the row axis
     * in a plot, and <code>false</code> otherwise.
     * 
     * @return A boolean.
     * 
     * @since 1.3
     */
    boolean isRowAxis();
    
    /**
     * Returns <code>true</code> if this axis is being used as the column axis
     * in a plot, and <code>false</code> otherwise.
     * 
     * @return A boolean.
     * 
     * @since 1.3
     */
    boolean isColumnAxis();
    
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
    double getCategoryValue(Comparable<?> category);
    
    /**
     * Generates the tick data for the axis (assumes the axis is being used
     * as the row axis).  The dataset is passed as an argument to provide the 
     * opportunity to incorporate dataset-specific info into tick labels (for 
     * example, a row label might show the total for that row in the dataset)
     * ---whether or not this is used depends on the axis implementation.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return The tick data.
     * 
     * @since 1.2
     */
    List<TickData> generateTickDataForRows(CategoryDataset3D dataset);

    /**
     * Generates the tick data for the axis (assumes the axis is being used
     * as the row axis).  The dataset is passed as an argument to provide the 
     * opportunity to incorporate dataset-specific info into tick labels (for 
     * example, a row label might show the total for that row in the dataset)
     * ---whether or not this is used depends on the axis implementation.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return The tick data.
     * 
     * @since 1.2
     */
    List<TickData> generateTickDataForColumns(CategoryDataset3D dataset);

    /** 
     * Returns a list of marker data instances for the markers that fall
     * within the current axis range.
     * 
     * @return A list of marker data. 
     */
    List<MarkerData> generateMarkerData();
    
    /**
     * Returns the marker with the specified key, if there is one.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return The marker (possibly <code>null</code>). 
     * 
     * @since 1.2
     */
    CategoryMarker getMarker(String key);
}
