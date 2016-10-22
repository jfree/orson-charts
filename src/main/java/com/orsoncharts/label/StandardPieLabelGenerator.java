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
import com.orsoncharts.data.PieDataset3D;
import com.orsoncharts.util.ArgChecks;

/**
 * A default implementation of the {@link PieLabelGenerator} interface.  The
 * implementation uses a {@link java.util.Formatter} instance to generate
 * the labels.  Three values are passed to the formatter: (1) the key for
 * the current pie section, (2) the data value for the section (as a 
 * {@code Double}, possibly {@code null}) and (3) the calculated 
 * percentage value (as a {@code Double}, {@code Double.NaN} if the 
 * value is {@code null}).
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 * 
 * @since 1.2
 */
@SuppressWarnings("serial")
public class StandardPieLabelGenerator implements PieLabelGenerator,
        Serializable {

    /** 
     * A template string that will show the section key only.
     * 
     * @since 1.2
     */
    public static final String KEY_ONLY_TEMPLATE = "%s";

    /** 
     * A template string that will show the section key followed by the 
     * percentage in brackets (with zero decimal places).
     * 
     * @since 1.2
     */
    public static final String PERCENT_TEMPLATE = "%s (%3$,.0f%%)";

    /** 
     * A template string that will show the section key followed by the 
     * percentage in brackets (with two decimal places precision).
     * 
     * @since 1.2
     */
    public static final String PERCENT_TEMPLATE_2DP = "%s (%3$,.2f%%)";

    /** 
     * A template string that will show the section key followed by the 
     * value in brackets (with zero decimal places precision).
     * 
     * @since 1.2
     */
    public static final String VALUE_TEMPLATE = "%s (%2$,.0f)";

    /** 
     * A template string that will show the section key followed by the 
     * value in brackets (with two decimal places precision).
     * 
     * @since 1.2
     */
    public static final String VALUE_TEMPLATE_2DP = "%s (%2$,.2f)";
    
    /**
     * The default template string (used in the default constructor, it is
     * equivalent to {@link #PERCENT_TEMPLATE}).
     * 
     * @since 1.2
     */
    public static final String DEFAULT_TEMPLATE = PERCENT_TEMPLATE;
    
    /** The label template. */
    private String template;

    /**
     * The default constructor, uses {@link #DEFAULT_TEMPLATE} for the 
     * template string.
     */
    public StandardPieLabelGenerator() {
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
    public StandardPieLabelGenerator(String template) {
        ArgChecks.nullNotPermitted(template, "template");
        this.template = template;
    }
    
    /**
     * Generates the label for one section in a pie chart.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * @param key  the key ({@code null} not permitted).
     * 
     * @return The label (never {@code null} for this implementation). 
     */
    @Override @SuppressWarnings("unchecked")
    public String generateLabel(PieDataset3D dataset, Comparable<?> key) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        ArgChecks.nullNotPermitted(key, "key");
        Formatter formatter = new Formatter(new StringBuilder());
        Number value = (Number) dataset.getValue(key);
        Number percent = Double.NaN;
        if (value != null) {
            double total = DataUtils.total(dataset);
            percent = Double.valueOf(100.0 * value.doubleValue() / total);
        }
        formatter.format(this.template, key, value, percent);
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
        if (!(obj instanceof StandardPieLabelGenerator)) {
            return false;
        }
        StandardPieLabelGenerator that = (StandardPieLabelGenerator) obj;
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
