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

/**
 * An interface for a dataset with multiple series of data in the form of
 * <code>(rowKey, columnKey, value)</code>.  This could be used for a 3D bar 
 * chart, or a stacked 3D bar chart. 
 */
public interface CategoryDataset3D extends KeyedValues3D, Dataset3D {

}
