/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts;

import com.orsoncharts.plot.Plot3D;
import com.orsoncharts.table.TableElement;

/**
 * LegendBuilder.
 */
public interface LegendBuilder {

    /**
     * Creates a legend for the specified plot.
     * 
     * @param plot  the plot (<code>null</code> not permitted).
     * 
     * @return A legend.
     */
    TableElement createLegend(Plot3D plot);
}
