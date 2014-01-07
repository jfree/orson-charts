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

import com.orsoncharts.plot.PiePlot3D;

/**
 * The interface through which the {@link PiePlot3D} class obtains data for
 * pie charts.  The interface defines methods for reading data only, not for
 * updating the data (however, classes that implement the interface will
 * typically provide their own methods for updating the dataset).
 */
public interface PieDataset3D extends KeyedValues<Number>, Dataset3D {

}
