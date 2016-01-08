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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

import javax.swing.event.EventListenerList;

import com.orsoncharts.ChartBox3D;
import com.orsoncharts.Colors;
import com.orsoncharts.table.RectanglePainter;
import com.orsoncharts.table.StandardRectanglePainter;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.SerialUtils;

/**
 * A standard implementation of the {@link ChartStyle} interface.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 * 
 * @since 1.2
 */
@SuppressWarnings("serial")
public class StandardChartStyle implements ChartStyle, Cloneable, Serializable {

    /** The first font family to try (usually found on Windows). */
    private static final String FONT_FAMILY_1 = "Palatino Linotype";

    /** The second font family to try (usually found on MacOSX). */
    private static final String FONT_FAMILY_2 = "Palatino";
    
    /**
     * Creates a default font with the specified {@code style} and 
     * {@code size}.  The method attempts to use 'Palatino Linotype' 
     * ('Palatino' on MacOSX) but if it is not found it falls back to the
     * {@code Font.SERIF} font family.
     * 
     * @param style  the style (see java.awt.Font).
     * @param size  the size.
     * 
     * @return The font.
     * 
     * @since 1.3
     */
    public static Font createDefaultFont(int style, int size) {
        Font f = new Font(FONT_FAMILY_1, style, size);
        if ("Dialog".equals(f.getFamily())) {
            f = new Font(FONT_FAMILY_2, style, size);
            if ("Dialog".equals(f.getFamily())) {
                f = new Font(Font.SERIF, style, size);
            }
        }
        return f;
    }
    
    /**
     * The default background color for the title and subtitle, and legend
     * header and footer. 
     */
    public static final Color DEFAULT_TEXT_BACKGROUND_COLOR 
            = new Color(255, 255, 255, 100);

    /** The default title font. */
    public static final Font DEFAULT_TITLE_FONT 
            = createDefaultFont(Font.PLAIN, 32);
    
    /** The default subtitle font. */
    public static final Font DEFAULT_SUBTITLE_FONT 
            = createDefaultFont(Font.PLAIN, 18);
 
    /** The default chartbox color. */
    public static final Color DEFAULT_CHARTBOX_COLOR = new Color(255, 255, 255,
            100);
    
    /** The default visibility for gridlines perpendicular to the row-axis. */
    public static final boolean DEFAULT_ROW_GRIDLINES_VISIBLE = false;
    
    /** The default visibility for gridlines perpendicular to the column-axis. */
    public static final boolean DEFAULT_COLUMN_GRIDLINES_VISIBLE = false;

    /** The default visibility for gridlines perpendicular to the x-axis. */
    public static final boolean DEFAULT_X_GRIDLINES_VISIBLE = true;
    
    /** The default visibility for gridlines perpendicular to the y-axis. */
    public static final boolean DEFAULT_Y_GRIDLINES_VISIBLE = true;
    
    /** The default visibility for gridlines perpendicular to the z-axis. */
    public static final boolean DEFAULT_Z_GRIDLINES_VISIBLE = true;
    
    /** The default gridline color. */
    public static final Color DEFAULT_GRIDLINE_COLOR = Color.GRAY;
    
    /** The default gridline stroke. */
    public static final Stroke DEFAULT_GRIDLINE_STROKE = new BasicStroke(0f);
    
    /** The default font for pie section labels. */
    public static final Font DEFAULT_SECTION_LABEL_FONT = createDefaultFont(
            Font.PLAIN, 14);
    
    /** The default color for pie section labels. */
    public static final Color DEFAULT_SECTION_LABEL_COLOR = Color.BLACK;

    /** The default font for axis labels. */
    public static final Font DEFAULT_AXIS_LABEL_FONT 
            = createDefaultFont(Font.BOLD, 12);
    
    /** The default foreground color for axis labels. */
    public static final Color DEFAULT_AXIS_LABEL_COLOR = Color.BLACK;

    /** The default font for axis tick labels. */
    public static final Font DEFAULT_AXIS_TICK_LABEL_FONT 
            = createDefaultFont(Font.PLAIN, 12);

    /** The default foreground color for axis tick labels. */
    public static final Color DEFAULT_AXIS_TICK_LABEL_COLOR = Color.BLACK;

    /** The default font for legend headers. */
    public static final Font DEFAULT_LEGEND_HEADER_FONT = createDefaultFont(
            Font.BOLD, 14);
    
    /** The default foreground color for the legend header if there is one. */
    public static final Color DEFAULT_LEGEND_HEADER_COLOR = Color.BLACK;
    
    /** The default legend item shape. */
    public static final Shape DEFAULT_LEGEND_ITEM_SHAPE 
            = new Rectangle2D.Double(-6, -4, 12, 8);

    /** The default font for legend item text. */
    public static final Font DEFAULT_LEGEND_ITEM_FONT = createDefaultFont(
            Font.PLAIN, 12);

    /** The default legend item color. */
    public static final Color DEFAULT_LEGEND_ITEM_COLOR = Color.BLACK;
    
    /** The default legend item background color. */
    public static final Color DEFAULT_LEGEND_ITEM_BACKGROUND_COLOR 
            = new Color(255, 255, 255, 100);

    /** The default font for legend footers. */
    public static final Font DEFAULT_LEGEND_FOOTER_FONT = createDefaultFont(
            Font.PLAIN, 10);

    /** The default foreground color for the legend footer if there is one. */
    public static final Color DEFAULT_LEGEND_FOOTER_COLOR = Color.BLACK;

    public static final Font DEFAULT_MARKER_LABEL_FONT = createDefaultFont(
            Font.PLAIN, 10);
    
    public static final Color DEFAULT_MARKER_LABEL_COLOR = Color.DARK_GRAY;
    
    public static final Stroke DEFAULT_MARKER_LINE_STROKE = new BasicStroke(
            2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    
    public static final Color DEFAULT_MARKER_LINE_COLOR = Color.DARK_GRAY;
    
    public static final Color DEFAULT_MARKER_FILL_COLOR 
            = new Color(127, 127, 127, 63);

    /** The background painter. */
    private RectanglePainter backgroundPainter;
    
    /** The chart title font. */
    private Font titleFont;
    
    /** The foreground color for the chart title. */
    private Color titleColor;
    
    /** The background color for the chart title. */
    private Color titleBackgroundColor;

    /** The chart subtitle font. */
    private Font subtitleFont;
    
    /** The foreground color for the chart subtitle. */
    private Color subtitleColor;
    
    /** The background color for the chart subtitle. */
    private Color subtitleBackgroundColor;
    
    /** The color for the chart box, if there is one. */
    private Color chartBoxColor;
    
    /** Are gridlines visible for the row-axis? */
    private boolean rowAxisGridlinesVisible;
    
    /** Are gridlines visible for the column-axis? */
    private boolean columnAxisGridlinesVisible;

    /** Are gridlines visible for the x-axis? */
    private boolean xAxisGridlinesVisible;
    
    /** Are gridlines visible for the y-axis? */
    private boolean yAxisGridlinesVisible;
    
    /** Are gridlines visible for the z-axis? */
    private boolean zAxisGridlinesVisible;
    
    /** The gridline color. */
    private Color gridlineColor;
    
    /** The gridline stroke. */
    private transient Stroke gridlineStroke;
    
    /** The font for pie section labels. */
    private Font sectionLabelFont;
    
    /** The foreground color for pie section labels. */
    private Color sectionLabelColor;
    
    /** The standard colors (to color pie sections or data series). */
    private Color[] standardColors;
    
    /** The axis label font. */
    private Font axisLabelFont;
    
    /** The color for the axis label. */
    private Color axisLabelColor;
    
    /** The axis tick label font. */
    private Font axisTickLabelFont;
    
    /** The color used to draw axis tick labels. */
    private Color axisTickLabelColor;
    
    /** The legend header font. */
    private Font legendHeaderFont;
    
    /** The legend header foreground color. */
    private Color legendHeaderColor;
    
    /** The legend header background color. */
    private Color legendHeaderBackgroundColor;
    
    /** The legend item shape. */
    private Shape legendItemShape;

    /** The legend item font. */
    private Font legendItemFont;
    
    /** The legend item color. */
    private Color legendItemColor;
    
    /** The legend item background color. */
    private Color legendItemBackgroundColor;
    
    /** The legend footer font. */
    private Font legendFooterFont;
    
    /** The foreground color for the legend footer if there is one. */
    private Color legendFooterColor;
    
    /** The background color for the legend footer if there is one. */
    private Color legendFooterBackgroundColor;

    /** The font used to draw marker labels. */
    private Font markerLabelFont;
  
    /** The color used to draw marker labels. */
    private Color markerLabelColor;
    
    /** The stroke used to draw marker lines. */
    private transient Stroke markerLineStroke;
    
    /** The color used to draw marker lines. */
    private Color markerLineColor;
    
    /** The color used to fill the band representing the marker range. */
    private Color markerFillColor;
    
    /** Storage for registered change listeners. */
    private transient EventListenerList listenerList;

    /**
     * A flag that controls whether or not the chart will notify listeners
     * of changes (defaults to {@code true}, but sometimes it is useful 
     * to disable this).
     */
    private boolean notify;

    /**
     * Creates a new instance with default attributes.
     */
    public StandardChartStyle() {
        this.backgroundPainter = new StandardRectanglePainter(Color.WHITE);
        this.titleFont = DEFAULT_TITLE_FONT;
        this.titleColor = Color.BLACK;
        this.titleBackgroundColor = DEFAULT_TEXT_BACKGROUND_COLOR;
        this.subtitleColor = Color.BLACK;
        this.subtitleBackgroundColor = DEFAULT_TEXT_BACKGROUND_COLOR;
        this.subtitleFont = DEFAULT_SUBTITLE_FONT;
        this.chartBoxColor = DEFAULT_CHARTBOX_COLOR;
        this.rowAxisGridlinesVisible = DEFAULT_ROW_GRIDLINES_VISIBLE;
        this.columnAxisGridlinesVisible = DEFAULT_COLUMN_GRIDLINES_VISIBLE;
        this.xAxisGridlinesVisible = DEFAULT_X_GRIDLINES_VISIBLE;
        this.yAxisGridlinesVisible = DEFAULT_Y_GRIDLINES_VISIBLE;
        this.zAxisGridlinesVisible = DEFAULT_Z_GRIDLINES_VISIBLE;
        this.gridlineColor = DEFAULT_GRIDLINE_COLOR;
        this.gridlineStroke = DEFAULT_GRIDLINE_STROKE;
        this.sectionLabelFont = DEFAULT_SECTION_LABEL_FONT;
        this.sectionLabelColor = DEFAULT_SECTION_LABEL_COLOR;
        this.standardColors = Colors.getDefaultColors();
        this.axisLabelFont = DEFAULT_AXIS_LABEL_FONT;
        this.axisLabelColor = DEFAULT_AXIS_LABEL_COLOR;
        this.axisTickLabelFont = DEFAULT_AXIS_TICK_LABEL_FONT;
        this.axisTickLabelColor = DEFAULT_AXIS_TICK_LABEL_COLOR;
        this.legendHeaderFont = DEFAULT_LEGEND_HEADER_FONT;
        this.legendHeaderColor = DEFAULT_LEGEND_HEADER_COLOR;
        this.legendHeaderBackgroundColor = DEFAULT_TEXT_BACKGROUND_COLOR;
        this.legendItemShape = DEFAULT_LEGEND_ITEM_SHAPE;
        this.legendItemFont = DEFAULT_LEGEND_ITEM_FONT;
        this.legendItemColor = DEFAULT_LEGEND_ITEM_COLOR;
        this.legendItemBackgroundColor = DEFAULT_LEGEND_ITEM_BACKGROUND_COLOR;
        this.legendFooterFont = DEFAULT_LEGEND_FOOTER_FONT;
        this.legendFooterColor = DEFAULT_LEGEND_FOOTER_COLOR;
        this.legendFooterBackgroundColor = DEFAULT_TEXT_BACKGROUND_COLOR;
        this.markerLabelFont = DEFAULT_MARKER_LABEL_FONT;
        this.markerLabelColor = DEFAULT_MARKER_LABEL_COLOR;
        this.markerLineStroke = DEFAULT_MARKER_LINE_STROKE;
        this.markerLineColor = DEFAULT_MARKER_LINE_COLOR;
        this.markerFillColor = DEFAULT_MARKER_FILL_COLOR;
        this.listenerList = new EventListenerList();
        this.notify = true;
    }
    
    /**
     * A copy constructor that creates a new style that is a copy of an
     * existing style (note that the existing style listeners are not copied).
     * 
     * @param source  the source style to copy ({@code null} not 
     *         permitted).
     */
    public StandardChartStyle(StandardChartStyle source) {
        ArgChecks.nullNotPermitted(source, "source");
        this.backgroundPainter = source.getBackgroundPainter();
        this.titleFont = source.getTitleFont();
        this.titleColor = source.getTitleColor();
        this.titleBackgroundColor = source.getTitleBackgroundColor();
        this.subtitleFont = source.getSubtitleFont();
        this.subtitleColor = source.getSubtitleColor();
        this.subtitleBackgroundColor = source.getSubtitleBackgroundColor();
        this.chartBoxColor = source.getChartBoxColor();
        this.xAxisGridlinesVisible = source.getXAxisGridlinesVisible();
        this.yAxisGridlinesVisible = source.getYAxisGridlinesVisible();
        this.zAxisGridlinesVisible = source.getZAxisGridlinesVisible();
        this.sectionLabelFont = source.getSectionLabelFont();
        this.sectionLabelColor = source.getSectionLabelColor();
        this.standardColors = source.getStandardColors();
        this.gridlineColor = source.getGridlineColor();
        this.gridlineStroke = source.getGridlineStroke();
        this.axisLabelFont = source.getAxisLabelFont();
        this.axisLabelColor = source.getAxisLabelColor();
        this.axisTickLabelFont = source.getAxisTickLabelFont();
        this.axisTickLabelColor = source.getAxisTickLabelColor();
        this.legendHeaderFont = source.getLegendHeaderFont();
        this.legendHeaderColor = source.getLegendHeaderColor();
        this.legendHeaderBackgroundColor 
                = source.getLegendHeaderBackgroundColor();
        this.legendItemShape = source.getLegendItemShape();
        this.legendItemFont = source.getLegendItemFont();
        this.legendItemColor = source.getLegendItemColor();
        this.legendItemBackgroundColor = source.getLegendItemBackgroundColor();
        this.legendFooterFont = source.getLegendFooterFont();
        this.legendFooterColor = source.getLegendFooterColor();
        this.legendFooterBackgroundColor 
                = source.getLegendFooterBackgroundColor();
        this.markerLabelFont = source.getMarkerLabelFont();
        this.markerLabelColor = source.getMarkerLabelColor();
        this.markerLineStroke = source.getMarkerLineStroke();
        this.markerLineColor = source.getMarkerLineColor();
        this.markerFillColor = source.getMarkerFillColor();
        this.listenerList = new EventListenerList();
        this.notify = true;        
    }
    
    /**
     * Returns the background painter.
     * 
     * @return The background painter (never {@code null}). 
     */
    @Override
    public RectanglePainter getBackgroundPainter() {
        return this.backgroundPainter;
    }
    
    /**
     * Sets the background painter.
     * 
     * @param painter  the painter ({@code null} not permitted). 
     */
    public void setBackgroundPainter(RectanglePainter painter) {
        ArgChecks.nullNotPermitted(painter, "painter");
        this.backgroundPainter = painter;
        fireChangeEvent();
    }
    
    /**
     * Returns the chart title font.  The default value is 
     * {@link #DEFAULT_TITLE_FONT}.
     * 
     * @return The chart title font (never {@code null}). 
     */
    @Override
    public Font getTitleFont() {
        return this.titleFont;
    }
    
    /**
     * Sets the font used for the chart title and sends a 
     * {@link ChartStyleChangeEvent} to all registered listeners.
     * 
     * @param font  the font ({@code null} not permitted). 
     */
    public void setTitleFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.titleFont = font;
        fireChangeEvent();
    }
    
    /**
     * Returns the title color.  The default value is {@link Color#BLACK}.
     * 
     * @return The title color (never {@code null}). 
     */
    @Override
    public Color getTitleColor() {
        return this.titleColor;
    }
    
    /**
     * Sets the foreground color for the chart title and sends a 
     * change event to all registered listeners.
     * 
     * @param color  the color ({@code null} not permitted).
     */
    public void setTitleColor(Color color) {
        this.titleColor = color;
        fireChangeEvent();
    }
    
    /**
     * Returns the background color for the title.  The default value is
     * {@link #DEFAULT_TEXT_BACKGROUND_COLOR}.
     * 
     * @return The background color (never {@code null}). 
     */
    @Override
    public Color getTitleBackgroundColor() {
        return this.titleBackgroundColor;
    }
    
    /**
     * Sets the background color for the title and sends a 
     * change event to all registered listeners.
     * 
     * @param color  the color ({@code null} not permitted). 
     */
    public void setTitleBackgroundColor(Color color) {
        this.titleBackgroundColor = color;
        fireChangeEvent();
    }
    
    /**
     * Returns the font used for the chart subtitle.  The default value 
     * is {@link #DEFAULT_SUBTITLE_FONT}.
     * 
     * @return The chart subtitle font (never {@code null}). 
     */
    @Override
    public Font getSubtitleFont() {
        return this.subtitleFont;
    }

    /**
     * Sets the font used for the chart subtitle and sends a 
     * {@link ChartStyleChangeEvent} to all registered listeners.
     * 
     * @param font  the font ({@code null} not permitted). 
     */
    public void setSubtitleFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.subtitleFont = font;
        fireChangeEvent();
    }
   
    /**
     * Returns the color for the chart subtitle.  The default value is 
     * {@link Color#BLACK}.
     * 
     * @return The color (never {@code null}).
     */
    @Override
    public Color getSubtitleColor() {
        return this.subtitleColor;
    }
    
    /**
     * Sets the color for the chart subtitle and sends a 
     * change event to all registered listeners.
     * 
     * @param color  the color ({@code null} not permitted). 
     */
    public void setSubtitleColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.subtitleColor = color;
        fireChangeEvent();
    }
    
    /**
     * Returns the background color for the chart subtitle.  The default value
     * is {@link #DEFAULT_TEXT_BACKGROUND_COLOR}.
     * 
     * @return The background color (never {@code null}). 
     */
    @Override
    public Color getSubtitleBackgroundColor() {
        return this.subtitleBackgroundColor;
    }
    
    /**
     * Sets the background color for the chart subtitle and sends a 
     * change event to all registered listeners.
     * 
     * @param color  the color ({@code null} not permitted). 
     */
    public void setSubtitleBackgroundColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.subtitleBackgroundColor = color;
        fireChangeEvent();
    }

    /**
     * Returns the color used for the {@link ChartBox3D} (for those charts that
     * have one).  The default value is {@link #DEFAULT_CHARTBOX_COLOR}.
     * 
     * @return The color (never {@code null}). 
     */
    @Override
    public Color getChartBoxColor() {
        return this.chartBoxColor;
    }
    
    /**
     * Sets the color used for the chart box and sends a 
     * {@link ChartStyleChangeEvent} to all registered listeners.
     * 
     * @param color  the color ({@code null} not permitted). 
     */
    public void setChartBoxColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.chartBoxColor = color;
        fireChangeEvent();
    }
    
    /**
     * Returns the flag that controls whether or not gridlines are drawn 
     * perpendicular to the column axis in category plots.
     * 
     * @return A boolean. 
     */
    @Override
    public boolean getColumnAxisGridlinesVisible() {
        return this.columnAxisGridlinesVisible;
    }
    
    /**
     * Returns the flag that controls whether or not gridlines are drawn 
     * perpendicular to the row axis in category plots.
     * 
     * @return A boolean. 
     */
    @Override
    public boolean getRowAxisGridlinesVisible() {
        return this.rowAxisGridlinesVisible;
    }

    /**
     * Returns the flag that specifies whether or not gridlines are drawn for
     * the x-axis.  The default value is {@code false}.
     * 
     * @return A boolean. 
     */
    @Override
    public boolean getXAxisGridlinesVisible() {
        return this.xAxisGridlinesVisible;
    }
    
    /**
     * Sets the flag that controls whether or not gridlines are drawn for 
     * the x-axis and sends a {@link ChartStyleChangeEvent}  to all 
     * registered listeners.
     * 
     * @param visible  the new flag value. 
     */
    public void setXAxisGridlinesVisible(boolean visible) {
        this.xAxisGridlinesVisible = visible;
        fireChangeEvent();
    }
    
    /**
     * Returns the flag that specifies whether or not gridlines are drawn for
     * the y-axis.  The default value is {@code true}.
     * 
     * @return A boolean. 
     */
    @Override
    public boolean getYAxisGridlinesVisible() {
        return this.yAxisGridlinesVisible;
    }
    
    /**
     * Sets the flag that controls whether or not gridlines are drawn for 
     * the y-axis and sends a {@link ChartStyleChangeEvent}  to all 
     * registered listeners.
     * 
     * @param visible  the new flag value. 
     */
    public void setYAxisGridlinesVisible(boolean visible) {
        this.yAxisGridlinesVisible = visible;
        fireChangeEvent();
    }

    /**
     * Returns the flag that specifies whether or not gridlines are drawn for
     * the z-axis.  The default value is {@code true}.
     * 
     * @return A boolean. 
     */
    @Override
    public boolean getZAxisGridlinesVisible() {
        return this.zAxisGridlinesVisible;
    }
    
    /**
     * Sets the flag that controls whether or not gridlines are drawn for 
     * the z-axis and sends a {@link ChartStyleChangeEvent}  to all 
     * registered listeners.
     * 
     * @param visible  the new flag value. 
     */
    public void setZAxisGridlinesVisible(boolean visible) {
        this.zAxisGridlinesVisible = visible;
        fireChangeEvent();
    }

    /**
     * Returns the color used for the gridlines.  The default value is
     * {@link #DEFAULT_GRIDLINE_STROKE}.
     * 
     * @return The color used for the gridlines (never {@code null}).
     */
    @Override
    public Color getGridlineColor() {
        return this.gridlineColor;
    }
    
    /**
     * Sets the color for the gridlines and sends a 
     * {@link ChartStyleChangeEvent} to all registered listeners.
     * 
     * @param color  the color ({@code null} not permitted). 
     */
    public void setGridlineColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.gridlineColor = color;
        fireChangeEvent();
    }
    
    /**
     * Returns the stroke used to draw the gridlines.  The default value is
     * {@link #DEFAULT_GRIDLINE_STROKE}.
     * 
     * @return The stroke (never {@code null}).
     */
    @Override
    public Stroke getGridlineStroke() {
        return this.gridlineStroke;
    }

    /**
     * Sets the stroke used for gridlines and sends a 
     * {@link ChartStyleChangeEvent} to all registered listeners.
     * 
     * @param stroke  the stroke ({@code null} not permitted).
     */
    public void setGridlineStroke(Stroke stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.gridlineStroke = stroke;
        fireChangeEvent();
    }
    /**
     * Returns the font used for pie section labels.  The default value is
     * {@link #DEFAULT_SECTION_LABEL_FONT}.
     * 
     * @return The font used for pie section labels (never {@code null}).
     */
    @Override
    public Font getSectionLabelFont() {
        return this.sectionLabelFont;
    }
    
    /**
     * Sets the font used for the pie section labels and sends a 
     * {@link ChartStyleChangeEvent} to all registered listeners.
     * 
     * @param font  the font ({@code null} not permitted). 
     */
    public void setSectionLabelFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.sectionLabelFont = font;
        fireChangeEvent();
    }
    
    /**
     * Returns the color used to display pie section labels.  The default
     * value is {@link #DEFAULT_SECTION_LABEL_COLOR}.
     * 
     * @return The color (never {@code null}). 
     */
    @Override
    public Color getSectionLabelColor() {
        return this.sectionLabelColor;
    }

    /**
     * Sets the color used for the pie section labels and sends a 
     * {@link ChartStyleChangeEvent} to all registered listeners.
     * 
     * @param color  the color ({@code null} not permitted). 
     */
    public void setSectionLabelColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.sectionLabelColor = color;
        fireChangeEvent();
    }
    
    /**
     * Returns the standard colors for the style.  The default value is
     * initialised by calling {@link Colors#getDefaultColors()}.
     * 
     * @return The standard colors (never {@code null}).
     */
    @Override
    public Color[] getStandardColors() {
        return this.standardColors;
    }
    
    /**
     * Sets the standard colors for the chart and sends a 
     * {@link ChartStyleChangeEvent} to all registered listeners.
     * 
     * @param colors  the colors ({@code null} not permitted). 
     */
    public void setStandardColors(Color... colors) {
        this.standardColors = colors;
        fireChangeEvent();
    }
    
    /**
     * Returns the font used for the axis label.  The default value is 
     * {@link #DEFAULT_AXIS_LABEL_FONT}.
     * 
     * @return The font used for the axis label.
     */
    @Override
    public Font getAxisLabelFont() {
        return this.axisLabelFont;
    }
    
    /**
     * Sets the font used for the axis label and sends a 
     * {@link ChartStyleChangeEvent} to all registered listeners.
     * 
     * @param font  the font ({@code null} not permitted). 
     */
    public void setAxisLabelFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.axisLabelFont = font;
        fireChangeEvent();
    }

    /**
     * Returns the foreground color for the axis label (the main label, not
     * the tick labels).  The default value is 
     * {@link #DEFAULT_AXIS_LABEL_COLOR}.
     * 
     * @return The color (never {@code null}). 
     */
    @Override
    public Color getAxisLabelColor() {
        return this.axisLabelColor;
    }
    
    /**
     * Sets the foreground color for the axis label and sends a 
     * {@link ChartStyleChangeEvent} to all registered listeners.
     * 
     * @param color  the color ({@code null} not permitted). 
     */
    public void setAxisLabelColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.axisLabelColor = color;
        fireChangeEvent();
    }
    
    /**
     * Returns the font used for the axis tick labels.  The default value 
     * is {@link #DEFAULT_AXIS_TICK_LABEL_FONT}.
     * 
     * @return The font (never {@code null}). 
     */
    @Override
    public Font getAxisTickLabelFont() {
        return this.axisTickLabelFont;
    }
    
    /**
     * Sets the font used for the axis tick labels and sends a 
     * {@link ChartStyleChangeEvent} to all registered listeners.
     * 
     * @param font  the font ({@code null} not permitted). 
     */
    public void setAxisTickLabelFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.axisTickLabelFont = font;
        fireChangeEvent();
    }    

    /**
     * Returns the color used to draw the tick labels on the axis.  The 
     * default value is {@link #DEFAULT_AXIS_TICK_LABEL_COLOR}.
     * 
     * @return The color (never {@code null}).
     */
    @Override
    public Color getAxisTickLabelColor() {
        return this.axisTickLabelColor;
    }
    
    /**
     * Sets the foreground color for the axis tick labels and sends a 
     * {@link ChartStyleChangeEvent} to all registered listeners.
     * 
     * @param color  the color ({@code null} not permitted). 
     */
    public void setAxisTickLabelColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.axisTickLabelColor = color;
        fireChangeEvent();
    }
    
    /**
     * Returns the font used to display the legend header.  The default 
     * value is {@link #DEFAULT_LEGEND_HEADER_FONT}.
     * 
     * @return The font (never {@code null}). 
     */
    @Override
    public Font getLegendHeaderFont() {
        return this.legendHeaderFont;
    }
    
    /**
     * Sets the legend header font and sends a {@link ChartStyleChangeEvent} to
     * all registered listeners.
     * 
     * @param font  the font ({@code null} not permitted). 
     */
    public void setLegendHeaderFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.legendHeaderFont = font;
        fireChangeEvent();
    }
    
    /**
     * Returns the foreground color for the legend header.  The default value
     * is {@link #DEFAULT_LEGEND_HEADER_COLOR}.
     * 
     * @return The color (never {@code null}). 
     */
    @Override
    public Color getLegendHeaderColor() {
        return this.legendHeaderColor;
    }
    
    /**
     * Sets the foreground color for the legend header and sends a 
     * {@link ChartStyleChangeEvent} to all registered listeners.
     * 
     * @param color  the color ({@code null} not permitted). 
     */
    public void setLegendHeaderColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.legendHeaderColor = color;
        fireChangeEvent();
    }

    /**
     * Returns the background color for the legend header.  The default value
     * is {@link #DEFAULT_TEXT_BACKGROUND_COLOR}.
     * 
     * @return The color (never {@code null}). 
     */
    @Override
    public Color getLegendHeaderBackgroundColor() {
        return this.legendHeaderBackgroundColor;
    }
    
    /**
     * Sets the background color for the legend header and sends a 
     * {@link ChartStyleChangeEvent} to all registered listeners.
     * 
     * @param color  the color ({@code null} not permitted).
     */
    public void setLegendHeaderBackgroundColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.legendHeaderBackgroundColor = color;
        fireChangeEvent();
    }

    /**
     * Returns the standard shape for legend items.  The default value 
     * is {@link #DEFAULT_LEGEND_ITEM_SHAPE}.
     * 
     * @return The legend shape (never {@code null}). 
     */
    @Override
    public Shape getLegendItemShape() {
        return this.legendItemShape;
    }
   
    /**
     * Sets the default shape for legend items and sends a 
     * {@link ChartStyleChangeEvent} to all registered listeners.
     * 
     * @param shape  the shape ({@code null} not permitted). 
     */
    public void setLegendItemShape(Shape shape) {
        ArgChecks.nullNotPermitted(shape, "shape");
        this.legendItemShape = shape;
        fireChangeEvent();
    }
    
    /**
     * Returns the font used for legend item text.  The default value is 
     * {@link #DEFAULT_LEGEND_ITEM_FONT}.
     * 
     * @return The font used for legend item text (never {@code null}). 
     */
    @Override
    public Font getLegendItemFont() {
        return this.legendItemFont;
    }
    
    /**
     * Sets the legend item font and sends a {@link ChartStyleChangeEvent} to
     * all registered listeners.
     * 
     * @param font  the font ({@code null} not permitted). 
     */
    public void setLegendItemFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.legendItemFont = font;
        fireChangeEvent();
    }

    /**
     * Returns the foreground color used for the legend items.  The default
     * value is {@link #DEFAULT_LEGEND_ITEM_COLOR}.
     * 
     * @return The color (never {@code null}). 
     */
    @Override
    public Color getLegendItemColor() {
        return this.legendItemColor;
    }
    
    /**
     * Sets the foreground color used for legend item text and sends a
     * {@link ChartStyleChangeEvent} to all registered listeners.
     * 
     * @param color  the color ({@code null} not permitted).
     */
    public void setLegendItemColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.legendItemColor = color;
        fireChangeEvent();
    }
    
    /**
     * Returns the background color for legend items.  The default value is
     * {@link #DEFAULT_LEGEND_ITEM_BACKGROUND_COLOR}.
     * 
     * @return The color (never {@code null}). 
     */
    @Override
    public Color getLegendItemBackgroundColor() {
        return this.legendItemBackgroundColor;
    }

    /**
     * Sets the background color for legend items and sends a 
     * {@link ChartStyleChangeEvent} to all registered listeners.
     * 
     * @param color  the color ({@code null} not permitted). 
     */
    public void setLegendItemBackgroundColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.legendItemBackgroundColor = color;
        fireChangeEvent();
    }

    /**
     * Returns the font for the legend footer.  The default value is 
     * {@link #DEFAULT_LEGEND_FOOTER_FONT}.
     * 
     * @return The font (never {@code null}). 
     */
    @Override
    public Font getLegendFooterFont() {
        return this.legendFooterFont;
    }

    /**
     * Sets the legend footer font and sends a {@link ChartStyleChangeEvent} to
     * all registered listeners.
     * 
     * @param font  the font ({@code null} not permitted). 
     */
    public void setLegendFooterFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.legendFooterFont = font;
        fireChangeEvent();
    }
    
    /**
     * Returns the foreground color for the legend footer.  The default
     * value is {@link #DEFAULT_LEGEND_FOOTER_COLOR}.
     * 
     * @return The color (never {@code null}). 
     */
    @Override
    public Color getLegendFooterColor() {
        return this.legendFooterColor;
    }
    
    /**
     * Sets the foreground color for the legend footer and sends a 
     * {@link ChartStyleChangeEvent} to all registered listeners.
     * 
     * @param color  the color ({@code null} not permitted). 
     */
    public void setLegendFooterColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.legendFooterColor = color;
        fireChangeEvent();
    }
    
    /**
     * Returns the background color for the legend footer.  The default
     * value is {@link #DEFAULT_TEXT_BACKGROUND_COLOR}.
     * 
     * @return The color (never {@code null}). 
     */
    @Override
    public Color getLegendFooterBackgroundColor() {
        return this.legendFooterBackgroundColor;
    }
    
    /**
     * Sets the background color for the legend footer and sends a 
     * {@link ChartStyleChangeEvent} to all registered listeners.
     * 
     * @param color  the color ({@code null} not permitted).
     */
    public void setLegendFooterBackgroundColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.legendFooterBackgroundColor = color;
        fireChangeEvent();
    }
    
    /**
     * Returns the font used to draw marker labels.
     * 
     * @return The font used to draw marker labels (never {@code null}).
     */
    @Override
    public Font getMarkerLabelFont() {
        return this.markerLabelFont;
    }
    
    /**
     * Sets the marker label font and sends a change event to all registered
     * listeners.
     * 
     * @param font  the font ({@code null} not permitted). 
     */
    public void setMarkerLabelFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.markerLabelFont = font;
        fireChangeEvent();
    }

    /**
     * Returns the color for the marker labels.
     * 
     * @return The color for the marker labels (never {@code null}). 
     */
    @Override
    public Color getMarkerLabelColor() {
        return this.markerLabelColor;
    }
    
    /**
     * Sets the color for the marker labels and sends a change event to all
     * registered listeners.
     * 
     * @param color  the color ({@code null} not permitted). 
     */
    public void setMarkerLabelColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.markerLabelColor = color;
        fireChangeEvent();
    }

    /**
     * Returns the stroke used to draw marker lines.
     * 
     * @return The stroke used to draw marker lines (never {@code null}).
     */
    @Override
    public Stroke getMarkerLineStroke() {
        return this.markerLineStroke;
    }
    
    /**
     * Sets the stroke for the marker lines and sends a change event to all
     * registered listeners.
     * 
     * @param stroke  the stroke ({@code null} not permitted). 
     */
    public void setMarkerLineStroke(Stroke stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.markerLineStroke = stroke;
        fireChangeEvent();
    }

    /**
     * Returns the color used to draw marker lines.
     * 
     * @return The color used to draw marker lines (never {@code null}). 
     */
    @Override
    public Color getMarkerLineColor() {
        return markerLineColor;
    }
    
    /**
     * Sets the marker line color and sends a change event to all registered
     * listeners.
     * 
     * @param color  the color ({@code null} not permitted). 
     */
    public void setMarkerLineColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.markerLineColor = color;
        fireChangeEvent();
    }

    /**
     * Returns the color used to fill the band representing the marker range.
     * 
     * @return The fill color (never {@code null}). 
     */
    @Override
    public Color getMarkerFillColor() {
        return markerFillColor;
    }
    
    /**
     * Sets the marker fill color and sends a change event to all registered
     * listeners.
     * 
     * @param color  the color ({@code null} not permitted).
     */
    public void setMarkerFillColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.markerFillColor = color;
        fireChangeEvent();
    }

    /**
     * Registers a listener to receive notification of changes to the chart.
     * When a style is added to a chart, the chart will register as a listener
     * on the style.
     * 
     * @param listener  the listener. 
     */
    @Override
    public void addChangeListener(ChartStyleChangeListener listener) {
        this.listenerList.add(ChartStyleChangeListener.class, listener);   
    }
  
    /**
     * Deregisters a listener so that it no longer receives notification of
     * changes to the chart.
     * 
     * @param listener  the listener. 
     */
    @Override
    public void removeChangeListener(ChartStyleChangeListener listener) {
        this.listenerList.remove(ChartStyleChangeListener.class, listener);  
    }
  
    /**
     * Notifies all registered listeners that the chart style has been modified.
     *
     * @param event  information about the change event.
     */
    public void notifyListeners(ChartStyleChangeEvent event) {
        // if the 'notify' flag has been switched to false, we don't notify
        // the listeners
        if (!this.notify) {
            return;
        }
        Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChartStyleChangeListener.class) { 
                ((ChartStyleChangeListener) listeners[i + 1]).styleChanged(event);
            }
        }
    }
    /**
     * Returns a flag that controls whether or not change events are sent to
     * registered listeners.
     * 
     * @return A boolean.
     *
     * @see #setNotify(boolean)
     */
    public boolean isNotify() {
        return this.notify;
    }

    /**
     * Sets a flag that controls whether or not listeners receive
     * {@link ChartStyleChangeEvent} notifications.  This can be useful when
     * updating multiple style attributes, you can call setNotify(false) first,
     * update the styles, then call setNotify(true) at the end. 
     *
     * @param notify  a boolean.
     *
     * @see #isNotify()
     */
    public void setNotify(boolean notify) {
        this.notify = notify;
        // if the flag is being set to true, there may be queued up changes...
        if (notify) {
            fireChangeEvent();
        }
    }
  
    /**
     * Sends a {@link ChartStyleChangeEvent} to all registered listeners.
     */
    protected void fireChangeEvent() {
        notifyListeners(new ChartStyleChangeEvent(this, this));
    }

    /**
     * Returns a clone of the chart style (note that the change listeners
     * are not cloned).
     * 
     * @return A clone (never {@code null}). 
     */
    @Override
    public ChartStyle clone() {
        try {
            return (ChartStyle) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(
                    "If we get here, a bug needs fixing.");
        }
    }
    
    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StandardChartStyle)) {
            return false;
        }
        StandardChartStyle that = (StandardChartStyle) obj;
        if (!this.backgroundPainter.equals(that.backgroundPainter)) {
            return false;
        }
        if (!this.titleFont.equals(that.titleFont)) {
            return false;
        }
        if (!this.titleColor.equals(that.titleColor)) {
            return false;
        }
        if (!this.titleBackgroundColor.equals(that.titleBackgroundColor)) {
            return false;
        }
        if (!this.subtitleFont.equals(that.subtitleFont)) {
            return false;
        }
        if (!this.subtitleColor.equals(that.subtitleColor)) {
            return false;
        }
        if (!this.subtitleBackgroundColor.equals(
                that.subtitleBackgroundColor)) {
            return false;
        }
        if (!this.chartBoxColor.equals(that.chartBoxColor)) {
            return false;
        }
        if (this.rowAxisGridlinesVisible!= that.rowAxisGridlinesVisible) {
            return false;
        }
        if (this.columnAxisGridlinesVisible 
                != that.columnAxisGridlinesVisible) {
            return false;
        }
        if (this.xAxisGridlinesVisible != that.xAxisGridlinesVisible) {
            return false;
        }
        if (this.yAxisGridlinesVisible != that.yAxisGridlinesVisible) {
            return false;
        }
        if (this.zAxisGridlinesVisible != that.zAxisGridlinesVisible) {
            return false;
        }
        if (!this.gridlineColor.equals(that.gridlineColor)) {
            return false;
        }
        if (!this.gridlineStroke.equals(that.gridlineStroke)) {
            return false;
        }
        if (!this.sectionLabelFont.equals(that.sectionLabelFont)) {
            return false;
        }
        if (!this.sectionLabelColor.equals(that.sectionLabelColor)) {
            return false;
        }
        if (!Arrays.equals(this.standardColors, that.standardColors)) {
            return false;
        }
        if (!this.axisLabelFont.equals(that.axisLabelFont)) {
            return false;
        }
        if (!this.axisLabelColor.equals(that.axisLabelColor)) {
            return false;
        }
        if (!this.axisTickLabelFont.equals(that.axisTickLabelFont)) {
            return false;
        }
        if (!this.axisTickLabelColor.equals(that.axisTickLabelColor)) {
            return false;
        }
        if (!this.legendHeaderFont.equals(that.legendHeaderFont)) {
            return false;
        }
        if (!this.legendHeaderColor.equals(that.legendHeaderColor)) {
            return false;
        }
        if (!this.legendHeaderBackgroundColor.equals(
                that.legendHeaderBackgroundColor)) {
            return false;
        }
        if (!this.legendItemShape.equals(that.legendItemShape)) {
            return false;
        }
        if (!this.legendItemFont.equals(that.legendItemFont)) {
            return false;
        }
        if (!this.legendItemColor.equals(that.legendItemColor)) {
            return false;
        }
        if (!this.legendItemBackgroundColor.equals(
                that.legendItemBackgroundColor)) {
            return false;
        }
        if (!this.legendFooterFont.equals(that.legendFooterFont)) {
            return false;
        }
        if (!this.legendFooterColor.equals(that.legendFooterColor)) {
            return false;
        }
        if (!this.legendFooterBackgroundColor.equals(
                that.legendFooterBackgroundColor)) {
            return false;
        }
        if (!this.markerLabelFont.equals(that.markerLabelFont)) {
            return false;
        }
        if (!this.markerLabelColor.equals(that.markerLabelColor)) {
            return false;
        }
        if (!this.markerLineColor.equals(that.markerLineColor)) {
            return false;
        }
        if (!this.markerLineStroke.equals(that.markerLineStroke)) {
            return false;
        }
        if (!this.markerFillColor.equals(that.markerFillColor)) {
            return false;
        }
        return true;
    }
    
    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writeStroke(this.gridlineStroke, stream);
        SerialUtils.writeStroke(this.markerLineStroke, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.gridlineStroke = SerialUtils.readStroke(stream);
        this.markerLineStroke = SerialUtils.readStroke(stream);
    }

}
