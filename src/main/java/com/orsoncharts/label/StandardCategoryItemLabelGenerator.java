/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package com.orsoncharts.label;

import java.io.Serializable;
import java.util.Formatter;

import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.KeyedValues3DItemKey;
import com.orsoncharts.interaction.KeyedValues3DItemSelection;
import com.orsoncharts.interaction.StandardKeyedValues3DItemSelection;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ObjectUtils;

/**
 * A default implementation of the {@link CategoryItemLabelGenerator} interface.  
 * The implementation uses a {@link java.util.Formatter} instance to generate
 * the labels.  Four values are passed to the formatter: (1) the key for
 * the series, (2) the row key, (3) the column key and (4) the data value.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 * 
 * @since 1.3
 */
@SuppressWarnings("serial")
public class StandardCategoryItemLabelGenerator 
        implements CategoryItemLabelGenerator, Serializable {

    /**
     * A template string that will show just the value (to 2 decimal places).
     * 
     * @since 1.3
     */
    public static final String VALUE_TEMPLATE = "%4$.2f";
    
    /** 
     * A template string that will show the series, row and column keys plus
     * the data value.
     * 
     * @since 1.3
     */
    public static final String KEYS_AND_VALUE_TEMPLATE = "%s, %s, %s = %4$.3f";
    
    /**
     * A template that shows the series key, column key and value (the row
     * key is omitted because it is often the same as the series key).
     * 
     * @since 1.3
     */
    public static final String SERIES_AND_COLUMN_KEYS_AND_VALUE_TEMPLATE 
            = "%1$s, %3$s = %4$.3f";
    
    /**
     * The default template string (used in the default constructor, it is
     * equivalent to {@link #SERIES_AND_COLUMN_KEYS_AND_VALUE_TEMPLATE}).
     * 
     * @since 1.3
     */
    public static final String DEFAULT_TEMPLATE 
            = SERIES_AND_COLUMN_KEYS_AND_VALUE_TEMPLATE;
        
    /** The template. */
    private String template;
    
    /** 
     * If this object is not-{@code null}, an item label will only be
     * returned by this generator if the selection contains the item that
     * the label is to be generated for.
     */
    private KeyedValues3DItemSelection itemSelection;
    
    /**
     * The default constructor.
     */
    public StandardCategoryItemLabelGenerator() {
        this(DEFAULT_TEMPLATE);
    }
    
    /**
     * Creates a new instance with the specified template string (which will
     * be passed to a {@code java.util.Formatter} instance when generating
     * labels).  See the class description for an explanation of the values 
     * that are available for use in the template string.
     * 
     * @param template  the template ({@code null} not permitted).
     */
    public StandardCategoryItemLabelGenerator(String template) {
        ArgChecks.nullNotPermitted(template, "template");
        this.template = template;
        this.itemSelection = null;
    }

    /**
     * Returns the item selection.  The default value is {@code null}.
     * 
     * @return The item selection.
     * 
     * @since 1.3
     */
    public KeyedValues3DItemSelection getItemSelection() {
        return this.itemSelection;
    }
    
    /**
     * Sets the item selection (labels will be created by this generator only 
     * for data items that are contained in the collection).  If you set the
     * selection to {@code null} then the generator will create labels for
     * all data items.
     * 
     * @param selection  the selection ({@code null} permitted).
     */
    public void setItemSelection(StandardKeyedValues3DItemSelection selection) {
        this.itemSelection = selection;
    }
    
    /**
     * Generates the item label for one data item in a category chart.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * @param seriesKey  the series key ({@code null} not permitted).
     * @param rowKey  the row key ({@code null} not permitted).
     * @param columnKey  the column key ({@code null} not permitted).
     * 
     * @return The label (never {@code null} for this implementation). 
     */
    @Override
    @SuppressWarnings("unchecked")
    public String generateItemLabel(CategoryDataset3D dataset, 
            Comparable<?> seriesKey, Comparable<?> rowKey, 
            Comparable<?> columnKey) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        ArgChecks.nullNotPermitted(rowKey, "rowKey");
        ArgChecks.nullNotPermitted(columnKey, "columnKey");
        if (this.itemSelection != null) {
            KeyedValues3DItemKey key = new KeyedValues3DItemKey(seriesKey, 
                    rowKey, columnKey);
            if (!this.itemSelection.isSelected(key)) {
                return null;
            }
        }
        Formatter formatter = new Formatter(new StringBuilder());
        Number value = (Number) dataset.getValue(seriesKey, rowKey, columnKey);
        Double d = null;
        if (value != null) {
            d = Double.valueOf(value.doubleValue());
        }
        formatter.format(this.template, seriesKey, rowKey, columnKey, d);
        String result = formatter.toString();
        formatter.close();
        return result;
    }
 
    /**
     * Tests this label generator for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StandardCategoryItemLabelGenerator)) {
            return false;
        }
        StandardCategoryItemLabelGenerator that 
                = (StandardCategoryItemLabelGenerator) obj;
        if (!this.template.equals(that.template)) {
            return false;
        }
        if (!ObjectUtils.equals(this.itemSelection, that.itemSelection)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return this.template.hashCode();
    }

}
