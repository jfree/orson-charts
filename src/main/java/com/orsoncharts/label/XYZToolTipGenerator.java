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
 * A tool tip generator for XYZ charts.  The 
 * ({@link StandardXYZToolTipGenerator}) class provides the default 
 * implementation.
 * 
 * @since 1.3
 */
public interface XYZToolTipGenerator {
    
    /**
     * Generates a tool tip for one data item in a {@link XYZDataset}.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param seriesKey  the series key (<code>null</code> not permitted).
     * @param itemIndex  the item index.
     * 
     * @return The series label (possibly <code>null</code>).
     */
    String generateToolTipText(XYZDataset dataset, Comparable<?> seriesKey,
            int itemIndex);
    
}
