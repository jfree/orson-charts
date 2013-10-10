/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.axis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Objects;
import javax.swing.event.EventListenerList;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ObjectUtils;

/**
 * A base class that can be used to create an {@link Axis3D} implementation.
 */
public abstract class AbstractAxis3D implements Axis3D {
    
    /** The axis label (if <code>null</code>, no label is displayed). */
    private String label;
  
    /** The label font (never <code>null</code>). */
    private Font labelFont;
    
    /** The color used to draw the axis label (never <code>null</code>). */
    private Paint labelPaint;
 
    /** The stroke used to draw the axis line. */
    private Stroke lineStroke;

    /** The color used to draw the axis line. */
    private Color lineColor;
  
    /** Draw the tick labels? */
    private boolean tickLabelsVisible;
    
    /** The font used to display tick labels (never <code>null</code>) */
    private Font tickLabelFont;
    
    /** The tick label paint (never <code>null</code>). */
    private Paint tickLabelPaint;

    /** Storage for registered change listeners. */
    private transient EventListenerList listenerList;
  
    /**
     * Creates a new label with the specified label.
     * 
     * @param label  the axis label (<code>null</code> permitted). 
     */
    public AbstractAxis3D(String label) {
        this.label = label;
        this.labelFont = new Font("SansSerif", Font.BOLD, 12);
        this.labelPaint = Color.BLACK;
        this.lineStroke = new BasicStroke(1.0f);
        this.lineColor = Color.GRAY;
        this.tickLabelsVisible = true;
        this.tickLabelFont = new Font("SansSerif", Font.PLAIN, 12);
        this.tickLabelPaint = Color.BLACK;
        this.listenerList = new EventListenerList();
    }

    /**
     * Returns the axis label.
     * 
     * @return The axis label (possibly <code>null</code>). 
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
     * Returns the paint used for the label.  The default value is 
     * <code>Color.BLACK</code>.
     * 
     * @return The label paint (never <code>null</code>). 
     */
    public Paint getLabelPaint() {
        return this.labelPaint;
    }
    
    /**
     * Sets the paint used to draw the axis label and sends a 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param paint  the paint (<code>null</code> not permitted). 
     */
    public void setLabelPaint(Paint paint) {
        ArgChecks.nullNotPermitted(paint, "paint");
        this.labelPaint = paint;
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
     * Returns the flag that controls whether or not the tick labels are
     * drawn.  The default value is <code>true</code>.
     * @return 
     */
    public boolean getTickLabelsVisible() {
        return this.tickLabelsVisible;
    }
    
    /**
     * Sets the flag that controls whether or not the tick labels are drawn,
     * and sends an {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param visible  visible?
     */
    public void setTickLabelsVisible(boolean visible) {
        this.tickLabelsVisible = visible;
        fireChangeEvent();
    }
    
    /**
     * Returns the font used to display the tick labels.
     * 
     * @return The font (never <code>null</code>). 
     */
    public Font getTickLabelFont() {
        return this.tickLabelFont;
    }
  
    /**
     * Sets the font used to display tick labels and sends a change event to
     * all registered listeners.
     * 
     * @param font  the font (<code>null</code> not permitted). 
     */
    public void setTickLabelFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.tickLabelFont = font;
        fireChangeEvent();
    }
    
    /**
     * Returns the foreground color for the tick labels.  The default value
     * is <code>Color.BLACK</code>.
     * 
     * @return The foreground color (never <code>null</code>). 
     */
    public Paint getTickLabelPaint() {
        return this.tickLabelPaint;
    }
    
    /**
     * Sets the foreground color for the tick labels and sends an 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param paint  the paint (<code>null</code> not permitted).
     */
    public void setTickLabelPaint(Paint paint) {
        ArgChecks.nullNotPermitted(paint, "paint");
        this.tickLabelPaint = paint;
        fireChangeEvent();
    }

    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object to test against (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractAxis3D)) {
            return false;
        }
        AbstractAxis3D that = (AbstractAxis3D) obj;
        if (!Objects.equals(this.label, that.label)) {
            return false;
        }
        if (!this.labelFont.equals(that.labelFont)) {
            return false;
        }
        if (!ObjectUtils.equalsPaint(this.labelPaint, that.labelPaint)) {
            return false;
        }
        if (!this.lineStroke.equals(that.lineStroke)) {
            return false;
        }
        if (!this.lineColor.equals(that.lineColor)) {
            return false;
        }
        if (this.tickLabelsVisible != that.tickLabelsVisible) {
            return false;
        }
        if (!this.tickLabelFont.equals(that.tickLabelFont)) {
            return false;
        }
        if (!ObjectUtils.equalsPaint(this.tickLabelPaint, 
                that.tickLabelPaint)) {
            return false;
        }
        return true;
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
    protected Line2D createPerpendicularLine(Line2D line, double b, double size, 
            Point2D opposingPoint) {
        double dx = line.getX2() - line.getX1();
        double dy = line.getY2() - line.getY1();
        double length = Math.sqrt(dx * dx + dy * dy);
        double pdx = dy / length;
        double pdy = -dx / length;
        int ccw = line.relativeCCW(opposingPoint);
        Point2D pt1 = new Point2D.Double(line.getX1() + b * dx, 
                line.getY1() + b * dy);
        Point2D pt2 = new Point2D.Double(pt1.getX() - ccw * size * pdx, 
                pt1.getY() - ccw * size * pdy);
        return new Line2D.Double(pt1, pt2);
    }
    
    protected Line2D createPerpendicularLine(Line2D line, Point2D pt1, double size, 
            Point2D opposingPoint) {
        double dx = line.getX2() - line.getX1();
        double dy = line.getY2() - line.getY1();
        double length = Math.sqrt(dx * dx + dy * dy);
        double pdx = dy / length;
        double pdy = -dx / length;
        int ccw = line.relativeCCW(opposingPoint);
        Point2D pt2 = new Point2D.Double(pt1.getX() - ccw * size * pdx, 
                pt1.getY() - ccw * size * pdy);
        return new Line2D.Double(pt1, pt2);
    }
  
    protected double calculateTheta(Line2D line) {
        double dx = line.getX2() - line.getX1();
        double dy = line.getY2() - line.getY1();
        return Math.atan2(dy, dx);
    }
    
    @Override
    public void addChangeListener(Axis3DChangeListener listener) {
        this.listenerList.add(Axis3DChangeListener.class, listener);   
    }
  
    @Override
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
        notifyListeners(new Axis3DChangeEvent(this));
    }
}
