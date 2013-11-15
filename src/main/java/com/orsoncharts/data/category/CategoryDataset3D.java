/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data.category;

import com.orsoncharts.data.Dataset3D;
import com.orsoncharts.data.KeyedValues3D;
import com.orsoncharts.plot.CategoryPlot3D;

/**
 * An interface for a dataset with multiple series of data in the form of
 * <code>(rowKey, columnKey, value)</code>.  This is the standard data 
 * interface used by the {@link CategoryPlot3D} class. 
 */
public interface CategoryDataset3D extends KeyedValues3D, Dataset3D {

}
