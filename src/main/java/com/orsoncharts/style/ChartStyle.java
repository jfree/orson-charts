/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
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
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package com.orsoncharts.style;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.Stroke;
import com.orsoncharts.Chart3D;
import com.orsoncharts.table.RectanglePainter;

/**
 * A chart style provides styling attributes for a chart.  To apply a style
 * to a chart, use the 
 * {@link Chart3D#setStyle(com.orsoncharts.style.ChartStyle)} method.  See the 
 * {@link ChartStyles} class for predefined styles.
 * 
 * @since 1.2
 */
public interface ChartStyle {
    
    /**
     * Returns the painter that fills the background for the chart.
     * 
     * @return The painter (never {@code null}). 
     */
    RectanglePainter getBackgroundPainter();
    
    /**
     * Returns the title font.
     * 
     * @return The title font (never {@code null}). 
     */
    Font getTitleFont();
    
    /**
     * Returns the foreground color for the chart title.
     * 
     * @return The foreground color (never {@code null}). 
     */
    Color getTitleColor();
    
    /**
     * Returns the background color for the chart title.
     * 
     * @return The background color for the chart title (never 
     *     {@code null}).
     */
    Color getTitleBackgroundColor();
    
    /**
     * Returns the subtitle font. 
     * 
     * @return The subtitle font (never {@code null}). 
     */
    Font getSubtitleFont();

    /**
     * Returns the foreground color for the chart subtitle.
     * 
     * @return The foreground color (never {@code null}). 
     */
    Color getSubtitleColor();
    
    /**
     * Returns the background color for the chart subtitle.
     * 
     * @return The background color (never {@code null}). 
     */
    Color getSubtitleBackgroundColor();
    
    /**
     * Returns the color for the chart box, if any.
     * 
     * @return The color for the chart box (never {@code null}). 
     */
    Color getChartBoxColor();
    
    /**
     * Returns the flag that controls whether or not gridlines are drawn 
     * perpendicular to the column axis in category plots.
     * 
     * @return A boolean. 
     */
    boolean getColumnAxisGridlinesVisible();
    
    /**
     * Returns the flag that controls whether or not gridlines are drawn 
     * perpendicular to the row axis in category plots.
     * 
     * @return A boolean. 
     */
    boolean getRowAxisGridlinesVisible();

    /**
     * Returns the flag that controls whether or not gridlines are drawn 
     * perpendicular to the x-axis (or column axis).
     * 
     * @return A boolean. 
     */
    boolean getXAxisGridlinesVisible();
    
    /**
     * Returns the flag that controls whether or not gridlines are drawn 
     * perpendicular to the y-axis (or value axis).
     * 
     * @return A boolean. 
     */
    boolean getYAxisGridlinesVisible();
    
    /**
     * Returns the flag that controls whether or not gridlines are drawn 
     * perpendicular to the z-axis (or row axis).
     * 
     * @return A boolean. 
     */
    boolean getZAxisGridlinesVisible();
    
    /**
     * Returns the color used to draw the gridlines.
     * 
     * @return The color (never {@code null}). 
     */
    Color getGridlineColor();
    
    /**
     * Returns the stroke used to draw the gridlines.
     * 
     * @return The stroke (never {@code null}).
     */
    Stroke getGridlineStroke();
    
    /**
     * Returns the font used for pie section labels.
     * 
     * @return The pie section label font (never {@code null}).
     */
    Font getSectionLabelFont();
    
    /**
     * Returns the foreground color used for pie section labels.
     * 
     * @return The pie section label color (never {@code null}). 
     */
    Color getSectionLabelColor();
    
    /**
     * Returns the standard colors for the style.
     * 
     * @return The standard colors (never {@code null}).
     */
    Color[] getStandardColors();
    
    /**
     * Returns the axis label font.  The axis label is usually a description
     * of what the axis represents.
     * 
     * @return The axis label font (never {@code null}). 
     * 
     * @see #getAxisTickLabelFont() 
     */
    Font getAxisLabelFont();
    
    /**
     * Returns the foreground color for axis labels.
     * 
     * @return The foreground color (never {@code null}). 
     */
    Color getAxisLabelColor();
    
    /**
     * Returns the axis tick label font.
     * 
     * @return The axis tick label font (never {@code null}).
     * 
     * @see #getAxisLabelFont() 
     */
    Font getAxisTickLabelFont();
    
    /**
     * Returns the color used to draw the tick labels on the axis.
     * 
     * @return The color (never {@code null}).
     */
    Color getAxisTickLabelColor();
    
    /**
     * Returns the legend header font.
     * 
     * @return The legend header font (never {@code null}). 
     */
    Font getLegendHeaderFont();
   
    /**
     * Returns the foreground color for the legend header if there is one.
     * 
     * @return The color (never {@code null}). 
     */
    Color getLegendHeaderColor();
    
    /**
     * Returns the background color for the legend header if there is one.
     * 
     * @return The color (never {@code null}). 
     */
    Color getLegendHeaderBackgroundColor();
    
    /**
     * Returns the standard shape for legend items.
     * 
     * @return The legend shape (never {@code null}). 
     */
    Shape getLegendItemShape();
    
    /**
     * Returns the legend item font.
     * 
     * @return The legend item font (never {@code null}). 
     */
    Font getLegendItemFont();
    
    /**
     * Returns the foreground color for legend items.
     * 
     * @return The foreground color (never {@code null}).
     */
    Color getLegendItemColor();
    
    /**
     * Returns the background color for legend items.
     * 
     * @return The background color (never {@code null}). 
     */
    Color getLegendItemBackgroundColor();
    
    /**
     * Returns the legend footer font.
     * 
     * @return The legend footer font (never {@code null}). 
     */
    Font getLegendFooterFont();
    
    /**
     * Returns the color used for the legend footer text (if any).
     * 
     * @return The color (never {@code null}). 
     */
    Color getLegendFooterColor();
    
    /**
     * Returns the color used for the background of legend footer text (if any).
     * 
     * @return The color (never {@code null}). 
     */
    Color getLegendFooterBackgroundColor();
    
    /**
     * Returns the font used to draw marker labels.
     * 
     * @return The font used to draw marker labels (never {@code null}).
     */
    Font getMarkerLabelFont();
    
    /**
     * Returns the color for the marker labels.
     * 
     * @return The color for the marker labels (never {@code null}). 
     */
    Color getMarkerLabelColor();

    /**
     * Returns the stroke used to draw marker lines.
     * 
     * @return The stroke used to draw marker lines (never {@code null}).
     */
    Stroke getMarkerLineStroke();
    
    /**
     * Returns the color used to draw marker lines.
     * 
     * @return The color used to draw marker lines (never {@code null}). 
     */
    Color getMarkerLineColor();
    
    /**
     * Returns the color used to fill the band representing the marker range.
     * 
     * @return The fill color (never {@code null}). 
     */
    Color getMarkerFillColor();

    /**
     * Returns a clone of the chart style.
     * 
     * @return A clone (never {@code null}). 
     */
    ChartStyle clone();
    
    /**
     * Registers a listener to receive notification of changes to the chart
     * style.  Typically the chart registers with its style, and applies the
     * style changes when it receives notification of any change.
     * 
     * @param listener  the listener ({@code null} not permitted). 
     */
    void addChangeListener(ChartStyleChangeListener listener);
    
    /**
     * Deregisters a listener so that it no longer receives notification of
     * chart changes.
     * 
     * @param listener  the listener ({@code null} not permitted). 
     */
    void removeChangeListener(ChartStyleChangeListener listener);

}
