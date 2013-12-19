/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */
package com.orsoncharts.demo;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.orsoncharts.ChartPanel3D;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Range;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.data.function.Function3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.legend.LegendAnchor;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.RainbowScale;
import com.orsoncharts.renderer.xyz.SurfaceRenderer;
import com.orsoncharts.util.Orientation;

/**
 * A demo of a surface chart.
 */
public class SurfaceRendererDemo2 extends JFrame {

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public SurfaceRendererDemo2(String title) {
        super(title);
        addWindowListener(new ExitOnClose());
        getContentPane().add(createDemoPanel());
    }

    /**
     * Returns a panel containing the content for the demo.  This method is
     * used across all the individual demo applications to allow aggregation 
     * into a single "umbrella" demo (OrsonChartsDemo).
     * 
     * @return A panel containing the content for the demo.
     */
    public static JPanel createDemoPanel() {
        DemoPanel content = new DemoPanel(new BorderLayout());
        content.setPreferredSize(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        Function3D function = new Function3D() {

            @Override
            public double getValue(double x, double z) {
                return Math.sin(x * x + z * z);
            }
            
        };
        
        Chart3D chart = Chart3DFactory.createSurfaceChart(
                "SurfaceRendererDemo2", 
                "y = sin(x^2 + z^2)", 
                function, "X", "Y", "Z");
        XYZPlot plot = (XYZPlot) chart.getPlot();
        plot.setDimensions(new Dimension3D(10, 5, 10));
        ValueAxis3D xAxis = plot.getXAxis();
        xAxis.setRange(-2, 2);
        ValueAxis3D zAxis = plot.getZAxis();
        zAxis.setRange(-2, 2);
        SurfaceRenderer renderer = (SurfaceRenderer) plot.getRenderer();
        renderer.setColorScale(new RainbowScale(new Range(-1.0, 1.0)));
        renderer.setXSamples(50);
        renderer.setZSamples(50);
        renderer.setDrawFaceOutlines(false);
        chart.setLegendPosition(LegendAnchor.TOP_RIGHT, Orientation.VERTICAL);
        chart.setAntiAlias(false);
        ChartPanel3D chartPanel = new ChartPanel3D(chart);
        chartPanel.zoomToFit(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        content.setChartPanel(chartPanel);
        content.add(new DisplayPanel3D(chartPanel));
        return content;
    }
    
    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        SurfaceRendererDemo2 app = new SurfaceRendererDemo2(
                "OrsonCharts: SurfaceRendererDemo2.java");
        app.pack();
        app.setVisible(true);
    }
}


