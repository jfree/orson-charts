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

import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.util.ArgChecks;

/**
 * A default implementation of the {@link CategoryToolTipGenerator} interface.  
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
public class StandardCategoryToolTipGenerator 
        implements CategoryToolTipGenerator, Serializable {

    /** 
     * A template string that will show the series, row and column keys plus
     * the data value.
     * 
     * @since 1.3
     */
    public static final String KEY_AND_VALUES_TEMPLATE = "%s %s %s = %4$.3f";
    
    /**
     * The default template string (used in the default constructor, it is
     * equivalent to {@link #KEY_ONLY_TEMPLATE}).
     * 
     * @since 1.2
     */
    public static final String DEFAULT_TEMPLATE = KEY_AND_VALUES_TEMPLATE;
        
    /** The template. */
    private String template;
    
    /**
     * The default constructor.
     */
    public StandardCategoryToolTipGenerator() {
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
    public StandardCategoryToolTipGenerator(String template) {
        ArgChecks.nullNotPermitted(template, "template");
        this.template = template;
    }
    
    /**
     * Generates the tool tip for one data item in a category chart.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param seriesKey  the key (<code>null</code> not permitted).
     * 
     * @return The label (never <code>null</code> for this implementation). 
     */
    @Override
    public String generateToolTipText(CategoryDataset3D dataset, 
            Comparable<?> seriesKey, Comparable<?> rowKey, 
            Comparable<?> columnKey) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        ArgChecks.nullNotPermitted(seriesKey, "seriesKey");
        ArgChecks.nullNotPermitted(rowKey, "rowKey");
        ArgChecks.nullNotPermitted(columnKey, "columnKey");
        Formatter formatter = new Formatter(new StringBuilder());
        Number value = dataset.getValue(seriesKey, rowKey, columnKey);
        formatter.format(this.template, seriesKey, rowKey, columnKey, value);
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
        if (!(obj instanceof StandardCategoryToolTipGenerator)) {
            return false;
        }
        StandardCategoryToolTipGenerator that 
                = (StandardCategoryToolTipGenerator) obj;
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
