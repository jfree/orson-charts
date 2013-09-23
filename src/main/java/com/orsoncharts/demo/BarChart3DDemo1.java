/**
 * (C)opyright 2013, by Object Refinery Limited
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
import com.orsoncharts.axis.CategoryAxis3D;
import com.orsoncharts.axis.NumberAxis3D;
import com.orsoncharts.axis.Range;
import com.orsoncharts.data.XYZDataItem;
import com.orsoncharts.data.XYZDataset;
import com.orsoncharts.data.XYZSeries;
import com.orsoncharts.data.XYZSeriesCollection;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.BarXYZRenderer;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;

/**
 * A demo of a 3D bar chart.
 */
public class BarChart3DDemo1 extends JFrame {
    
  Chart3D chart;

  ChartPanel3D chartPanel3D;

  /**
   * Creates a new test app.
   *
   * @param title  the frame title.
   */
  public BarChart3DDemo1(String title) {
    super(title);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    getContentPane().add(createContent());
  }

  JPanel createContent() {
    JPanel content = new JPanel(new BorderLayout());
    content.setPreferredSize(new Dimension(600, 400));
    XYZDataset dataset = createDataset();
    CategoryAxis3D xAxis = new CategoryAxis3D("Quarter");
    xAxis.setCategoryLabel("Q1", 1.0);
    xAxis.setCategoryLabel("Q2", 2.0);
//    xAxis.setCategoryLabel("Q3", 2.5);
//    xAxis.setCategoryLabel("Q4", 3.5);
    NumberAxis3D yAxis = new NumberAxis3D("Value", new Range(0.0, 20.0));
    CategoryAxis3D zAxis = new CategoryAxis3D("Company");
    zAxis.setRange(0, 3);
    zAxis.setCategoryLabel("Google", 1.0);
    zAxis.setCategoryLabel("Yahoo", 2.0);
 //   zAxis.setCategoryLabel("J.P.Morgan", 2.5);
    XYZPlot plot = new XYZPlot(dataset, xAxis, yAxis, zAxis);
    plot.setDimensions(new Dimension3D(10, 5, 6));
    plot.setRenderer(new BarXYZRenderer());
    this.chartPanel3D = new ChartPanel3D(new Chart3D(plot));
    content.add(new DisplayPanel3D(this.chartPanel3D, true));
    return content;
  }
  
  private XYZDataset createDataset() {
    XYZSeries series1 = new XYZSeries("Series 1");
 //   for (int x = 0; x < 4; x++) {
        for (int x = 0; x < 4; x++) {
            series1.add(new XYZDataItem(x + 0.5, 0.5, Math.random() * 20));            
        }
//    }
    
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
    BarChart3DDemo1 app = new BarChart3DDemo1(
            "OrsonCharts: BarChart3DDemo1.java");
    app.pack();
    app.setVisible(true);
  }
}

