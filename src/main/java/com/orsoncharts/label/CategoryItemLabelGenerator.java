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
 * An item label generator for category charts (this is used for tooltips and
 * data item labels).  Note that this generator is used for individual data
 * items, and is distinct from the {@link CategoryLabelGenerator} which
 * generates labels for series keys, column keys and row keys.  
 * The {@link StandardCategoryItemLabelGenerator} class provides the default 
 * implementation.
 * 
 * @since 1.3
 */
public interface CategoryItemLabelGenerator {

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
    String generateItemLabel(CategoryDataset3D dataset, 
            Comparable<?> seriesKey, Comparable<?> rowKey, 
            Comparable<?> columnKey);

}