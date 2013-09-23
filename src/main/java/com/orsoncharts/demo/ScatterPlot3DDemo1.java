package com.orsoncharts.demo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import com.orsoncharts.ChartPanel3D;
import com.orsoncharts.Chart3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.axis.NumberAxis3D;
import com.orsoncharts.axis.Range;
import com.orsoncharts.data.DefaultXYZDataset;
import com.orsoncharts.data.XYZDataset;
import com.orsoncharts.renderer.ScatterXYZRenderer;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;

/**
 * A test app.
 */
public class ScatterPlot3DDemo1 extends JFrame {

  Chart3D chart;

  ChartPanel3D chartPanel3D;

  /**
   * Creates a new test app.
   *
   * @param title  the frame title.
   */
  public ScatterPlot3DDemo1(String title) {
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
    XYZDataset dataset = new DefaultXYZDataset(3, 100);
    NumberAxis3D xAxis = new NumberAxis3D("X", new Range(0.0, 10.0));
    NumberAxis3D yAxis = new NumberAxis3D("Y", new Range(0.0, 10.0));
    NumberAxis3D zAxis = new NumberAxis3D("Z", new Range(0.0, 10.0));
    XYZPlot plot = new XYZPlot(dataset, xAxis, yAxis, zAxis);
    plot.setRenderer(new ScatterXYZRenderer());
    this.chartPanel3D = new ChartPanel3D(new Chart3D(plot));
    content.add(new DisplayPanel3D(this.chartPanel3D, true));
    return content;
  }

  /**
   * Starting point for the app.
   *
   * @param args  command line arguments (ignored).
   */
  public static void main(String[] args) {
    ScatterPlot3DDemo1 app = new ScatterPlot3DDemo1(
            "OrsonCharts : ScatterPlot3DDemo1.java");
    app.pack();
    app.setVisible(true);
  }
}
