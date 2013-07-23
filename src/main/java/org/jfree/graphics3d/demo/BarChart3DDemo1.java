/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.graphics3d.demo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart3d.ChartPanel3D;
import org.jfree.chart3d.JFreeChart3D;
import org.jfree.chart3d.axis.NumberAxis3D;
import org.jfree.chart3d.axis.Range;
import org.jfree.chart3d.data.DefaultXYZDataset;
import org.jfree.chart3d.data.XYZDataset;
import org.jfree.chart3d.plot.XYZPlot;
import org.jfree.chart3d.renderer.BarXYZRenderer;
import org.jfree.graphics3d.swing.DisplayPanel3D;

/**
 *
 * @author dgilbert
 */
public class BarChart3DDemo1 extends JFrame {
    
  JFreeChart3D chart;

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
    XYZDataset dataset = new DefaultXYZDataset(3, 5);
    NumberAxis3D xAxis = new NumberAxis3D("Category X", new Range(0.0, 10.0));
    NumberAxis3D yAxis = new NumberAxis3D("Value", new Range(0.0, 20.0));
    NumberAxis3D zAxis = new NumberAxis3D("Category Z", new Range(0.0, 30.0));
    XYZPlot plot = new XYZPlot(dataset, xAxis, yAxis, zAxis);
    plot.setRenderer(new BarXYZRenderer());
    this.chartPanel3D = new ChartPanel3D(new JFreeChart3D(plot));
    content.add(new DisplayPanel3D(this.chartPanel3D, true));
    return content;
  }

  /**
   * Starting point for the app.
   *
   * @param args  command line arguments (ignored).
   */
  public static void main(String[] args) {
    BarChart3DDemo1 app = new BarChart3DDemo1("JFreeChart3D: BarChart3DDemo1.java");
    app.pack();
    app.setVisible(true);
  }
}

