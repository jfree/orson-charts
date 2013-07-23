/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jfree.chart3d.axis.Axis3D;
import org.jfree.chart3d.axis.TextAnchor;
import org.jfree.chart3d.axis.TextUtils;
import org.jfree.chart3d.plot.PiePlot3D;
import org.jfree.chart3d.plot.XYZPlot;
import org.jfree.graphics3d.Face;
import org.jfree.graphics3d.Object3D;
import org.jfree.graphics3d.Panel3D;
import org.jfree.graphics3d.Tools2D;
import org.jfree.graphics3d.World;

/**
 * A panel designed to display a JFreeChart3D.  The panel will manage:
 * 
 * - the chart title;
 * - the chart viewing area;
 * - mouse interaction (drag to rotate, mouse-wheel to zoom in and out);
 * - viewing controls (zoom in/out/best-fit, buttons for rotations)
 * - export to PNG, SVG and PDF.
 */
public class ChartPanel3D extends Panel3D {

  /** The chart being rendered. */
  private JFreeChart3D chart;

  /** The chart box (a frame of reference for the chart). */
  private ChartBox3D chartBox;

  /**
   * Creates a new chart panel to display the specified chart.
   *
   * @param chart  the chart.
   */
  public ChartPanel3D(JFreeChart3D chart) {
    super(new World());
    this.chart = chart;
    setWorld(renderWorld());
  }
  
  // listen for dataset changes and update the world
  // then trigger a repaint
  private World renderWorld() {
    World world = new World();  // TODO: when we re-render the chart, should we
        // create a new world, or recycle the existing one?
    
    if (!(this.chart.getPlot() instanceof PiePlot3D)) {
      this.chartBox = new ChartBox3D(20, 10, 10, -10.0, -5.0, -5.0, Color.WHITE);
      world.add(chartBox.getObject3D());    
    }

    this.chart.getPlot().composeToWorld(world, -10.0, -5.0, -5.0);
    return world;

  }

  /**
   * Draws the chart to the specified output target.
   * 
   * @param g2  the output target. 
   */
  @Override
  public void drawContent(Graphics2D g2) {
    super.drawContent(g2); 
    Dimension dim = getSize();
    AffineTransform saved = g2.getTransform();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
    
    g2.translate(dim.width / 2, (dim.height - 40) / 2);

    // if a PiePlot3D then there will be an overlay to track the pie label positions
    if (this.chart.getPlot() instanceof PiePlot3D) {
      PiePlot3D p = (PiePlot3D) this.chart.getPlot();
      World labelOverlay = new World();
      List<Object3D> objs = p.getLabelFaces(-10.0, -5.0, -5.0);
      for (Object3D obj : objs) {
        labelOverlay.add(obj);
      }
      Point2D[] pts = labelOverlay.calculateProjectedPoints(getViewPoint(),
           1000f);
      for (int i = 0; i < p.getDataset().getItemCount() * 2; i++) {
        Face f = labelOverlay.getFaces().get(i);
        if (Tools2D.area2(pts[f.getVertexIndex(0)], pts[f.getVertexIndex(1)], pts[f.getVertexIndex(2)]) > 0) {
          Comparable key = p.getDataset().getKey(i / 2);
          g2.setColor(Color.BLACK);
          g2.setFont(p.getDefaultSectionFont());
          Point2D pt = Tools2D.centrePoint(pts[f.getVertexIndex(0)], pts[f.getVertexIndex(1)], pts[f.getVertexIndex(2)], pts[f.getVertexIndex(3)]);
          TextUtils.drawAlignedString(key.toString(), g2, (float) pt.getX(), (float) pt.getY(), TextAnchor.CENTER);
         }
      }

      
    }
    
    // if a CategoryPlot3D then there will be a ChartBox overlay

    // if an XYZPlot then there will be a ChartBox overlay
    if (this.chart.getPlot() instanceof XYZPlot) {
      World labelOverlay = new World();
      ChartBox3D cb = new ChartBox3D(20, 10, 10, -10.0, -5.0, -5.0, new Color(0, 0, 255, 200));
      labelOverlay.add(cb.getObject3D());
      Point2D[] axisPts2D = labelOverlay.calculateProjectedPoints(getViewPoint(),
           1000f);
      Point2D axisPt = axisPts2D[0];
      g2.setPaint(Color.YELLOW);
      g2.fill(new Rectangle2D.Double(axisPt.getX()-2.0, axisPt.getY()-2.0, 4.0, 4.0));

      g2.setPaint(Color.RED);
      axisPt = axisPts2D[1];
      g2.fill(new Rectangle2D.Double(axisPt.getX()-2.0, axisPt.getY()-2.0, 4.0, 4.0));
      axisPt = axisPts2D[2];
      g2.fill(new Rectangle2D.Double(axisPt.getX()-2.0, axisPt.getY()-2.0, 4.0, 4.0));
      axisPt = axisPts2D[3];
      g2.fill(new Rectangle2D.Double(axisPt.getX()-2.0, axisPt.getY()-2.0, 4.0, 4.0));

      g2.setPaint(Color.GREEN);
      axisPt = axisPts2D[4];
      g2.fill(new Rectangle2D.Double(axisPt.getX()-2.0, axisPt.getY()-2.0, 4.0, 4.0));
      axisPt = axisPts2D[5];
      g2.fill(new Rectangle2D.Double(axisPt.getX()-2.0, axisPt.getY()-2.0, 4.0, 4.0));
      axisPt = axisPts2D[6];
      g2.fill(new Rectangle2D.Double(axisPt.getX()-2.0, axisPt.getY()-2.0, 4.0, 4.0));
      axisPt = axisPts2D[7];
      g2.fill(new Rectangle2D.Double(axisPt.getX()-2.0, axisPt.getY()-2.0, 4.0, 4.0));

      // vertices
      Point2D v0 = axisPts2D[0];
      Point2D v1 = axisPts2D[1];
      Point2D v2 = axisPts2D[2];
      Point2D v3 = axisPts2D[3];
      Point2D v4 = axisPts2D[4];
      Point2D v5 = axisPts2D[5];
      Point2D v6 = axisPts2D[6];
      Point2D v7 = axisPts2D[7];

      // faces
      boolean a = this.chartBox.faceA().isRendered();
      boolean b = this.chartBox.faceB().isRendered();
      boolean c = this.chartBox.faceC().isRendered();
      boolean d = this.chartBox.faceD().isRendered();
      boolean e = this.chartBox.faceE().isRendered();
      boolean f = this.chartBox.faceF().isRendered();

      XYZPlot plot = (XYZPlot) this.chart.getPlot();
      Axis3D xAxis = plot.getXAxis();//new Axis3D(new Range(0.0, 10.0));
    //  xAxis.setLineStroke(new BasicStroke(3.0f));
   //   xAxis.setLinePaint(Color.RED);
      Axis3D yAxis = plot.getYAxis();//new Axis3D(new Range(0.0, 10.0));
    //  yAxis.setLineStroke(new BasicStroke(3.0f));
   //   yAxis.setLinePaint(Color.GREEN);
      Axis3D zAxis = plot.getZAxis(); //new Axis3D(new Range(0.0, 10.0));
   //   zAxis.setLineStroke(new BasicStroke(3.0f));
  //    zAxis.setLinePaint(Color.BLUE);

      double ab = (count(a, b) == 1 ? v0.distance(v1) : 0.0);
      double bc = (count(b, c) == 1 ? v3.distance(v2) : 0.0);
      double cd = (count(c, d) == 1 ? v4.distance(v7) : 0.0);
      double da = (count(d, a) == 1 ? v5.distance(v6) : 0.0);
      double be = (count(b, e) == 1 ? v0.distance(v3) : 0.0);
      double bf = (count(b, f) == 1 ? v1.distance(v2) : 0.0);
      double df = (count(d, f) == 1 ? v6.distance(v7) : 0.0);
      double de = (count(d, e) == 1 ? v5.distance(v4) : 0.0);
      double ae = (count(a, e) == 1 ? v0.distance(v5) : 0.0);
      double af = (count(a, f) == 1 ? v1.distance(v6) : 0.0);
      double cf = (count(c, f) == 1 ? v2.distance(v7) : 0.0);
      double ce = (count(c, e) == 1 ? v3.distance(v4) : 0.0);

      if (count(a, b) == 1 && longest(ab, bc, cd, da)) {
        xAxis.render(g2, v0, v1, v7, true);
      }
      if (count(b, c) == 1 && longest(bc, ab, cd, da)) {
        xAxis.render(g2, v3, v2, v6, true);
      }
      if (count(c, d) == 1 && longest(cd, ab, bc, da)) {
        xAxis.render(g2, v4, v7, v1, true);
      }
      if (count(d, a) == 1 && longest(da, ab, bc, cd)) {
        xAxis.render(g2, v5, v6, v3, true);
      }

      if (count(b, e) == 1 && longest(be, bf, df, de)) {
        yAxis.render(g2, v0, v3, v7, true);
      }
      if (count(b, f) == 1 && longest(bf, be, df, de)) {
        yAxis.render(g2, v1, v2, v4, true);
      }
      if (count(d, f) == 1 && longest(df, be, bf, de)) {
        yAxis.render(g2, v6, v7, v0, true);
      }
      if (count(d, e) == 1 && longest(de, be, bf, df)) {
        yAxis.render(g2, v5, v4, v1, true);
      }

      if (count(a, e) == 1 && longest(ae, af, cf, ce)) {
        zAxis.render(g2, v0, v5, v2, true);
      }
      if (count(a, f) == 1 && longest(af, ae, cf, ce)) {
        zAxis.render(g2, v1, v6, v3, true);
      }
      if (count(c, f) == 1 && longest(cf, ae, af, ce)) {
        zAxis.render(g2, v2, v7, v5, true);
      }
      if (count(c, e) == 1 && longest(ce, ae, af, cf)) {
        zAxis.render(g2, v3, v4, v6, true);
      }
    }
    g2.setTransform(saved);
  }

  private boolean longest(double x, double a, double b, double c) {
    return x >= a && x >= b && x >= c;
  }

  private int count(boolean a, boolean b) {
    int result = 0;
    if (a) {
      result++;
    }
    if (b) {
      result++;
    }
    return result;
  }


}
