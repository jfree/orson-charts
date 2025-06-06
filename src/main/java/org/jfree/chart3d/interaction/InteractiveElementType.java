/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-present, by David Gilbert.  All rights reserved.
 * 
 * https://github.com/jfree/orson-charts
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * license is available to sponsors (higher tiers only) of the JFree projects.
 * For details, please see visit:
 *
 * https://github.com/sponsors/jfree
 * 
 */

package org.jfree.chart3d.interaction;

import org.jfree.chart3d.graphics3d.RenderedElement;

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
    
    /** The legend (see also {@link #LEGEND_ITEM}). */
    LEGEND,
    
    /** 
     * An item within a legend (typically representing a series in the chart). 
     */
    LEGEND_ITEM,
    
    /** A marker. */
    MARKER

}
