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

/**
 * The type for an interactive element.
 */
public enum InteractiveElementType {

    DATA_ITEM,
    
    TITLE,
    
    SUBTITLE,
    
    GRIDLINE, 
    
    AXIS_LABEL,
    
    CATEGORY_AXIS_TICK_LABEL,
    
    VALUE_AXIS_TICK_LABEL,
    
    /** The section label for a pie chart. */
    SECTION_LABEL,
    
    LEGEND,
    
    LEGEND_ITEM,
    
    MARKER;

    private InteractiveElementType() {
    }

}
