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

import com.orsoncharts.data.DataUtils;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.util.ArgChecks;

/**
 * A default implementation of the {@link CategoryLabelGenerator} interface.  
 * The implementation uses a {@link java.util.Formatter} instance to generate
 * the labels.  Three values are passed to the formatter: (1) the key for
 * the series, row or column, (2) the count for the number of 
 * non-{@code null} items in the series, row or column (as an 
 * {@code Integer}) and (3) the total of the non-{@code null} values 
 * (as a {@code Double}).
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 * 
 * @since 1.2
 */
@SuppressWarnings("serial")
public class StandardCategoryLabelGenerator<S extends Comparable<S>, R extends Comparable<R>, C extends Comparable<C>> 
        implements CategoryLabelGenerator<S, R, C>, Serializable {

    /** 
     * A template string that will show the series, row or column key only.
     * 
     * @since 1.2
     */
    public static final String KEY_ONLY_TEMPLATE = "%s";
    
    /**
     * A template string that will show the key followed by the data total
     * (for the series, row or column) in brackets, with zero decimal places.
     */
    public static final String TOTAL_TEMPLATE = "%s (%3$,.0f)";
    
    /**
     * A template string that will show the key followed by the data total
     * (for the series, row or column) in brackets, with two decimal places.
     */
    public static final String TOTAL_TEMPLATE_2DP = "%s (%3$,.2f)";

    /**
     * The default template string (used in the default constructor, it is
     * equivalent to {@link #KEY_ONLY_TEMPLATE}).
     * 
     * @since 1.2
     */
    public static final String DEFAULT_TEMPLATE = KEY_ONLY_TEMPLATE;
        
    /** The template. */
    private String template;
    
    /**
     * The default constructor.
     */
    public StandardCategoryLabelGenerator() {
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
    public StandardCategoryLabelGenerator(String template) {
        ArgChecks.nullNotPermitted(template, "template");
        this.template = template;
    }
    
    /**
     * Generates the label for one series in a category chart.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * @param seriesKey  the key ({@code null} not permitted).
     * 
     * @return The label (never {@code null} for this implementation). 
     */
    @Override
    public String generateSeriesLabel(CategoryDataset3D<S, R, C> dataset, 
            S seriesKey) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        Formatter formatter = new Formatter(new StringBuilder());
        int count = DataUtils.count(dataset, seriesKey);
        double total = DataUtils.total(dataset, seriesKey);
        formatter.format(this.template, seriesKey, count, total);
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    
    /**
     * Generates a label for one row in a {@link CategoryDataset3D}.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * @param rowKey  the key ({@code null} not permitted).
     * 
     * @return The row label (possibly {@code null}).
     */
    @Override
    public String generateRowLabel(CategoryDataset3D<S, R, C> dataset, 
            R rowKey) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        ArgChecks.nullNotPermitted(rowKey, "rowKey");
        Formatter formatter = new Formatter(new StringBuilder());
        int count = DataUtils.countForRow(dataset, rowKey);
        double total = DataUtils.totalForRow(dataset, rowKey);
        formatter.format(this.template, rowKey, count, total);
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    
    /**
     * Generates a label for one column in a {@link CategoryDataset3D}.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * @param columnKey  the key ({@code null} not permitted).
     * 
     * @return The column label (possibly {@code null}).
     */
    @Override
    public String generateColumnLabel(CategoryDataset3D<S, R, C> dataset, 
            C columnKey) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        ArgChecks.nullNotPermitted(columnKey, "columnKey");
        Formatter formatter = new Formatter(new StringBuilder());
        int count = DataUtils.countForColumn(dataset, columnKey);
        double total = DataUtils.totalForColumn(dataset, columnKey);
        formatter.format(this.template, columnKey, count, total);
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
        if (!(obj instanceof StandardCategoryLabelGenerator)) {
            return false;
        }
        StandardCategoryLabelGenerator that 
                = (StandardCategoryLabelGenerator) obj;
        if (!this.template.equals(that.template)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return this.template.hashCode();
    }

}
