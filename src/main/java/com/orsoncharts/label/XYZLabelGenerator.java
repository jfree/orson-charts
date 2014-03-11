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

package com.orsoncharts.label;

import com.orsoncharts.data.xyz.XYZDataset;

/**
 * A label generator for XYZ charts, used to create labels for the chart 
 * legend.  The ({@link StandardXYZLabelGenerator}) class provides the default 
 * implementation.
 * 
 * @since 1.2
 */
public interface XYZLabelGenerator {

    /**
     * Generates a label for one series in a {@link XYZDataset}.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param seriesKey  the series key (<code>null</code> not permitted).
     * 
     * @return The series label (possibly <code>null</code>).
     */
    String generateSeriesLabel(XYZDataset dataset, Comparable<?> seriesKey);

}