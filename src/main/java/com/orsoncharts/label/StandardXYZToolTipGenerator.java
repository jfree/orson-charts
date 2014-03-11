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

import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.util.ArgChecks;

/**
 * A default implementation of the {@link XYZToolTipGenerator} interface.  
 * The implementation uses a {@link java.util.Formatter} instance to generate
 * the tool tips.  Four values are passed to the formatter for possible 
 * inclusion in the resulting tool tip text: (1) the key for the series, 
 * (2) the x-value (3) the y-value and (4) the z-value.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 * 
 * @since 1.3
 */
@SuppressWarnings("serial")
public class StandardXYZToolTipGenerator implements XYZToolTipGenerator, 
        Serializable {

    /** 
     * A label template that will display the series key followed by the
     * total of the data items for the series (in brackets) with zero decimal
     * places.
     */
    public static final String KEY_AND_COORDS_3DP_TEMPLATE 
            = "%s (%2$.3f, %3$.3f, %4$.3f)";
    
    /** A label template that will display the series key only. */
    public static final String COORDS_3DP_TEMPLATE = "(%2$.3f, %3$.3f, %4$.3f)";

    /** The default label template. */
    public static final String DEFAULT_TEMPLATE = KEY_AND_COORDS_3DP_TEMPLATE;
            
    /** The label template. */
    private String template;
    
    /**
     * The default constructor.
     */
    public StandardXYZToolTipGenerator() {
        this(DEFAULT_TEMPLATE);    
    }
    
    /**
     * Creates a new instance with the specified label template.
     * 
     * @param template  the label template (<code>null</code> not permitted). 
     */
    public StandardXYZToolTipGenerator(String template) {
        ArgChecks.nullNotPermitted(template, "template");
        this.template = template;
    }

    /**
     * Generates a tool tip for the specified data item.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param seriesKey  the series key (<code>null</code> not permitted).
     * 
     * @return The series label (possibly <code>null</code>). 
     */
    @Override
    public String generateToolTipText(XYZDataset dataset, 
            Comparable<?> seriesKey, int itemIndex) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        int seriesIndex = dataset.getSeriesIndex(seriesKey);
        Formatter formatter = new Formatter(new StringBuilder());
        double x = dataset.getX(seriesIndex, itemIndex);
        double y = dataset.getY(seriesIndex, itemIndex);
        double z = dataset.getZ(seriesIndex, itemIndex);
        formatter.format(this.template, seriesKey, x, y, z);
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
        if (!(obj instanceof StandardXYZToolTipGenerator)) {
            return false;
        }
        StandardXYZToolTipGenerator that = (StandardXYZToolTipGenerator) obj;
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
