/* ============
 * Orson Charts 
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts;

import com.orsoncharts.axis.CategoryAxis3D;
import com.orsoncharts.axis.StandardCategoryAxis3D;
import com.orsoncharts.axis.NumberAxis3D;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.PieDataset3D;
import com.orsoncharts.data.function.Function3D;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.data.xyz.XYZSeriesCollection;
import com.orsoncharts.legend.ColorScaleLegendBuilder;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.plot.PiePlot3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.xyz.BarXYZRenderer;
import com.orsoncharts.renderer.xyz.XYZRenderer;
import com.orsoncharts.renderer.category.AreaRenderer3D;
import com.orsoncharts.renderer.category.BarRenderer3D;
import com.orsoncharts.renderer.category.CategoryRenderer3D;
import com.orsoncharts.renderer.category.LineRenderer3D;
import com.orsoncharts.renderer.category.StackedBarRenderer3D;
import com.orsoncharts.renderer.xyz.ScatterXYZRenderer;
import com.orsoncharts.renderer.xyz.SurfaceRenderer;

/**
 * Utility methods for constructing common chart types.  Charts can be 
 * assembled piece-wise, but usually it is simpler to use the methods in this
 * class then customise the resulting chart as necessary.
 */
public class Chart3DFactory {
    
    /**
     * Private constructor prevents instantiation which is unnecessary.
     */
    private Chart3DFactory() {
        // no need to instantiate this ever
    }
    
    /**
     * Creates and returns a pie chart based on the supplied dataset.  The 
     * chart returned by this method will be constructed using a  
     * {@link PiePlot3D} instance (so it is safe to cast the result of
     * <code>chart.getPlot()</code>).
     * <br><br>
     * For reference, here is a sample pie chart:
     * <div>
     * <object id="ABC" data="../../doc-files/PieChart3DDemo1.svg"  
     * type="image/svg+xml" width="500" height="359"> 
     * </object>
     * </div>
     * 
     * @param title  the chart title (<code>null</code> permitted).
     * @param subtitle  the chart subtitle (<code>null</code> permitted).
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return A pie chart (never <code>null</code>). 
     */
    public static Chart3D createPieChart(String title, String subtitle, 
            PieDataset3D dataset) {
        PiePlot3D plot = new PiePlot3D(dataset);
        return new Chart3D(title, subtitle, plot);
    }
    
    /**
     * Creates and returns a bar chart based on the supplied dataset. The chart
     * returned by this method will be constructed with a 
     * {@link CategoryPlot3D} using a {@link BarRenderer3D} (so it is
     * safe to cast the plot and/or renderer to customise attributes that are
     * specific to those subclasses).
     * <br><br>
     * For reference, here is a sample bar chart:
     * <div>
     * <object id="ABC" data="../../doc-files/BarChart3DDemo1.svg"  
     * type="image/svg+xml" width="500" height="359"> 
     * </object>
     * </div>
     * 
     * @param title  the chart title (<code>null</code> permitted).
     * @param subtitle  the chart subtitle (<code>null</code> permitted).
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param rowAxisLabel  the row axis label (<code>null</code> permitted).
     * @param columnAxisLabel  the column axis label (<code>null</code> 
     *     permitted).
     * @param valueAxisLabel  the value axis label (<code>null</code> 
     *     permitted).
     * 
     * @return A bar chart (never <code>null</code>). 
     */
    public static Chart3D createBarChart(String title, String subtitle,
            CategoryDataset3D dataset, String rowAxisLabel, 
            String columnAxisLabel, String valueAxisLabel) {
        CategoryAxis3D rowAxis = new StandardCategoryAxis3D(rowAxisLabel);
        CategoryAxis3D columnAxis = new StandardCategoryAxis3D(columnAxisLabel);
        ValueAxis3D valueAxis = new NumberAxis3D(valueAxisLabel, 
                new Range(0.0, 1.0));
        CategoryRenderer3D renderer = new BarRenderer3D();
        CategoryPlot3D plot = new CategoryPlot3D(dataset, renderer, 
                rowAxis, columnAxis, valueAxis);
        return new Chart3D(title, subtitle, plot);
    }
    
    /**
     * Creates and returns a stacked bar chart based on the supplied dataset.
     * The chart returned by this method will be constructed with a 
     * {@link CategoryPlot3D} using a {@link StackedBarRenderer3D} (so it is
     * safe to cast the plot and/or renderer to customise attributes that
     * are specific to those subclasses).
     * <br><br>
     * For reference, here is a sample stacked bar chart:
     * <div>
     * <object id="ABC" data="../../doc-files/StackedBarChart3DDemo1.svg"  
     * type="image/svg+xml" width="500" height="359"> 
     * </object>
     * </div>
     * 
     * @param title  the chart title (<code>null</code> permitted).
     * @param subtitle  the chart subtitle (<code>null</code> permitted).
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param rowAxisLabel  the row axis label (<code>null</code> permitted).
     * @param columnAxisLabel  the column axis label (<code>null</code> 
     *     permitted).
     * @param valueAxisLabel  the value axis label (<code>null</code> 
     *     permitted).
     * 
     * @return A stacked bar chart (never <code>null</code>). 
     */
    public static Chart3D createStackedBarChart(String title, String subtitle,
            CategoryDataset3D dataset, String rowAxisLabel, 
            String columnAxisLabel, String valueAxisLabel) {
        CategoryAxis3D rowAxis = new StandardCategoryAxis3D(rowAxisLabel);
        CategoryAxis3D columnAxis = new StandardCategoryAxis3D(columnAxisLabel);
        ValueAxis3D valueAxis = new NumberAxis3D(valueAxisLabel, 
                new Range(0.0, 1.0));
        CategoryRenderer3D renderer = new StackedBarRenderer3D();
        CategoryPlot3D plot = new CategoryPlot3D(dataset, renderer, rowAxis, 
                columnAxis, valueAxis);
        return new Chart3D(title, subtitle, plot);
    }
    
    /**
     * Creates and returns an area chart based on the supplied dataset.  The 
     * chart returned by this method will be constructed with a 
     * {@link CategoryPlot3D} using an {@link AreaRenderer3D} (so it is safe 
     * to cast the plot and/or renderer to customise attributes that are 
     * specific to those subclasses).
     * <br><br>
     * For reference, here is a sample area chart:
     * <div>
     * <object id="ABC" data="../../doc-files/AreaChart3DDemo1.svg"  
     * type="image/svg+xml" width="500" height="359"> 
     * </object>
     * </div>
     * 
     * @param title  the chart title (<code>null</code> permitted).
     * @param subtitle  the chart subtitle (<code>null</code> permitted).
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param rowAxisLabel  the row axis label (<code>null</code> permitted).
     * @param columnAxisLabel  the column axis label (<code>null</code> 
     *     permitted).
     * @param valueAxisLabel  the value axis label (<code>null</code> 
     *     permitted).
     * 
     * @return An area chart (never <code>null</code>). 
     */
    public static Chart3D createAreaChart(String title, String subtitle,
            CategoryDataset3D dataset, String rowAxisLabel, 
            String columnAxisLabel, String valueAxisLabel) {
        
        CategoryAxis3D rowAxis = new StandardCategoryAxis3D(rowAxisLabel);
        StandardCategoryAxis3D columnAxis 
                = new StandardCategoryAxis3D(columnAxisLabel);
        columnAxis.setFirstCategoryHalfWidth(true);
        columnAxis.setLastCategoryHalfWidth(true);
        ValueAxis3D valueAxis = new NumberAxis3D(valueAxisLabel, 
                new Range(0.0, 1.0));
        CategoryRenderer3D renderer = new AreaRenderer3D();
        CategoryPlot3D plot = new CategoryPlot3D(dataset, renderer, rowAxis, 
                columnAxis, valueAxis);
        return new Chart3D(title, subtitle, plot);
    }
    
    /**
     * Creates and returns a line chart based on the supplied dataset.  The 
     * chart returned by this method will be constructed with a 
     * {@link CategoryPlot3D} using a {@link LineRenderer3D} (so it is safe
     * to cast the plot and/or renderer to customise attributes that are
     * specific to those subclasses).
     * <br><br>
     * For reference, here is a sample line chart:
     * <div>
     * <object id="ABC" data="../../doc-files/LineChart3DDemo1.svg"  
     * type="image/svg+xml" width="500" height="359"> 
     * </object>
     * </div>
     * 
     * @param title  the chart title (<code>null</code> permitted).
     * @param subtitle  the chart subtitle (<code>null</code> permitted).
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param rowAxisLabel  the row axis label (<code>null</code> permitted).
     * @param columnAxisLabel  the column axis label (<code>null</code> 
     *     permitted).
     * @param valueAxisLabel  the value axis label (<code>null</code> 
     *     permitted).
     * 
     * @return A line chart (never <code>null</code>).
     */
    public static Chart3D createLineChart(String title, String subtitle,
            CategoryDataset3D dataset, String rowAxisLabel, 
            String columnAxisLabel, String valueAxisLabel) {
        CategoryAxis3D rowAxis = new StandardCategoryAxis3D(rowAxisLabel);
        StandardCategoryAxis3D columnAxis 
                = new StandardCategoryAxis3D(columnAxisLabel);
        columnAxis.setFirstCategoryHalfWidth(true);
        columnAxis.setLastCategoryHalfWidth(true);
        ValueAxis3D valueAxis = new NumberAxis3D(valueAxisLabel, 
                new Range(0.0, 1.0));
        CategoryRenderer3D renderer = new LineRenderer3D();
        CategoryPlot3D plot = new CategoryPlot3D(dataset, renderer, rowAxis, 
                columnAxis, valueAxis);
        return new Chart3D(title, subtitle, plot);
    }
    
    /**
     * Creates and returns a scatter plot based on the supplied dataset 
     * (containing one or more series of <code>(x, y, z)</code> values).  The 
     * chart returned by this method will be constructed with an 
     * {@link XYZPlot} using a {@link ScatterXYZRenderer}  (so it is safe
     * to cast the plot and/or renderer to customise attributes that are
     * specific to those subclasses).
     * <br><br>
     * For reference, here is a sample scatter chart:
     * <div>
     * <object id="ABC" data="../../doc-files/ScatterPlot3DDemo1.svg"  
     * type="image/svg+xml" width="564" height="351"> 
     * </object>
     * </div>
     * 
     * @param title  the chart title (<code>null</code> permitted).
     * @param subtitle  the chart subtitle (<code>null</code> permitted).
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param xAxisLabel  the x-axis label (<code>null</code> permitted).
     * @param yAxisLabel  the y-axis label (<code>null</code> permitted).
     * @param zAxisLabel  the z-axis label (<code>null</code> permitted).
     * 
     * @return The chart. 
     */
    public static Chart3D createScatterChart(String title, String subtitle, 
            XYZDataset dataset, String xAxisLabel, String yAxisLabel, 
            String zAxisLabel) {
        ValueAxis3D xAxis = new NumberAxis3D(xAxisLabel);
        ValueAxis3D yAxis = new NumberAxis3D(yAxisLabel);
        ValueAxis3D zAxis = new NumberAxis3D(zAxisLabel);
        XYZRenderer renderer = new ScatterXYZRenderer();
        XYZPlot plot = new XYZPlot(dataset, renderer, xAxis, yAxis, zAxis);
        return new Chart3D(title, subtitle, plot);
    }
    
    /**
     * Creates a surface chart for the specified function.
     * <br><br>
     * For reference, here is a sample surface chart:
     * <div>
     * <object id="SurfaceRenderer3DDemo2" data="../../doc-files/SurfaceRenderer3DDemo2.svg"  
     * type="image/svg+xml" width="562" height="408"> 
     * </object>
     * </div>
     * 
     * @param title  the chart title (<code>null</code> permitted).
     * @param subtitle  the chart subtitle (<code>null</code> permitted).
     * @param function  the function (<code>null</code> not permitted).
     * @param xAxisLabel  the x-axis label (<code>null</code> permitted).
     * @param yAxisLabel  the y-axis label (<code>null</code> permitted).
     * @param zAxisLabel  the z-axis label (<code>null</code> permitted).
     * 
     * @return The chart.
     * 
     * @since 1.1
     */
    public static Chart3D createSurfaceChart(String title, String subtitle, 
            Function3D function, String xAxisLabel, String yAxisLabel, 
            String zAxisLabel) {
        ValueAxis3D xAxis = new NumberAxis3D(xAxisLabel);
        ValueAxis3D yAxis = new NumberAxis3D(yAxisLabel);
        ValueAxis3D zAxis = new NumberAxis3D(zAxisLabel);
        XYZRenderer renderer = new SurfaceRenderer(function);
        // we pass an empty dataset because the plot must have a non-null
        // dataset, but the renderer never looks at it...
        XYZPlot plot = new XYZPlot(new XYZSeriesCollection(), renderer, xAxis, 
                yAxis, zAxis);
        
        Chart3D chart = new Chart3D(title, subtitle, plot);
        chart.setLegendBuilder(new ColorScaleLegendBuilder());
        return chart;
    }
    
    /**
     * Creates and returns a bar chart based on the supplied dataset (this is 
     * for special cases, most general cases will be covered by the 
     * {@link #createBarChart(String, String, CategoryDataset3D, String, String, String) }
     * method). The chart returned by this method will be constructed with an 
     * {@link XYZPlot} using a {@link BarXYZRenderer}  (so it is safe
     * to cast the plot and/or renderer to customise attributes that are
     * specific to those subclasses).
     * <br><br>
     * For reference, here is a sample XYZ bar chart:
     * <div>
     * <object id="ABC" data="../../doc-files/XYZBarChart3DDemo1.svg"  
     * type="image/svg+xml" width="500" height="359"> 
     * </object>
     * </div>
     * 
     * @param title  the chart title (<code>null</code> permitted).
     * @param subtitle  the chart subtitle (<code>null</code> permitted).
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param xAxisLabel  the x-axis label (<code>null</code> permitted).
     * @param yAxisLabel  the y-axis label (<code>null</code> permitted).
     * @param zAxisLabel  the z-axis label (<code>null</code> permitted).
     * 
     * @return The chart. 
     */
    public static Chart3D createXYZBarChart(String title, String subtitle, 
            XYZDataset dataset, String xAxisLabel, String yAxisLabel, 
            String zAxisLabel) {
        ValueAxis3D xAxis = new NumberAxis3D(xAxisLabel);
        ValueAxis3D yAxis = new NumberAxis3D(yAxisLabel);
        ValueAxis3D zAxis = new NumberAxis3D(zAxisLabel);
        XYZRenderer renderer = new BarXYZRenderer();
        XYZPlot plot = new XYZPlot(dataset, renderer, xAxis, yAxis, zAxis);
        return new Chart3D(title, subtitle, plot);
    }

}
