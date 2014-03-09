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

package com.orsoncharts.style;

import java.util.EventObject;

import com.orsoncharts.util.ArgChecks;

/**
 * An event that signals a change to a {@link ChartStyle}.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 * 
 * @since 1.2
 */
@SuppressWarnings("serial")
public class ChartStyleChangeEvent extends EventObject {

    private ChartStyle style;
  
    /**
     * Creates a new event.
     * 
     * @param style  the style (<code>null</code> not permitted). 
     */
    public ChartStyleChangeEvent(ChartStyle style) {
        this(style, style);
    }
    
    /**
     * Creates a new event.
     * 
     * @param source  the source.
     * @param style  the style (<code>null</code> not permitted).
     */
    public ChartStyleChangeEvent(Object source, ChartStyle style) {
        super(source);
        ArgChecks.nullNotPermitted(style, "style");
        this.style = style;
    }
  
    /**
     * Returns the chart style that this event is associated with.
     * 
     * @return The style (never <code>null</code>). 
     */
    public ChartStyle getChartStyle () {
        return this.style;
    }
    
}
