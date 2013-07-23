/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.axis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * An axis in 3D space.
 */
public class Axis3D {

  /** The axis label (if null, no label is displayed). */
  private String label;
  
  private Font labelFont;
 
  private Range range;

  private double tickSize;

  private Font valueLabelFont = new Font("SansSerif", Font.PLAIN, 12);
  
  private Stroke lineStroke;

  private Paint linePaint;

  NumberFormat tickFormatter;

  /**
   * Creates a new axis with the specified range.
   *
   * @param label  the axis label (<code>null</code> permitted).
   * @param range  the range (<code>null</code> not permitted).
   */
  public Axis3D(String label, Range range) {
    this.label = label;
    this.labelFont = new Font("SansSerif", Font.BOLD, 12);
    this.range = range;
    this.tickFormatter = new DecimalFormat("0.00");
    this.tickSize = range.getLength() / 10.0;  // FIXME
    this.lineStroke = new BasicStroke(1.0f);
    this.linePaint = Color.GRAY;
  }
  
  public String getLabel() {
    return this.label;
  }
  
  /**
   * Sets the axis label.
   * 
   * @param label  the label (<code>null</code> permitted). 
   */
  public void setLabel(String label) {
    this.label = label;
  }

  public double getTickSize() {
    return this.tickSize;
  }

  public void setTickSize(double tickSize) {
    this.tickSize = tickSize;
  }

  public void setLineStroke(Stroke stroke) {
    this.lineStroke = stroke;
  }

  public void setLinePaint(Paint paint) {
    this.linePaint = paint;
  }

  /**
   * Renders the axis using the supplied graphics device, with the
   * specified starting and ending points for the line.
   *
   * @param g2
   * @param pt0
   * @param pt1
   * @param opposingPt
   * @param labels
   */
  public void render(Graphics2D g2, Point2D pt0, Point2D pt1, Point2D opposingPt, boolean labels) {
    g2.setStroke(this.lineStroke);
    g2.setPaint(this.linePaint);
    Line2D axisLine = new Line2D.Float(pt0, pt1);
    g2.draw(axisLine);

    // now draw a small black line perpendicular to the axis - the aim is to
    // point to the side where the text labels will be displayed
    // we could do this by assuming that the diagonally opposite
    // line segment in the cube is on the "inside" of the chart
    g2.setFont(this.valueLabelFont);
    for (int i = 0; i <= 10; i++) {
      Line2D perpLine = createPerpendicularLine(axisLine, 0.1 * i, 10.0, opposingPt);
      g2.setPaint(Color.BLACK);
      g2.setStroke(new BasicStroke(1f));
      g2.draw(perpLine);
      double theta = calculateTheta(axisLine);
      double thetaAdj = theta + Math.PI / 2.0;
      if (thetaAdj < -Math.PI / 2.0) {
          thetaAdj = thetaAdj + Math.PI;
      }
      if (thetaAdj > Math.PI / 2.0) {
          thetaAdj = thetaAdj - Math.PI;
      }
      double v = range.getMin() + (0.1 * i * range.getLength());

      double perpTheta = calculateTheta(perpLine);
      TextAnchor textAnchor = TextAnchor.CENTER_LEFT;
      if (Math.abs(perpTheta) > Math.PI / 2.0) {
          textAnchor = TextAnchor.CENTER_RIGHT;
      } 
      TextUtils.drawRotatedString(this.tickFormatter.format(v), g2, (float) perpLine.getX2(), (float) perpLine.getY2(), textAnchor,
              thetaAdj, textAnchor);
      //  g2.drawString(String.valueOf(i), (float) perpLine.getX2(), (float) perpLine.getY2());
    }

    if (this.label != null) {
      g2.setFont(this.labelFont);
      Line2D labelPosLine = createPerpendicularLine(axisLine, 0.5, 60.0, 
              opposingPt);
      double theta = calculateTheta(axisLine);
      if (theta < -Math.PI / 2.0) {
          theta = theta + Math.PI;
      }
      if (theta > Math.PI / 2.0) {
          theta = theta - Math.PI;
      }
      TextUtils.drawRotatedString(this.label, g2, (float) labelPosLine.getX2(), 
              (float) labelPosLine.getY2(), TextAnchor.CENTER, theta, TextAnchor.CENTER);
    }
  }

  private static final double EPSILON = 0.000001;
  
  private double calculateTheta(Line2D line) {
      double dx = line.getX2() - line.getX1();
      double dy = line.getY2() - line.getY1();
      double theta = Math.atan2(dy, dx);
      return theta;
  }
  
  /**
   * 
   */
  private Line2D createPerpendicularLine(Line2D line, double b, double size) {
    double dx = line.getX2() - line.getX1();
    double dy = line.getY2() - line.getY1();
    double length = Math.sqrt(dx * dx + dy * dy);
    double pdx = dy / length;
    double pdy = -dx / length;
    Point2D pt1 = new Point2D.Double(line.getX1() + b * dx, line.getY1() + b * dy);
    Point2D pt2 = new Point2D.Double(pt1.getX() + size * pdx, pt1.getY() + size * pdy);
    return new Line2D.Double(pt1, pt2);
  }

  /**
   * Creates and returns a line that is perpendicular to the specified line.
   *
   * @param line  the reference line.
   * @param b  the base point, expressed as a percentage along the length of 
   *     the reference line.
   * @param size  the size or length of the perpendicular line.
   * @param opposingPoint  an opposing point, to define which side of the 
   *     reference line the perpendicular line will extend.
   *
   * @return The perpendicular line.
   */
  private Line2D createPerpendicularLine(Line2D line, double b, double size, 
          Point2D opposingPoint) {
    double dx = line.getX2() - line.getX1();
    double dy = line.getY2() - line.getY1();
    double length = Math.sqrt(dx * dx + dy * dy);
    double pdx = dy / length;
    double pdy = -dx / length;
    int ccw = line.relativeCCW(opposingPoint);
    Point2D pt1 = new Point2D.Double(line.getX1() + b * dx, line.getY1() + b * dy);
    Point2D pt2 = new Point2D.Double(pt1.getX() - ccw * size * pdx, pt1.getY() - ccw * size * pdy);
    return new Line2D.Double(pt1, pt2);
  }
}
