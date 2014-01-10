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

package com.orsoncharts.label;

import com.orsoncharts.data.category.CategoryDataset3D;

/**
 * A label generator for category charts, used to create labels for the axes 
 * and for the chart legend.  The ({@link StandardCategoryLabelGenerator}) class
 * provides the default implementation.
 * 
 * @since 1.2
 */
public interface CategoryLabelGenerator {

    /**
     * Generates a label for one series in a {@link CategoryDataset3D}.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param seriesKey  the series key (<code>null</code> not permitted).
     * 
     * @return The series label (possibly <code>null</code>).
     */
    String generateSeriesLabel(CategoryDataset3D dataset, 
            Comparable<?> seriesKey);

    /**
     * Generates a label for one row in a {@link CategoryDataset3D}.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param rowKey  the key (<code>null</code> not permitted).
     * 
     * @return The row label (possibly <code>null</code>).
     */
    String generateRowLabel(CategoryDataset3D dataset, Comparable<?> rowKey);
    
    /**
     * Generates a label for one column in a {@link CategoryDataset3D}.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param columnKey  the key (<code>null</code> not permitted).
     * 
     * @return The column label (possibly <code>null</code>).
     */
    String generateColumnLabel(CategoryDataset3D dataset, 
            Comparable<?> columnKey);

}