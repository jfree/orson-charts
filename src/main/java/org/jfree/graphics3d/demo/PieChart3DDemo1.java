/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.graphics3d.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart3d.ChartPanel3D;
import org.jfree.chart3d.JFreeChart3D;
import org.jfree.chart3d.data.DefaultPieDataset3D;
import org.jfree.chart3d.data.PieDataset3D;
import org.jfree.chart3d.plot.PiePlot3D;
import org.jfree.graphics3d.swing.DisplayPanel3D;

/**
 * A test app.
 */
public class PieChart3DDemo1 extends JFrame {

  JFreeChart3D chart;

  ChartPanel3D chartPanel3D;

  /**
   * Creates a new test app.
   *
   * @param title  the frame title.
   */
  public PieChart3DDemo1(String title) {
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
    content.setPreferredSize(new Dimension(720, 400));
    PiePlot3D plot = new PiePlot3D(createDataset());
    plot.setSectionColor("United States", new Color(0x1A9641));
    plot.setSectionColor("France", new Color(0xA6D96A));
    plot.setSectionColor("New Zealand", new Color(0xFDAE61));
    plot.setSectionColor("United Kingdom", new Color(0xFFFFBF));
    this.chartPanel3D = new ChartPanel3D(new JFreeChart3D(plot));
    content.add(new DisplayPanel3D(this.chartPanel3D, true));
    return content;
  }

  PieDataset3D createDataset() {
    DefaultPieDataset3D dataset = new DefaultPieDataset3D();
    dataset.add("United States", new Double(30.0));
    dataset.add("France", new Double(20.0));
    dataset.add("New Zealand", new Double(12.0));
    dataset.add("United Kingdom", new Double(43.3));
    return dataset; 
  }
  /**
   * Starting point for the app.
   *
   * @param args  command line arguments (ignored).
   */
  public static void main(String[] args) {
    PieChart3DDemo1 app = new PieChart3DDemo1("JFreeChart3D: PieChart3DDemo1.java");
    app.pack();
    app.setVisible(true);
  }
}

