/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

import com.orsoncharts.plot.PiePlot3D;

/**
 * The interface through which the {@link PiePlot3D} class obtains data for
 * pie charts.
 */
public interface PieDataset3D<T> extends KeyedValues<T>, Dataset3D {

}
