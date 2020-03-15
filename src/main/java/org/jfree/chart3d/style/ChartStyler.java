/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2020, by Object Refinery Limited.  All rights reserved.
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
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package org.jfree.chart3d.style;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import org.jfree.chart3d.Chart3D;
import org.jfree.chart3d.ChartElement;
import org.jfree.chart3d.ChartElementVisitor;
import org.jfree.chart3d.axis.Axis3D;
import org.jfree.chart3d.axis.CategoryAxis3D;
import org.jfree.chart3d.axis.ValueAxis3D;
import org.jfree.chart3d.marker.CategoryMarker;
import org.jfree.chart3d.marker.Marker;
import org.jfree.chart3d.marker.NumberMarker;
import org.jfree.chart3d.marker.RangeMarker;
import org.jfree.chart3d.plot.CategoryPlot3D;
import org.jfree.chart3d.plot.PiePlot3D;
import org.jfree.chart3d.plot.Plot3D;
import org.jfree.chart3d.plot.XYZPlot;
import org.jfree.chart3d.renderer.Renderer3D;
import org.jfree.chart3d.renderer.category.CategoryRenderer3D;
import org.jfree.chart3d.renderer.xyz.XYZRenderer;
import org.jfree.chart3d.table.TableElement;
import org.jfree.chart3d.table.TableElementStyler;

/**
 * A {@link ChartElementVisitor} that applies a chart style to the elements
 * of a chart.
 * 
 * @since 1.2
 */
public class ChartStyler implements ChartElementVisitor {
    
    /** The chart style. */
    private final ChartStyle style;
    
    /**
     * Creates a new instance.
     * 
     * @param style  the style ({@code null} not permitted). 
     */
    public ChartStyler(ChartStyle style) {
        this.style = style;
    }

    /**
     * Visits a chart element and applies the current style to that element.
     * 
     * @param element  the chart element (never {@code null}). 
     */
    @Override
    public void visit(ChartElement element) {
        if (element instanceof Chart3D) {
            Chart3D chart = (Chart3D) element;
            styleChart(chart);
        }
        if (element instanceof Plot3D) {
            Plot3D plot = (Plot3D) element;
            stylePlot(plot);
        }
        if (element instanceof Axis3D) {
            Axis3D axis = (Axis3D) element;
            styleAxis(axis);
        }
        if (element instanceof Renderer3D) {
            Renderer3D renderer = (Renderer3D) element;
            styleRenderer(renderer);
        }
        if (element instanceof Marker) {
            Marker marker = (Marker) element;
            styleMarker(marker);
        }
    }
    
    /**
     * Applies the current style to a chart.
     * 
     * @param chart  the chart ({@code null} not permitted). 
     */
    protected void styleChart(Chart3D chart) {
        chart.setBackground(this.style.getBackgroundPainter());

        // if the chart title font changed, visit all table elements in the
        // chart title and change the font for any element that is tagged as
        // "CHART_TITLE"
        TableElement chartTitle = chart.getTitle();
        if (chartTitle != null) {
            Map<String, Font> fontChanges = new HashMap<>();
            fontChanges.put("CHART_TITLE", this.style.getTitleFont());
            fontChanges.put("CHART_SUBTITLE", this.style.getSubtitleFont());
            Map<String, Color> bgChanges = new HashMap<>();
            bgChanges.put("CHART_TITLE", this.style.getTitleBackgroundColor());
            bgChanges.put("CHART_SUBTITLE", 
                    this.style.getSubtitleBackgroundColor());
            Map<String, Color> fgChanges = new HashMap<>();
            fgChanges.put("CHART_TITLE", this.style.getTitleColor());
            fgChanges.put("CHART_SUBTITLE", this.style.getSubtitleColor());
            TableElementStyler m1 = new TableElementStyler(fontChanges, 
                    fgChanges, bgChanges);
            chartTitle.receive(m1);
        }
        chart.setChartBoxColor(this.style.getChartBoxColor());
    }
    
    /**
     * Applies the current style to the plot.
     * 
     * @param plot  the plot ({@code null} not permitted). 
     */
    protected void stylePlot(Plot3D plot) {
        if (plot instanceof PiePlot3D) {
            PiePlot3D p = (PiePlot3D) plot;
            p.getSectionLabelFontSource().style(
                    this.style.getSectionLabelFont());
            p.getSectionLabelColorSource().style(
                    this.style.getSectionLabelColor());
            p.getSectionColorSource().style(
                    this.style.getStandardColors());
        }
        if (plot instanceof CategoryPlot3D) {
            CategoryPlot3D p = (CategoryPlot3D) plot;
            
            // gridline paint and stroke for rows, columns and values
            p.setGridlinesVisibleForColumns(
                    this.style.getColumnAxisGridlinesVisible());
            p.setGridlinesVisibleForRows(
                    this.style.getRowAxisGridlinesVisible());
            p.setGridlinesVisibleForValues(
                    this.style.getYAxisGridlinesVisible());
            p.setGridlinePaintForRows(this.style.getGridlineColor());
            p.setGridlinePaintForColumns(this.style.getGridlineColor());
            p.setGridlinePaintForValues(this.style.getGridlineColor());
            p.setGridlineStrokeForColumns(this.style.getGridlineStroke());
            p.setGridlineStrokeForRows(this.style.getGridlineStroke());
            p.setGridlineStrokeForValues(this.style.getGridlineStroke());
        }
        if (plot instanceof XYZPlot) {
            XYZPlot p = (XYZPlot) plot;
            p.setGridlinesVisibleX(this.style.getXAxisGridlinesVisible());
            p.setGridlinesVisibleY(this.style.getYAxisGridlinesVisible());
            p.setGridlinesVisibleZ(this.style.getZAxisGridlinesVisible());
            p.setGridlinePaintX(this.style.getGridlineColor());
            p.setGridlinePaintY(this.style.getGridlineColor());
            p.setGridlinePaintZ(this.style.getGridlineColor());
            p.setGridlineStrokeX(this.style.getGridlineStroke());
            p.setGridlineStrokeY(this.style.getGridlineStroke());
            p.setGridlineStrokeZ(this.style.getGridlineStroke());
        }
    }
    
    /**
     * Applies the current style to the axis.
     * 
     * @param axis  the axis ({@code null} not permitted). 
     */
    protected void styleAxis(Axis3D axis) {
        // axis line visible, stroke and paint TODO
        // tick marks visible, stroke and paint TODO
        axis.setLabelFont(this.style.getAxisLabelFont());
        axis.setLabelColor(this.style.getAxisLabelColor());
        axis.setTickLabelFont(this.style.getAxisTickLabelFont());
        axis.setTickLabelColor(this.style.getAxisTickLabelColor());
        if (axis instanceof CategoryAxis3D) {
            styleCategoryAxis((CategoryAxis3D) axis);
        }
        if (axis instanceof ValueAxis3D) {
            styleValueAxis((ValueAxis3D) axis);
        }   
    }
    
    /**
     * Applies the current style to a category axis.  This implementation does
     * nothing, it is here for overriding.
     * 
     * @param axis  the axis ({@code null} not permitted). 
     */
    protected void styleCategoryAxis(CategoryAxis3D axis) {
        // for overriding
    }
    
    /**
     * Applies the current style to a value axis.  This implementation does
     * nothing, it is here for overriding.
     * 
     * @param axis  the axis ({@code null} not permitted). 
     */
    protected void styleValueAxis(ValueAxis3D axis) {
        // for overriding
    }
    
    /**
     * Applies the current style to the renderer.
     * 
     * @param renderer  the renderer ({@code null} not permitted). 
     */
    protected void styleRenderer(Renderer3D renderer) {
        if (renderer instanceof CategoryRenderer3D) {
            styleCategoryRenderer3D((CategoryRenderer3D) renderer);
        }
        if (renderer instanceof XYZRenderer) {
            styleXYZRenderer((XYZRenderer) renderer);
        }
    }
    
    /**
     * Applies the current style to a category renderer.
     * 
     * @param renderer  the renderer ({@code null} not permitted). 
     */
    protected void styleCategoryRenderer3D(CategoryRenderer3D renderer) {
        renderer.getColorSource().style(this.style.getStandardColors());
    }
    
    /**
     * Applies the current style to an XYZ renderer.
     * 
     * @param renderer  the renderer ({@code null} not permitted). 
     */
    protected void styleXYZRenderer(XYZRenderer renderer) {
        renderer.getColorSource().style(this.style.getStandardColors());
    }
    
    /**
     * Applies the current style to a marker.
     * 
     * @param marker  the marker ({@code null} not permitted). 
     */
    protected void styleMarker(Marker marker) {
        if (marker instanceof CategoryMarker) {
            CategoryMarker cm = (CategoryMarker) marker;
            cm.setFont(this.style.getMarkerLabelFont());
            cm.setLabelColor(this.style.getMarkerLabelColor());
            cm.setLineColor(this.style.getMarkerLineColor());
            cm.setLineStroke(this.style.getMarkerLineStroke());
            cm.setFillColor(this.style.getMarkerFillColor());            
        } else if (marker instanceof NumberMarker) {
            NumberMarker nm = (NumberMarker) marker;
            nm.setFont(this.style.getMarkerLabelFont());
            nm.setLabelColor(this.style.getMarkerLabelColor());
            nm.setLineColor(this.style.getMarkerLineColor());
            nm.setLineStroke(this.style.getMarkerLineStroke());
        } else if (marker instanceof RangeMarker) {
            RangeMarker rm = (RangeMarker) marker;
            rm.setFont(this.style.getMarkerLabelFont());
            rm.setLabelColor(this.style.getMarkerLabelColor());
            rm.setFillColor(this.style.getMarkerFillColor());
            
            rm.getStart().setFont(this.style.getMarkerLabelFont());
            rm.getStart().setLabelColor(this.style.getMarkerLabelColor());
            rm.getStart().setLineColor(this.style.getMarkerLineColor());
            rm.getStart().setLineStroke(this.style.getMarkerLineStroke());
            rm.getEnd().setFont(this.style.getMarkerLabelFont());
            rm.getEnd().setLabelColor(this.style.getMarkerLabelColor());
            rm.getEnd().setLineColor(this.style.getMarkerLineColor());
            rm.getEnd().setLineStroke(this.style.getMarkerLineStroke());   
        }
    }
}
