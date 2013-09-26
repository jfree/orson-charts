/* =============
 * OrsonCharts3D
 * =============
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts;

import com.orsoncharts.axis.Axis3D;
import com.orsoncharts.axis.CategoryAxis3D;
import com.orsoncharts.axis.DefaultCategoryAxis3D;
import com.orsoncharts.axis.NumberAxis3D;
import com.orsoncharts.axis.Range;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.data.CategoryDataset3D;
import com.orsoncharts.data.PieDataset3D;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.plot.PiePlot3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.BarXYZRenderer;
import com.orsoncharts.renderer.ScatterXYZRenderer;
import com.orsoncharts.renderer.XYZRenderer;
import com.orsoncharts.renderer.category.AreaRenderer3D;
import com.orsoncharts.renderer.category.BarRenderer3D;
import com.orsoncharts.renderer.category.CategoryRenderer3D;
import com.orsoncharts.renderer.category.LineRenderer3D;
import com.orsoncharts.renderer.category.StackedBarRenderer3D;

/**
 * Utility methods for constructing common chart types.
 */
public class Chart3DFactory {
    
    /**
     * Private constructor prevents instantiation which is unnecessary.
     */
    private Chart3DFactory() {
        // no need to instantiate this ever
    }
    
    /**
     * Creates a pie chart in 3D.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return A pie chart. 
     */
    public static Chart3D createPieChart(PieDataset3D dataset) {
        PiePlot3D plot = new PiePlot3D(dataset);
        return new Chart3D(plot);
    }
    
    /**
     * Creates a bar chart in 3D.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param rowAxisLabel  the row axis label.
     * @param columnAxisLabel  the column axis label.
     * @param valueAxisLabel  the value axis label.
     * 
     * @return A bar chart. 
     */
    public static Chart3D createBarChart(CategoryDataset3D dataset,
            String rowAxisLabel, String columnAxisLabel, 
            String valueAxisLabel) {
        CategoryAxis3D rowAxis = new DefaultCategoryAxis3D(rowAxisLabel);
        CategoryAxis3D columnAxis = new DefaultCategoryAxis3D(columnAxisLabel);
        Axis3D valueAxis = new NumberAxis3D(valueAxisLabel, 
                new Range(0.0, 1.0));
        CategoryRenderer3D renderer = new BarRenderer3D();
        CategoryPlot3D plot = new CategoryPlot3D(dataset, renderer, rowAxis, 
                columnAxis, valueAxis);
        return new Chart3D(plot);
    }
    
    /**
     * Creates a stacked bar chart in 3D.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param rowAxisLabel  the row axis label.
     * @param columnAxisLabel  the column axis label.
     * @param valueAxisLabel  the value axis label.
     * 
     * @return A stacked bar chart. 
     */
    public static Chart3D createStackedBarChart(CategoryDataset3D dataset,
            String rowAxisLabel, String columnAxisLabel, 
            String valueAxisLabel) {
        CategoryAxis3D rowAxis = new DefaultCategoryAxis3D(rowAxisLabel);
        CategoryAxis3D columnAxis = new DefaultCategoryAxis3D(columnAxisLabel);
        Axis3D valueAxis = new NumberAxis3D(valueAxisLabel, 
                new Range(0.0, 1.0));
        CategoryRenderer3D renderer = new StackedBarRenderer3D();
        CategoryPlot3D plot = new CategoryPlot3D(dataset, renderer, rowAxis, 
                columnAxis, valueAxis);
        return new Chart3D(plot);
    }
    
    /**
     * Creates an area chart in 3D.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param rowAxisLabel  the row axis label.
     * @param columnAxisLabel  the column axis label.
     * @param valueAxisLabel  the value axis label.
     * 
     * @return An area chart. 
     */
    public static Chart3D createAreaChart(CategoryDataset3D dataset,
            String rowAxisLabel, String columnAxisLabel, 
            String valueAxisLabel) {
        CategoryAxis3D rowAxis = new DefaultCategoryAxis3D(rowAxisLabel);
        CategoryAxis3D columnAxis = new DefaultCategoryAxis3D(columnAxisLabel);
        Axis3D valueAxis = new NumberAxis3D(valueAxisLabel, 
                new Range(0.0, 1.0));
        CategoryRenderer3D renderer = new AreaRenderer3D();
        CategoryPlot3D plot = new CategoryPlot3D(dataset, renderer, rowAxis, 
                columnAxis, valueAxis);
        return new Chart3D(plot);
    }
    
    /**
     * Creates a line chart in 3D.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param rowAxisLabel  the row axis label.
     * @param columnAxisLabel  the column axis label.
     * @param valueAxisLabel  the value axis label.
     * 
     * @return A line chart. 
     */
    public static Chart3D createLineChart(CategoryDataset3D dataset,
            String rowAxisLabel, String columnAxisLabel, 
            String valueAxisLabel) {
        CategoryAxis3D rowAxis = new DefaultCategoryAxis3D(rowAxisLabel);
        CategoryAxis3D columnAxis = new DefaultCategoryAxis3D(columnAxisLabel);
        Axis3D valueAxis = new NumberAxis3D(valueAxisLabel, 
                new Range(0.0, 1.0));
        CategoryRenderer3D renderer = new LineRenderer3D();
        CategoryPlot3D plot = new CategoryPlot3D(dataset, renderer, rowAxis, 
                columnAxis, valueAxis);
        return new Chart3D(plot);
    }
    
    /**
     * Creates a scatter plot of <code>(x, y, z)</code> values.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param xAxisLabel  the x-axis label.
     * @param yAxisLabel  the y-axis label.
     * @param zAxisLabel  the z-axis label.
     * 
     * @return The chart. 
     */
    public static Chart3D createScatterPlot(XYZDataset dataset, 
            String xAxisLabel, String yAxisLabel, String zAxisLabel) {
        ValueAxis3D xAxis = new NumberAxis3D(xAxisLabel);
        ValueAxis3D yAxis = new NumberAxis3D(yAxisLabel);
        ValueAxis3D zAxis = new NumberAxis3D(zAxisLabel);
        XYZRenderer renderer = new ScatterXYZRenderer();
        XYZPlot plot = new XYZPlot(dataset, renderer, xAxis, yAxis, zAxis);
        return new Chart3D(plot);
    }
    
    /**
     * Creates a bar chart based on <code>(x, y, z)</code> values.  You should
     * only use this for special cases, it is usually easier to create 3D
     * bar charts using the {@link #createBarChart(com.orsoncharts.data.CategoryDataset3D, java.lang.String, java.lang.String, java.lang.String)}
     * method.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param xAxisLabel  the x-axis label.
     * @param yAxisLabel  the y-axis label.
     * @param zAxisLabel  the z-axis label.
     * 
     * @return The chart. 
     */
    public static Chart3D createXYZBarChart(XYZDataset dataset, 
            String xAxisLabel, String yAxisLabel, String zAxisLabel) {
        ValueAxis3D xAxis = new NumberAxis3D(xAxisLabel);
        ValueAxis3D yAxis = new NumberAxis3D(yAxisLabel);
        ValueAxis3D zAxis = new NumberAxis3D(zAxisLabel);
        XYZRenderer renderer = new BarXYZRenderer();
        XYZPlot plot = new XYZPlot(dataset, renderer, xAxis, yAxis, zAxis);
        return new Chart3D(plot);
    }

}
