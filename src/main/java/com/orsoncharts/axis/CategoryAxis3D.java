/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.axis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * An axis that displays categories.
 */
public class CategoryAxis3D extends AbstractAxis3D implements Axis3D {

  private Map<Comparable, Number> labels;
  
  private Range range;
  
  public CategoryAxis3D(String label) {
    super(label);
    this.labels = new HashMap<Comparable, Number>();
    this.range = new Range(0, 4);
  }

  // FIXME: scaffolding method
  public void setCategoryLabel(Comparable key, double value) {
    labels.put(key, Double.valueOf(value));
  }
  
  @Override
  public Range getRange() {
    return this.range;
  }
  
  public void setRange(double lowerBound, double upperBound) {
    this.range = new Range(lowerBound, upperBound);
  }

  @Override
  public double translateToWorld(double value, double length) {
    return length * (value - this.range.getMin()) / this.range.getLength();
  }

  @Override
  public void render(Graphics2D g2, Point2D pt0, Point2D pt1, Point2D opposingPt, boolean labels) {
    g2.setStroke(getLineStroke());
    g2.setPaint(getLineColor());
    Line2D axisLine = new Line2D.Float(pt0, pt1);
    g2.draw(axisLine);

    // now draw a small black line perpendicular to the axis - the aim is to
    // point to the side where the text labels will be displayed
    // we could do this by assuming that the diagonally opposite
    // line segment in the cube is on the "inside" of the chart
    g2.setFont(getTickLabelFont());
    Set<Entry<Comparable, Number>> entrySet = this.labels.entrySet();
    for (Entry<Comparable, Number> entry : entrySet) {
      Comparable key = entry.getKey();
      Number n = entry.getValue();
      double d = n.doubleValue();
      double p = getRange().percent(d);
      Line2D perpLine = createPerpendicularLine(axisLine, p, 10.0, opposingPt);
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

      double perpTheta = calculateTheta(perpLine);
      TextAnchor textAnchor = TextAnchor.CENTER_LEFT;
      if (Math.abs(perpTheta) > Math.PI / 2.0) {
          textAnchor = TextAnchor.CENTER_RIGHT;
      } 
      TextUtils.drawRotatedString(key.toString(), g2, 
          (float) perpLine.getX2(), (float) perpLine.getY2(), textAnchor,
          thetaAdj, textAnchor);
    }

    if (getLabel() != null) {
      g2.setFont(getLabelFont());
      Line2D labelPosLine = createPerpendicularLine(axisLine, 0.5, 60.0, 
              opposingPt);
      double theta = calculateTheta(axisLine);
      if (theta < -Math.PI / 2.0) {
          theta = theta + Math.PI;
      }
      if (theta > Math.PI / 2.0) {
          theta = theta - Math.PI;
      }
      TextUtils.drawRotatedString(getLabel(), g2, (float) labelPosLine.getX2(), 
              (float) labelPosLine.getY2(), TextAnchor.CENTER, theta, 
              TextAnchor.CENTER);
    }
  }
}
