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

package com.orsoncharts.interaction;

import com.orsoncharts.graphics3d.RenderedElement;
import static com.orsoncharts.interaction.InteractiveElementType.LEGEND_ITEM;

/**
 * The type for an interactive chart element.  These values are used when 
 * constructing a {@link RenderedElement}.  For each type, some additional
 * properties will usually be defined:
 * 
 * <ul>
 * <li>CATEGORY_AXIS_TICK_LABEL: 'label' (a string containing the tick label, 
 * and 'axis' (either 'row' or 'column');</li>
 * <li>LEGEND_ITEM: 'series_key' (the series key)</li>
 * </ul>
 * 
 */
public enum InteractiveElementType {

    /** A data item within a chart. */
    DATA_ITEM,
    
    /** The chart title. */
    TITLE,
    
    /** The chart subtitle. */
    SUBTITLE,
    
    /** A gridline in the chart. */
    GRIDLINE, 
    
    /** An axis label (the main label for an axis, not a tick label). */
    AXIS_LABEL,
    
    /** A category axis tick label. */
    CATEGORY_AXIS_TICK_LABEL,
    
    /** A value axis tick label. */
    VALUE_AXIS_TICK_LABEL,
    
    /** The section label for a pie chart. */
    SECTION_LABEL,
    
    /** The legend (see also {@link LEGEND_ITEM}). */
    LEGEND,
    
    /** 
     * An item within a legend (typically representing a series in the chart). 
     */
    LEGEND_ITEM,
    
    /** A marker. */
    MARKER;

    private InteractiveElementType() {
    }

}
