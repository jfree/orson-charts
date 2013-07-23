/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.axis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;
import javax.swing.event.EventListenerList;
import org.jfree.chart3d.event.Axis3DChangeEvent;
import org.jfree.chart3d.event.Axis3DChangeListener;
import org.jfree.graphics3d.ArgChecks;

/**
 * An axis in 3D space.
 */
public class NumberAxis3D implements Axis3D {

  /** The axis label (if null, no label is displayed). */
  private String label;
  
  /** The label font (never <code>null</code>). */
  private Font labelFont;
 
  /** The stroke used to draw the axis line. */
  private Stroke lineStroke;

  /** The color used to draw the axis line. */
  private Color lineColor;
  
  /** The axis range. */
  private Range range;

  private double tickSize;

  private Font valueLabelFont = new Font("SansSerif", Font.PLAIN, 12);

  NumberFormat tickFormatter;

  /** Storage for registered change listeners. */
  private transient EventListenerList listenerList;
  
  /**
   * Creates a new axis with the specified range.
   *
   * @param label  the axis label (<code>null</code> permitted).
   * @param range  the range (<code>null</code> not permitted).
   */
  public NumberAxis3D(String label, Range range) {
    this.label = label;
    this.labelFont = new Font("SansSerif", Font.BOLD, 12);
    this.range = range;
    this.tickFormatter = new DecimalFormat("0.00");
    this.tickSize = range.getLength() / 10.0;  // FIXME
    this.lineStroke = new BasicStroke(1.0f);
    this.lineColor = Color.GRAY;
    this.listenerList = new EventListenerList();
  }
  
  /**
   * Returns the axis label.
   * 
   * @return The axis label (<code>null</code> permitted). 
   */
  public String getLabel() {
    return this.label;
  }
  
  /**
   * Sets the axis label and sends a change event to all registered listeners.
   * 
   * @param label  the label (<code>null</code> permitted). 
   */
  public void setLabel(String label) {
    this.label = label;
    fireChangeEvent();
  }

  /**
   * Returns the font used to display the axis label.
   * 
   * @return The font used to display the axis label (never <code>null</code>). 
   */
  public Font getLabelFont() {
    return this.labelFont;
  }
  
  /**
   * Sets the font used to display the axis label and sends a change event to
   * all registered listeners.
   * 
   * @param font  the new font (<code>null</code> not permitted). 
   */
  public void setLabelFont(Font font) {
    ArgChecks.nullNotPermitted(font, "font");
    this.labelFont = font;
    fireChangeEvent();
  }
  
  /**
   * Returns the stroke used to draw the axis line.
   * 
   * @return The stroke used to draw the axis line (never <code>null</code>).
   */
  public Stroke getLineStroke() {
    return this.lineStroke;
  }
  
  /**
   * Sets the stroke used to draw the axis line and sends a change event
   * to all registered listeners.
   * 
   * @param stroke  the new stroke (<code>null</code> not permitted).
   */
  public void setLineStroke(Stroke stroke) {
    ArgChecks.nullNotPermitted(stroke, "stroke");
    this.lineStroke = stroke;
    fireChangeEvent();
  }

  /**
   * Returns the color used to draw the axis line.
   * 
   * @return The color used to draw the axis line (never <code>null</code>). 
   */
  public Color getLineColor() {
    return this.lineColor;
  }
  
  /**
   * Sets the color used to draw the axis line and sends a change event to 
   * all registered listeners.
   * 
   * @param color  the new color (<code>null</code> not permitted). 
   */
  public void setLineColor(Color color) {
    ArgChecks.nullNotPermitted(color, "color");
    this.lineColor = color;
    fireChangeEvent();
  }

  /**
   * Returns the axis range.
   * 
   * @return the axis range (never <code>null</code>).
   */
  @Override
  public Range getRange() {
    return this.range;
  }
  
  /**
   * Sets the axis range (bounds) and sends a change event to all registered
   * listeners.
   * 
   * @param range  the new range (<code>null</code> not permitted).
   */
  public void setRange(Range range) {
    ArgChecks.nullNotPermitted(range, "range");
    this.range = range;
    fireChangeEvent();
  }
  
  /**
   * Returns the tick size.
   * 
   * @return The tick size.
   */
  public double getTickSize() {
    return this.tickSize;
  }

  /**
   * Sets the tick size and sends a change event to all registered listeners.
   * 
   * @param tickSize  the new tick size.
   */
  public void setTickSize(double tickSize) {
    this.tickSize = tickSize;
    fireChangeEvent();
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
    g2.setPaint(this.lineColor);
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

  @Override
  public double translateToWorld(double value, double length) {
    return length * (value - this.range.getMin()) / this.range.getLength();
  }
  
  /**
   * Tests this instance for equality with an arbitrary object.
   * 
   * @param obj  the object to test against (<code>null</code> permitted).
   * 
   * @return A boolean. 
   */
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof NumberAxis3D)) {
      return false;
    }
    NumberAxis3D that = (NumberAxis3D) obj;
    if (!Objects.equals(this.label, that.label)) {
      return false;
    }
    if (!this.labelFont.equals(that.labelFont)) {
      return false;
    }
    if (!this.lineStroke.equals(that.lineStroke)) {
      return false;
    }
    if (!this.lineColor.equals(that.lineColor)) {
      return false;
    }
    if (!this.range.equals(that.range)) {
      return false;
    }
    if (this.tickSize != that.tickSize) {
      return false;
    }
    return true;
  }
  
  public void addChangeListener(Axis3DChangeListener listener) {
    this.listenerList.add(Axis3DChangeListener.class, listener);   
  }
  
  public void removeChangeListener(Axis3DChangeListener listener) {
    this.listenerList.remove(Axis3DChangeListener.class, listener);  
  }
  
  /**
   * Notifies all registered listeners that the plot has been modified.
   *
   * @param event  information about the change event.
   */
  public void notifyListeners(Axis3DChangeEvent event) {
    Object[] listeners = this.listenerList.getListenerList();
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == Axis3DChangeListener.class) { 
        ((Axis3DChangeListener) listeners[i + 1]).axisChanged(event);
      }
    }
  }
  
  /**
   * Sends a {@link Axis3DChangeEvent} to all registered listeners.
   */
  protected void fireChangeEvent() {
    notifyListeners(new Axis3DChangeEvent(this, this));
  }
}
