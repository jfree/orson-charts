/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.legend;

import java.awt.Paint;
import java.awt.Shape;
import java.util.Map;

/**
 * Information about a legend item.
 */
public interface LegendItemInfo {
    
    /**
     * Returns the series key.
     * 
     * @return The series key (never <code>null</code>). 
     */
    Comparable getSeriesKey();
    
    /**
     * Returns the series label that will be displayed in the legend.  Very
     * often this is the same as <code>getSeriesKey().toString()</code>, but 
     * here we have the option to provide some alternative text.
     * 
     * @return The label (never <code>null</code>).
     */
    String getLabel();

    /**
     * Returns a longer description of the series (this could be used in 
     * tooltips, for example).
     * 
     * @return The description (possibly <code>null</code>). 
     */
    String getDescription();
    
    /**
     * Returns the shape used to represent the series in the legend.  This
     * may be <code>null</code>, in which case the legend builder should
     * use some default shape.
     * 
     * @return The shape (possibly <code>null</code>). 
     */
    Shape getShape();

    /**
     * Returns the paint used to represent a series.
     * 
     * @return The paint (never <code>null</code>).
     */
    Paint getPaint();
    
    /**
     * A map containing other properties for the legend item.  Not currently
     * used.
     * 
     * @return A map (never <code>null</code>). 
     */
    Map<Comparable, Object> getProperties();
    
}
