/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.axis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.EventListenerList;

import com.orsoncharts.Chart3DHints;
import com.orsoncharts.ChartElementVisitor;
import com.orsoncharts.graphics3d.RenderedElement;
import com.orsoncharts.graphics3d.RenderingInfo;
import com.orsoncharts.graphics3d.Utils2D;
import com.orsoncharts.interaction.InteractiveElementType;
import com.orsoncharts.marker.MarkerChangeEvent;
import com.orsoncharts.marker.MarkerChangeListener;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ObjectUtils;
import com.orsoncharts.util.SerialUtils;
import com.orsoncharts.util.TextAnchor;
import com.orsoncharts.util.TextUtils;

/**
 * A base class that can be used to create an {@link Axis3D} implementation.
 * This class implements the core axis attributes as well as the change 
 * listener mechanism required to enable automatic repainting of charts.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
public abstract class AbstractAxis3D implements Axis3D, MarkerChangeListener, 
        Serializable {
    
    /** 
     * The default axis label font (in most circumstances this will be
     * overridden by the chart style).
     * 
     * @since 1.2
     */
    public static final Font DEFAULT_LABEL_FONT = new Font("Dialog", Font.BOLD, 
            12);
    
    /** 
     * The default axis label color (in most circumstances this will be
     * overridden by the chart style).
     * 
     * @since 1.2
     */
    public static final Color DEFAULT_LABEL_COLOR = Color.BLACK;
    
    /**
     * The default label offset.
     * 
     * @since 1.2
     */
    public static final double DEFAULT_LABEL_OFFSET = 10;

    /** 
     * The default tick label font (in most circumstances this will be
     * overridden by the chart style).
     * 
     * @since 1.2
     */
    public static final Font DEFAULT_TICK_LABEL_FONT = new Font("Dialog", 
            Font.PLAIN, 12);
    
    /** 
     * The default tick label color (in most circumstances this will be
     * overridden by the chart style).
     * 
     * @since 1.2
     */
    public static final Color DEFAULT_TICK_LABEL_COLOR = Color.BLACK;
    
    /** 
     * The default stroke for the axis line.
     * 
     * @since 1.2
     */
    public static final Stroke DEFAULT_LINE_STROKE = new BasicStroke(0f);
    
    /**
     * The default color for the axis line.
     * 
     * @since 1.2
     */
    public static final Color DEFAULT_LINE_COLOR = Color.GRAY;
    
    /** A flag that determines whether or not the axis will be drawn. */
    private boolean visible;
    
    /** The axis label (if <code>null</code>, no label is displayed). */
    private String label;
  
    /** The label font (never <code>null</code>). */
    private Font labelFont;
    
    /** The color used to draw the axis label (never <code>null</code>). */
    private Color labelColor;
    
    /** The offset between the tick labels and the label. */
    private double labelOffset;
    
    /** The stroke used to draw the axis line. */
    private transient Stroke lineStroke;

    /** The color used to draw the axis line. */
    private Color lineColor;
  
    /** Draw the tick labels? */
    private boolean tickLabelsVisible;
    
    /** The font used to display tick labels (never <code>null</code>) */
    private Font tickLabelFont;
    
    /** The tick label paint (never <code>null</code>). */
    private Color tickLabelColor;

    /** Storage for registered change listeners. */
    private transient EventListenerList listenerList;
    
    /**
     * Creates a new label with the specified label.  If the supplied label
     * is <code>null</code>, the axis will be shown without a label.
     * 
     * @param label  the axis label (<code>null</code> permitted). 
     */
    public AbstractAxis3D(String label) {
        this.visible = true;
        this.label = label;
        this.labelFont = DEFAULT_LABEL_FONT;
        this.labelColor = DEFAULT_LABEL_COLOR;
        this.labelOffset = DEFAULT_LABEL_OFFSET;
        this.lineStroke = DEFAULT_LINE_STROKE;
        this.lineColor = DEFAULT_LINE_COLOR;
        this.tickLabelsVisible = true;
        this.tickLabelFont = DEFAULT_TICK_LABEL_FONT;
        this.tickLabelColor = DEFAULT_TICK_LABEL_COLOR;
        this.listenerList = new EventListenerList();
    }

    /**
     * Returns the flag that determines whether or not the axis is drawn 
     * on the chart.
     * 
     * @return A boolean.
     * 
     * @see #setVisible(boolean) 
     */
    @Override
    public boolean isVisible() {
        return this.visible;
    }
    
    /**
     * Sets the flag that determines whether or not the axis is drawn on the
     * chart and sends an {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param visible  the flag.
     * 
     * @see #isVisible() 
     */
    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
        fireChangeEvent(false);
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
     * Sets the axis label and sends an {@link Axis3DChangeEvent} to all 
     * registered listeners.  If the supplied label is <code>null</code>,
     * the axis will be drawn without a label.
     * 
     * @param label  the label (<code>null</code> permitted). 
     */
    public void setLabel(String label) {
        this.label = label;
        fireChangeEvent(false);
    }

    /**
     * Returns the font used to display the main axis label.  The default value
     * is <code>Font("SansSerif", Font.BOLD, 12)</code>.
     * 
     * @return The font used to display the axis label (never <code>null</code>). 
     */
    @Override
    public Font getLabelFont() {
        return this.labelFont;
    }
   
    /**
     * Sets the font used to display the main axis label and sends an
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param font  the new font (<code>null</code> not permitted). 
     */
    @Override
    public void setLabelFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.labelFont = font;
        fireChangeEvent(false);
    }

    /**
     * Returns the color used for the label.  The default value is 
     * <code>Color.BLACK</code>.
     * 
     * @return The label paint (never <code>null</code>). 
     */
    @Override
    public Color getLabelColor() {
        return this.labelColor;
    }
    
    /**
     * Sets the color used to draw the axis label and sends a 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param color  the color (<code>null</code> not permitted). 
     */
    @Override
    public void setLabelColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.labelColor = color;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the offset between the tick labels and the axis label.
     * 
     * @return The offset.
     * 
     * @since 1.2
     */
    public double getLabelOffset() {
        return this.labelOffset;
    }
    
    /**
     * Sets the offset between the tick labels and the axis label and sends
     * a change event to all registered listeners.
     * 
     * @param offset  the offset.
     * 
     * @since 1.2
     */
    public void setLabelOffset(double offset) {
        this.labelOffset = offset;
        fireChangeEvent(false);
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
        fireChangeEvent(false);
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
        fireChangeEvent(false);
    }

    /**
     * Returns the flag that controls whether or not the tick labels are
     * drawn.  The default value is <code>true</code>.
     * 
     * @return A boolean.
     */
    public boolean getTickLabelsVisible() {
        return this.tickLabelsVisible;
    }
    
    /**
     * Sets the flag that controls whether or not the tick labels are drawn,
     * and sends a change event to all registered listeners.  You should think
     * carefully before setting this flag to <code>false</code>, because if 
     * the tick labels are not shown it will be hard for the reader to 
     * understand the resulting chart.
     * 
     * @param visible  visible?
     */
    public void setTickLabelsVisible(boolean visible) {
        this.tickLabelsVisible = visible;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the font used to display the tick labels.
     * 
     * @return The font (never <code>null</code>). 
     */
    @Override
    public Font getTickLabelFont() {
        return this.tickLabelFont;
    }
  
    /**
     * Sets the font used to display tick labels and sends an 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param font  the font (<code>null</code> not permitted). 
     */
    @Override
    public void setTickLabelFont(Font font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.tickLabelFont = font;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the foreground color for the tick labels.  The default value
     * is <code>Color.BLACK</code>.
     * 
     * @return The foreground color (never <code>null</code>). 
     */
    @Override
    public Color getTickLabelColor() {
        return this.tickLabelColor;
    }
    
    /**
     * Sets the foreground color for the tick labels and sends an 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param color  the color (<code>null</code> not permitted).
     */
    @Override
    public void setTickLabelColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.tickLabelColor = color;
        fireChangeEvent(false);
    }

    /**
     * Receives a {@link ChartElementVisitor}.  This method is part of a general
     * mechanism for traversing the chart structure and performing operations
     * on each element in the chart.  You will not normally call this method
     * directly.
     * 
     * @param visitor  the visitor (<code>null</code> not permitted).
     * 
     * @since 1.2
     */
    @Override
    public abstract void receive(ChartElementVisitor visitor);
    
    /**
     * Draws the specified text as the axis label and returns a bounding 
     * shape for the text.
     * 
     * @param label  the label (<code>null</code> not permitted).
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param axisLine  the axis line (<code>null</code> not permitted).
     * @param opposingPt  an opposing point (<code>null</code> not permitted).
     * @param offset  the offset.
     * @param hinting  perform element hinting?
     * 
     * @return A bounding shape.
     */
    protected Shape drawAxisLabel(String label, Graphics2D g2, 
            Line2D axisLine, Point2D opposingPt, double offset, 
            RenderingInfo info, boolean hinting) {
        ArgChecks.nullNotPermitted(label, "label");
        ArgChecks.nullNotPermitted(g2, "g2");
        ArgChecks.nullNotPermitted(axisLine, "axisLine");
        ArgChecks.nullNotPermitted(opposingPt, "opposingPt");
        g2.setFont(getLabelFont());
        g2.setPaint(getLabelColor());
        Line2D labelPosLine = Utils2D.createPerpendicularLine(axisLine, 0.5, 
                offset, opposingPt);
        double theta = Utils2D.calculateTheta(axisLine);
        if (theta < -Math.PI / 2.0) {
            theta = theta + Math.PI;
        }
        if (theta > Math.PI / 2.0) {
            theta = theta - Math.PI;
        }
        
        if (hinting) {
            Map m = new HashMap<String, String>();
            m.put("ref", "{\"type\": \"axisLabel\", axis: " + axisStr() 
                    + ", \"label\": \"" + getLabel() + "\"}");
            g2.setRenderingHint(Chart3DHints.KEY_BEGIN_ELEMENT, m);
        }
        Shape bounds = TextUtils.drawRotatedString(getLabel(), g2, 
                (float) labelPosLine.getX2(), (float) labelPosLine.getY2(), 
                TextAnchor.CENTER, theta, TextAnchor.CENTER);
        if (hinting) {
            g2.setRenderingHint(Chart3DHints.KEY_END_ELEMENT, true);
        }
        if (info != null) {
            RenderedElement labelElement = new RenderedElement(
                    InteractiveElementType.AXIS_LABEL, bounds);
            labelElement.setProperty("axis", axisStr());
            labelElement.setProperty("label", getLabel());
            info.addOffsetElement(labelElement);
        }
        return bounds;
    }
 
    protected abstract String axisStr();
    
    /**
     * Draws the axis along an arbitrary line (between <code>startPt</code> 
     * and <code>endPt</code>).  The opposing point is used as a reference
     * point to know on which side of the axis to draw the labels.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param startPt  the starting point (<code>null</code> not permitted).
     * @param endPt  the end point (<code>null</code> not permitted)
     * @param opposingPt  an opposing point (<code>null</code> not permitted).
     * @param tickData  info about the ticks to draw (<code>null</code> not 
     *     permitted).
     * @param info  an object to be populated with rendering info 
     *     (<code>null</code> permitted).
     * @param hinting  a flag that controls whether or not element hinting 
     *     should be performed.
     */
    @Override
    public abstract void draw(Graphics2D g2, Point2D startPt, Point2D endPt, 
            Point2D opposingPt, List<TickData> tickData, RenderingInfo info, 
            boolean hinting);
    
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
        if (this.visible != that.visible) {
            return false;
        }
        if (!ObjectUtils.equals(this.label, that.label)) {
            return false;
        }
        if (!this.labelFont.equals(that.labelFont)) {
            return false;
        }
        if (!this.labelColor.equals(that.labelColor)) {
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
        if (!this.tickLabelColor.equals(that.tickLabelColor)) {
            return false;
        }
        return true;
    }
    
    /**
     * Registers a listener so that it will receive axis change events.
     * 
     * @param listener  the listener (<code>null</code> not permitted). 
     */
    @Override
    public void addChangeListener(Axis3DChangeListener listener) {
        this.listenerList.add(Axis3DChangeListener.class, listener);   
    }
  
    /**
     * Deregisters a listener so that it will no longer receive axis
     * change events.
     * 
     * @param listener  the listener (<code>null</code> not permitted). 
     */
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
     * 
     * @param requiresWorldUpdate   a flag indicating whether or not this change
     *     requires the 3D world to be updated.
     */
    protected void fireChangeEvent(boolean requiresWorldUpdate) {
        notifyListeners(new Axis3DChangeEvent(this, requiresWorldUpdate));
    }

    /**
     * Receives notification of a change to a marker managed by this axis - the
     * response is to fire a change event for the axis (to eventually trigger
     * a repaint of the chart).  Marker changes don't require the world model
     * to be updated.
     * 
     * @param event  the event.
     * 
     * @since 1.2
     */
    @Override
    public void markerChanged(MarkerChangeEvent event) {
        fireChangeEvent(false);
    }
    
    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writeStroke(this.lineStroke, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.lineStroke = SerialUtils.readStroke(stream);
    }
 
}
