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

import com.orsoncharts.data.category.CategoryDataset3D;

/**
 * A tool tip generator for category charts.  The 
 * {@link StandardCategoryToolTipGenerator} class provides the default 
 * implementation.
 * 
 * @since 1.3
 */
public interface CategoryToolTipGenerator {

    /**
     * Generates a label for one series in a {@link CategoryDataset3D}.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param seriesKey  the series key (<code>null</code> not permitted).
     * @param rowKey  the row key (<code>null</code> not permitted).
     * @param columnKey  the column key (<code>null</code> not permitted).
     * 
     * @return The series label (possibly <code>null</code>).
     */
    String generateToolTipText(CategoryDataset3D dataset, 
            Comparable<?> seriesKey, Comparable<?> rowKey, 
            Comparable<?> columnKey);

}