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
 * <code>Double</code>, possibly <code>null</code>) and (3) the calculated 
 * percentage value (as a <code>Double</code>, <code>Double.NaN</code> if the 
 * value is <code>null</code>).
 * 
 * @since 1.2
 */
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
     * be passed to a <code>java.util.Formatter</code> instance when generating
     * labels).  See the class description for an explanation of the values 
     * that are available for use in the template string.
     * 
     * @param template  the template (<code>null</code> not permitted).
     */
    public StandardPieLabelGenerator(String template) {
        ArgChecks.nullNotPermitted(template, "template");
        this.template = template;
    }
    
    /**
     * Generates the label for one section in a pie chart.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return The label (never <code>null</code> for this implementation). 
     */
    @Override
    public String generateLabel(PieDataset3D dataset, Comparable<?> key) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        ArgChecks.nullNotPermitted(key, "key");
        Formatter formatter = new Formatter(new StringBuilder());
        Number value = dataset.getValue(key);
        Number percent = Double.NaN;
        if (value != null) {
            double total = DataUtils.total(dataset);
            percent = Double.valueOf(100.0 * value.doubleValue() / total);
        }
        formatter.format(this.template, key, value, percent);
        return formatter.toString();
    }
    
    /**
     * Tests this label generator for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
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
