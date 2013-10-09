/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.legend;

import java.awt.Color;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.util.List;
import com.orsoncharts.data.PieDataset3D;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.plot.PiePlot3D;
import com.orsoncharts.plot.Plot3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.category.CategoryRenderer3D;
import com.orsoncharts.renderer.xyz.XYZRenderer;
import com.orsoncharts.table.FlowElement;
import com.orsoncharts.table.GridElement;
import com.orsoncharts.table.ShapeElement;
import com.orsoncharts.table.TableElement;
import com.orsoncharts.table.TextElement;
import com.orsoncharts.util.ArgChecks;
import java.io.Serializable;

/**
 * The default legend builder.
 */
public final class StandardLegendBuilder implements LegendBuilder, 
        Serializable {

    /**
     * Default constructor.
     */
    public StandardLegendBuilder() {
        
    }
    
    /**
     * Creates and returns a legend (instance of {@link TableElement}) that
     * provides a visual key for the data series in the specified plot.  The
     * plot can be any of the built-in plot types: {@link PiePlot3D}, 
     * {@link CategoryPlot3D} or {@link XYZPlot}.
     * 
     * @param plot  the plot (<code>null</code> not permitted).
     * @return 
     */
    @Override
    public TableElement createLegend(Plot3D plot) {
        ArgChecks.nullNotPermitted(plot, "plot");
        if (plot instanceof PiePlot3D) {
            return createPieLegend((PiePlot3D) plot);
        } else if (plot instanceof CategoryPlot3D) {
            return createCategoryLegend((CategoryPlot3D) plot);
        } else if (plot instanceof XYZPlot) {
            return createXYZLegend((XYZPlot) plot);
        } else {
            return null;
        }
    }
    
    private TableElement createPieLegend(PiePlot3D plot) {
        FlowElement legend = new FlowElement();
        PieDataset3D dataset = plot.getDataset();
        List<Comparable> keys = dataset.getKeys();
        for (Comparable key : keys) {
            Color c = plot.getSectionColor(key);
            legend.addElement(createLegendItem(key.toString(), c));
        }
        return legend;    
    }
    
    private TableElement createCategoryLegend(CategoryPlot3D plot) {
        FlowElement legend = new FlowElement();
        CategoryDataset3D dataset = plot.getDataset();
        List<Comparable> keys = dataset.getSeriesKeys();
        for (Comparable key : keys) {
            CategoryRenderer3D renderer = plot.getRenderer();
            int series = plot.getDataset().getSeriesIndex(key);
            Color c = renderer.getPaintSource().getLegendPaint(series);
            legend.addElement(createLegendItem(key.toString(), c));
        }
        return legend;    
    }
    
    private TableElement createXYZLegend(XYZPlot plot) {
        FlowElement legend = new FlowElement();
        XYZDataset dataset = plot.getDataset();
        List<Comparable> keys = dataset.getSeriesKeys();
        for (Comparable key : keys) {
            XYZRenderer renderer = plot.getRenderer();
            int series = plot.getDataset().getSeriesIndex(key);
            Color c = renderer.getPaintSource().getLegendPaint(series);
            legend.addElement(createLegendItem(key.toString(), c));
        }
        return legend;    
    }

    private TableElement createLegendItem(String text, Paint color) {
        ShapeElement se = new ShapeElement(new Rectangle2D.Double(-6, -4, 12, 8), color);
        TextElement te = new TextElement(text);
        GridElement ge = new GridElement();
        ge.setElement(se, "R1", "C1");
        ge.setElement(te, "R1", "C2");
        return ge;
    }
    
    /**
     * Tests this legend builder for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StandardLegendBuilder)) {
            return false;
        }
        return true;
    }

}
