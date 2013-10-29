/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.legend;

import java.awt.Font;
import com.orsoncharts.Chart3D;
import com.orsoncharts.plot.Plot3D;
import com.orsoncharts.table.TableElement;

/**
 * A legend builder is responsible for creating a legend for a chart.  The API
 * has been kept to a minimum intentionally, so as not to overly constrain 
 * developers that want to implement a custom legend builder.  The 
 * <code>get/setItemFont()</code> methods have been added for convenience
 * because changing the font of the legend item text is a very common 
 * operation.
 * <p>
 * Classes that implement this interface should also implement 
 * <code>java.io.Serializable</code> if you intend to serialize and deserialize 
 * chart objects.
 * 
 * @see Chart3D#setLegendBuilder(LegendBuilder) 
 */
public interface LegendBuilder {

    /**
     * Creates a legend for the specified plot.
     * 
     * @param plot  the plot (<code>null</code> not permitted).
     * 
     * @return A legend.
     */
    TableElement createLegend(Plot3D plot);

    /**
     * Returns the font used for each item within the legend.
     * 
     * @return The font (never <code>null</code>). 
     */
    Font getItemFont();
    
    /**
     * Sets the font used for each item within the legend.
     * 
     * @param font  the font (<code>null</code> not permitted). 
     */
    void setItemFont(Font font);

}
