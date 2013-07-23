package org.jfree.graphics3d.demo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart3d.ChartPanel3D;
import org.jfree.chart3d.JFreeChart3D;
import org.jfree.chart3d.plot.XYZPlot;
import org.jfree.chart3d.axis.Axis3D;
import org.jfree.chart3d.axis.Range;
import org.jfree.chart3d.data.DefaultXYZDataset;
import org.jfree.chart3d.renderer.ScatterXYZRenderer;

/**
 * A test app.
 */
public class ScatterPlot3DDemo extends JFrame {

  JFreeChart3D chart;

  ChartPanel3D chartPanel3D;

  /**
   * Creates a new test app.
   *
   * @param title  the frame title.
   */
  public ScatterPlot3DDemo(String title) {
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
    Axis3D xAxis = new Axis3D("X", new Range(0.0, 10.0));
    Axis3D yAxis = new Axis3D("Y", new Range(0.0, 20.0));
    Axis3D zAxis = new Axis3D("Z", new Range(0.0, 30.0));
    XYZPlot plot = new XYZPlot(xAxis, yAxis, zAxis);
    plot.setDataset(new DefaultXYZDataset(3, 100));
    plot.setRenderer(new ScatterXYZRenderer());
    this.chartPanel3D = new ChartPanel3D(new JFreeChart3D(plot));
    content.add(this.chartPanel3D);

    return content;
  }

  /**
   * Starting point for the app.
   *
   * @param args  command line arguments (ignored).
   */
  public static void main(String[] args) {
    ScatterPlot3DDemo app = new ScatterPlot3DDemo("JFreeChart3D : ScatterPlot3DDemo1.java");
    app.pack();
    app.setVisible(true);
  }
}
