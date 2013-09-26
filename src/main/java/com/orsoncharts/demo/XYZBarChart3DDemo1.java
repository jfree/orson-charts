/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.demo;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;

/**
 * A demo of a 3D bar chart.
 */
public class XYZBarChart3DDemo1 extends JFrame {

    ChartPanel3D chartPanel3D;

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
        getContentPane().add(createContent());
    }

    final JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout());
        content.setPreferredSize(new Dimension(600, 400));
        XYZDataset dataset = createDataset();
        Chart3D chart = Chart3DFactory.createXYZBarChart(dataset, "X", "Value", 
                "Z");
        this.chartPanel3D = new ChartPanel3D(chart);
        content.add(new DisplayPanel3D(this.chartPanel3D, true));
        return content;
    }
  
    private XYZDataset createDataset() {
        XYZSeries series1 = new XYZSeries("Series 1");
        for (int x = 0; x < 4; x++) {
            series1.add(new XYZDataItem(x + 0.5, 0.5, Math.random() * 20));            
        }
        XYZSeries series2 = new XYZSeries("Series 2");
        for (int x = 0; x < 4; x++) {
            series2.add(new XYZDataItem(x + 0.5, 1.5, Math.random() * 20));            
        }     
        XYZSeries series3 = new XYZSeries("Series 3");
        for (int x = 0; x < 4; x++) {
            series3.add(new XYZDataItem(x + 0.5, 2.5, Math.random() * 20));            
        }
        XYZSeriesCollection dataset = new XYZSeriesCollection();
        dataset.add(series1);
        dataset.add(series2);
        dataset.add(series3);
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