/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.demo;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.orsoncharts.ChartPanel3D;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.data.xyz.XYZDataItem;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.data.xyz.XYZSeries;
import com.orsoncharts.data.xyz.XYZSeriesCollection;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.xyz.BarXYZRenderer;
import com.orsoncharts.renderer.xyz.StandardXYZColorSource;
import java.awt.Color;

/**
 * A demo of a 3D bar chart.
 */
public class XYZBarChart3DDemo1 extends JFrame {

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public XYZBarChart3DDemo1(String title) {
        super(title);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
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
        XYZDataset dataset = createDataset();
        Chart3D chart = Chart3DFactory.createXYZBarChart("XYZBarChart3DDemo1", 
                "Chart created with Orson Charts", dataset, "X", "Value", "Z");
        XYZPlot plot = (XYZPlot) chart.getPlot();
        BarXYZRenderer renderer = (BarXYZRenderer) plot.getRenderer();
        renderer.setBarXWidth(Math.PI / 20);
        renderer.setBarZWidth(Math.PI / 20);
        renderer.setColorSource(new StandardXYZColorSource(Color.GREEN));
        chart.setViewPoint(ViewPoint3D.createAboveRightViewPoint(40));
        ChartPanel3D chartPanel = new ChartPanel3D(chart);
        content.setChartPanel(chartPanel);
        chartPanel.zoomToFit(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        content.add(new DisplayPanel3D(chartPanel));
        return content;
    }
  
    /**
     * Creates a sample dataset (hard-coded for the purpose of keeping the
     * demo self-contained - in practice you would normally read your data
     * from a file, database or other source).
     * 
     * @return A sample dataset.
     */
    private static XYZDataset createDataset() {
        XYZSeries series1 = new XYZSeries("y = cos(x) * sin(z)");
        for (double x = Math.PI / 2; x < 3 * Math.PI / 2; x += Math.PI / 20) {
            for (double z = Math.PI; z < 2 * Math.PI; z += Math.PI / 20) {
                series1.add(new XYZDataItem(x, Math.cos(x) * Math.sin(z), z));            
            }
        }
        XYZSeriesCollection dataset = new XYZSeriesCollection();
        dataset.add(series1);
        return dataset;
    }

    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        XYZBarChart3DDemo1 app = new XYZBarChart3DDemo1(
                "OrsonCharts: XYZBarChart3DDemo1.java");
        app.pack();
        app.setVisible(true);
    }
}