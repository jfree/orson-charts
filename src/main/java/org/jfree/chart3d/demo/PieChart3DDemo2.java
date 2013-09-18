/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;

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
public class PieChart3DDemo2 extends JFrame implements ActionListener {

  JFreeChart3D chart;

  ChartPanel3D chartPanel3D;

  /**
   * Creates a new test app.
   *
   * @param title  the frame title.
   */
  public PieChart3DDemo2(String title) {
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
    this.chart = new JFreeChart3D(plot);
    this.chartPanel3D = new ChartPanel3D(this.chart);
    content.add(new DisplayPanel3D(this.chartPanel3D, true));
    JButton button = new JButton("Change the Data");
    button.addActionListener(this);
    content.add(button, BorderLayout.SOUTH);
    return content;
  }

  PieDataset3D createDataset() {
    DefaultPieDataset3D dataset = new DefaultPieDataset3D();
    dataset.add("United States", new Double(Math.random() * 30));
    dataset.add("France", new Double(Math.random() * 20));
    dataset.add("New Zealand", new Double(Math.random() * 12));
    dataset.add("United Kingdom", new Double(Math.random() * 43));
    return dataset; 
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    PieDataset3D dataset = createDataset();
    PiePlot3D plot = (PiePlot3D) this.chart.getPlot();
    plot.setDataset(dataset);
  }

  /**
   * Starting point for the app.
   *
   * @param args  command line arguments (ignored).
   */
  public static void main(String[] args) {
    PieChart3DDemo2 app = new PieChart3DDemo2("JFreeChart3D: PieChart3DDemo2.java");
    app.pack();
    app.setVisible(true);
  }

}

