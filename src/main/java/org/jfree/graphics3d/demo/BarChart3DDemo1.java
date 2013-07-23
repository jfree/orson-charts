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
import org.jfree.chart3d.axis.Axis3D;
import org.jfree.chart3d.axis.Range;
import org.jfree.chart3d.data.DefaultXYZDataset;
import org.jfree.chart3d.plot.XYZPlot;
import org.jfree.chart3d.renderer.BarXYZRenderer;

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

    //World world = new World();

    //ChartBox3D chartBox = new ChartBox3D(10, 10, 10, 0.0, 0.0, 0.0, new Color(0, 0, 255, 200));
    //world.addObject(chartBox.getObject3D());

    Axis3D xAxis = new Axis3D("Category X", new Range(0.0, 10.0));
//    xAxis.setLineStroke(new BasicStroke(3.0f));
//    xAxis.setLinePaint(Color.RED);
    Axis3D yAxis = new Axis3D("Value", new Range(0.0, 20.0));
//    yAxis.setLineStroke(new BasicStroke(3.0f));
//    yAxis.setLinePaint(Color.GREEN);
    Axis3D zAxis = new Axis3D("Category Z", new Range(0.0, 30.0));
//    zAxis.setLineStroke(new BasicStroke(3.0f));
//    zAxis.setLinePaint(Color.BLUE);
    XYZPlot plot = new XYZPlot(xAxis, yAxis, zAxis);
    plot.setDataset(new DefaultXYZDataset(3, 5));
    plot.setRenderer(new BarXYZRenderer());
    this.chartPanel3D = new ChartPanel3D(new JFreeChart3D(plot));

//        World axisWorld = new World();
//        axisWorld.addObject(new Dot3D(-3.0f, -2.0f, -2.0f, Color.YELLOW));
//        axisWorld.addObject(new Dot3D(-3.0f, 2.0f, -2.0f, Color.YELLOW));
//        axisWorld.addObject(new Dot3D(3.0f, -2.0f, -2.0f, Color.YELLOW));
//        axisWorld.addObject(new Dot3D(3.0f, 2.0f, -2.0f, Color.YELLOW));
//
//        axisWorld.addObject(new Dot3D(-3.0f, -2.0f, 2.0f, Color.YELLOW));
//        axisWorld.addObject(new Dot3D(-3.0f, 2.0f, 2.0f, Color.YELLOW));
//        axisWorld.addObject(new Dot3D(3.0f, -2.0f, 2.0f, Color.YELLOW));
//        axisWorld.addObject(new Dot3D(3.0f, 2.0f, 2.0f, Color.YELLOW));
//        this.panel3D.setOverlayWorld(axisWorld);
    content.add(this.chartPanel3D);

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

