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

package com.orsoncharts.legend;

import java.awt.Font;
import com.orsoncharts.Chart3D;
import com.orsoncharts.plot.Plot3D;
import com.orsoncharts.table.TableElement;
import com.orsoncharts.util.Anchor2D;
import com.orsoncharts.util.Orientation;

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
     * Creates a legend for the specified plot.  If this method returns 
     * <code>null</code>, no legend will be displayed.
     * 
     * @param plot  the plot (<code>null</code> not permitted).
     * @param anchor  the anchor (<code>null</code> not permitted).
     * @param orientation  the legend orientation (<code>null</code> not 
     *         permitted).
     * 
     * @return A legend (possibly <code>null</code>).
     * 
     * @since 1.1
     */
    TableElement createLegend(Plot3D plot, Anchor2D anchor, 
            Orientation orientation);

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
