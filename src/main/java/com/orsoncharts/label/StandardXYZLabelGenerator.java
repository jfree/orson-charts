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

import java.io.Serializable;
import java.util.Formatter;

import com.orsoncharts.data.DataUtils;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.util.ArgChecks;

/**
 * A default implementation of the {@link XYZLabelGenerator} interface.  
 * The implementation uses a {@link java.util.Formatter} instance to generate
 * the labels, which are typically used as the series labels in the chart
 * legend.  Three values are passed to the formatter for possible inclusion
 * in the resulting label: (1) the key for the series, (2) the count for the 
 * number of items in the series (as an <code>Integer</code>) and (3) the total 
 * of the non-<code>NaN</code> values in the series (as a <code>Double</code>).
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 * 
 * @since 1.2
 */
@SuppressWarnings("serial")
public class StandardXYZLabelGenerator implements XYZLabelGenerator, 
        Serializable {

    /** A label template that will display the series key only. */
    public static final String KEY_ONLY_TEMPLATE = "%s";

    /** 
     * A label template that will display the series key followed by the
     * total of the data items for the series (in brackets) with zero decimal
     * places.
     */
    public static final String TOTAL_TEMPLATE = "%s (%3$,.0f)";
    
    /** 
     * A label template that will display the series key followed by the
     * total of the data items for the series (in brackets) with zero decimal
     * places.
     */
    public static final String TOTAL_TEMPLATE_2DP = "%s (%3$,.2f)";

    /**
     * A label template that will display the series key followed by the
     * number of data items for the series (in brackets).
     */
    public static final String COUNT_TEMPLATE = "%s (%2$,d)";
    
    /** The default label template. */
    public static final String DEFAULT_TEMPLATE = KEY_ONLY_TEMPLATE;
    
    /** The label template. */
    private String template;
    
    /**
     * The default constructor.
     */
    public StandardXYZLabelGenerator() {
        this(DEFAULT_TEMPLATE);    
    }
    
    /**
     * Creates a new instance with the specified label template.
     * 
     * @param template  the label template (<code>null</code> not permitted). 
     */
    public StandardXYZLabelGenerator(String template) {
        ArgChecks.nullNotPermitted(template, "template");
        this.template = template;
    }

    /**
     * Generates a series label.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param seriesKey  the series key (<code>null</code> not permitted).
     * 
     * @return The series label (possibly <code>null</code>). 
     */
    @Override
    public String generateSeriesLabel(XYZDataset dataset, 
            Comparable<?> seriesKey) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        Formatter formatter = new Formatter(new StringBuilder());
        int count = dataset.getItemCount(dataset.getSeriesIndex(seriesKey));
        double total = DataUtils.total(dataset, seriesKey);
        formatter.format(this.template, seriesKey, count, total);
        String result = formatter.toString();
        formatter.close();
        return result;
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
        if (!(obj instanceof StandardXYZLabelGenerator)) {
            return false;
        }
        StandardXYZLabelGenerator that = (StandardXYZLabelGenerator) obj;
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
