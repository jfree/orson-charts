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
 * A label generator for data items in XYZ charts (this can be used for 
 * tooltips and item labels).  The ({@link StandardXYZItemLabelGenerator}) 
 * class provides the default implementation.
 * 
 * @since 1.3
 */
public interface XYZItemLabelGenerator {
    
    /**
     * Generates an item label for one data item in an {@link XYZDataset}.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param seriesKey  the series key (<code>null</code> not permitted).
     * @param itemIndex  the item index.
     * 
     * @return The item label (possibly <code>null</code>).
     */
    String generateItemLabel(XYZDataset dataset, Comparable<?> seriesKey,
            int itemIndex);
    
}
