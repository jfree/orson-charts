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

import java.awt.Font;
import java.awt.Paint;
import java.util.HashMap;
import java.util.Map;
import com.orsoncharts.Chart3D;
import com.orsoncharts.ChartElement;
import com.orsoncharts.ChartElementVisitor;
import com.orsoncharts.axis.Axis3D;
import com.orsoncharts.axis.CategoryAxis3D;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.plot.PiePlot3D;
import com.orsoncharts.plot.Plot3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.Renderer3D;
import com.orsoncharts.renderer.category.CategoryRenderer3D;
import com.orsoncharts.renderer.xyz.XYZRenderer;
import com.orsoncharts.table.TableElementStyler;

/**
 * A {@link ChartElementVisitor} that applies a chart style to the elements
 * of a chart.
 * 
 * @since 1.2
 */
public class ChartStyler implements ChartElementVisitor {
    
    /** The chart style. */
    private ChartStyle style;
    
    /**
     * Creates a new instance.
     * 
     * @param style  the style (<code>null</code> not permitted). 
     */
    public ChartStyler(ChartStyle style) {
        this.style = style;
    }

    /**
     * Visits a chart element and applies the current style to that element.
     * 
     * @param element  the chart element (never <code>null</code>). 
     */
    @Override
    public void visit(ChartElement element) {
        // this instanceof checking seems bad, come back to see
        // what we're doing wrong...FIXME
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
    }
    
    /**
     * Applies the current style to a chart.
     * 
     * @param chart  the chart (<code>null</code> not permitted). 
     */
    protected void styleChart(Chart3D chart) {
        chart.setBackground(this.style.getBackgroundPainter());

        // if the chart title font changed, visit all table elements in the
        // chart title and change the font for any element that is tagged as
        // "CHART_TITLE"
        Map<String, Font> fontChanges = new HashMap();
        fontChanges.put("CHART_TITLE", this.style.getTitleFont());
        fontChanges.put("CHART_SUBTITLE", this.style.getSubtitleFont());
        Map<String, Paint> bgChanges = new HashMap<String, Paint>();
        bgChanges.put("CHART_TITLE", this.style.getTitleBackgroundPaint());
        bgChanges.put("CHART_SUBTITLE", this.style.getSubtitleBackgroundPaint());
        Map<String, Paint> fgChanges = new HashMap<String, Paint>();
        fgChanges.put("CHART_TITLE", this.style.getTitlePaint());
        fgChanges.put("CHART_SUBTITLE", this.style.getSubtitlePaint());
        TableElementStyler m1 = new TableElementStyler(fontChanges, fgChanges, bgChanges);
        chart.getTitle().receive(m1);
        chart.setChartBoxColor(this.style.getChartBoxColor());
    }
    
    /**
     * Applies the current style to the plot.
     * 
     * @param plot  the plot (<code>null</code> not permitted). 
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
                    this.style.getXAxisGridlinesVisible());
            p.setGridlinesVisibleForRows(this.style.getZAxisGridlinesVisible());
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
     * @param axis  the axis (<code>null</code> not permitted). 
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
    
    protected void styleCategoryAxis(CategoryAxis3D axis) {
    }
    
    protected void styleValueAxis(ValueAxis3D axis) {
    }
    
    protected void styleRenderer(Renderer3D renderer) {
        if (renderer instanceof CategoryRenderer3D) {
            styleCategoryRenderer3D((CategoryRenderer3D) renderer);
        }
        if (renderer instanceof XYZRenderer) {
            styleXYZRenderer((XYZRenderer) renderer);
        }
    }
    
    protected void styleCategoryRenderer3D(CategoryRenderer3D renderer) {
        renderer.getColorSource().style(this.style.getStandardColors());
    }
    
    protected void styleXYZRenderer(XYZRenderer renderer) {
        renderer.getColorSource().style(this.style.getStandardColors());
    }
}
