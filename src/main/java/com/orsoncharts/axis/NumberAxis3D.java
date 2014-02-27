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
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.Format;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import com.orsoncharts.util.TextUtils;
import com.orsoncharts.util.TextAnchor;
import com.orsoncharts.Range;
import com.orsoncharts.graphics3d.Utils2D;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.util.ObjectUtils;
import com.orsoncharts.util.SerialUtils;

/**
 * A numerical axis for use with 3D plots (implements {@link ValueAxis3D}).
 * In a {@link CategoryPlot3D} the value axis (the vertical one) is numerical, 
 * and in an {@link XYZPlot} all the axes (x, y and z) are numerical - for
 * all these cases an instance of this class can be used.
 */
public class NumberAxis3D extends AbstractValueAxis3D implements ValueAxis3D,
        Serializable {

    /** 
     * A flag indicating whether or not the auto-range calculation should
     * include zero.
     */
    private boolean autoRangeIncludesZero;
    
    /**
     * A flag that controls how zero is handled when it falls within the
     * margins.  If <code>true</code>, the margin is truncated at zero, if
     * <code>false</code> the margin is not changed.
     */
    private boolean autoRangeStickyZero;
        
    /** 
     * The tick selector (if not <code>null</code>, then auto-tick selection 
     * is used). 
     */
    private TickSelector tickSelector;

    /** 
     * The tick size.  If the tickSelector is not <code>null</code> then it is 
     * used to auto-select an appropriate tick size and format.
     */
    private double tickSize;

    /** The tick formatter (never <code>null</code>). */
    private Format tickLabelFormatter;

    /** The tick label factor (defaults to 1.4). */
    private double tickLabelFactor;    

    /** The tick label offset (number of Java2D units). */
    private double tickLabelOffset;
    
    /** The length of tick marks (in Java2D units).  Can be set to 0.0. */
    private double tickMarkLength;
    
    /** The tick mark stroke (never <code>null</code>). */
    private transient Stroke tickMarkStroke;
    
    /** The tick mark paint (never <code>null</code>). */
    private transient Paint tickMarkPaint;
    
    /**
     * Creates a new axis with the specified label and default attributes.
     * 
     * @param label  the axis label (<code>null</code> permitted). 
     */
    public NumberAxis3D(String label) {
        this(label, new Range(0.0, 1.0));
    }
    
    /**
     * Creates a new axis with the specified label and range.
     *
     * @param label  the axis label (<code>null</code> permitted).
     * @param range  the range (<code>null</code> not permitted).
     */
    public NumberAxis3D(String label, Range range) {
        super(label, range);
        this.autoRangeIncludesZero = false;
        this.autoRangeStickyZero = true;
        this.tickSelector = new NumberTickSelector();
        this.tickLabelFactor = 1.4;
        this.tickSize = range.getLength() / 10.0;
        this.tickLabelFormatter = new DecimalFormat("0.00");
        this.tickLabelOffset = 5.0;
        this.tickMarkLength = 3.0;
        this.tickMarkStroke = new BasicStroke(0.5f);
        this.tickMarkPaint = Color.GRAY;
    }
      
    /**
     * Returns the flag that determines whether or not the auto range 
     * mechanism should force zero to be included in the range.  The default
     * value is <code>false</code>.
     * 
     * @return A boolean.
     */
    public boolean getAutoRangeIncludesZero() {
        return this.autoRangeIncludesZero;
    }
    
    /**
     * Sets the flag that controls whether or not the auto range mechanism 
     * should force zero to be included in the axis range, and sends an
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param include  the new flag value.
     */
    public void setAutoRangeIncludeZero(boolean include) {
        this.autoRangeIncludesZero = include;
        fireChangeEvent(true);
    }
    
    /**
     * Returns the flag that controls the behaviour of the auto range 
     * mechanism when zero falls into the axis margins.  The default value
     * is <code>true</code>.
     * 
     * @return A boolean. 
     * 
     * @see #setAutoRangeStickyZero(boolean) 
     */
    public boolean getAutoRangeStickyZero() {
        return this.autoRangeStickyZero;
    }
    
    /**
     * Sets the flag that controls the behaviour of the auto range mechanism 
     * when zero falls into the axis margins.  If <code>true</code>, when
     * zero is in the axis margin the axis range is truncated at zero.  If
     * <code>false</code>, there is no special treatment.
     * 
     * @param sticky  the new flag value. 
     */
    public void setAutoRangeStickyZero(boolean sticky) {
        this.autoRangeStickyZero = sticky;
        fireChangeEvent(true);
    }
  
    /**
     * Returns the tick selector, an object that is responsible for choosing
     * standard tick units for the axis.  The default value is a default
     * instance of {@link NumberTickSelector}.
     * 
     * @return The tick selector. 
     * 
     * @see #setTickSelector(TickSelector) 
     */
    public TickSelector getTickSelector() {
        return this.tickSelector;    
    }
    
    /**
     * Sets the tick selector and sends an {@link Axis3DChangeEvent} to all
     * registered listeners.
     * 
     * @param selector  the selector (<code>null</code> permitted).
     * 
     * @see #getTickSelector() 
     */
    public void setTickSelector(TickSelector selector) {
        this.tickSelector = selector;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the tick size (to be used when the tick selector is 
     * <code>null</code>).
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
        fireChangeEvent(false);
    }
    
    /**
     * Returns the tick label formatter.  The default value is
     * <code>DecimalFormat("0.00")</code>.
     * 
     * @return The tick label formatter (never <code>null</code>). 
     */
    public Format getTickLabelFormatter() {
        return this.tickLabelFormatter;
    }
    
    /**
     * Sets the formatter for the tick labels and sends an 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param formatter  the formatter (<code>null</code> not permitted).
     */
    public void setTickLabelFormatter(Format formatter) {
        ArgChecks.nullNotPermitted(formatter, "formatter");
        this.tickLabelFormatter = formatter;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the tick label factor, a multiplier for the label height to
     * determine the maximum number of tick labels that can be displayed.  
     * The default value is <code>1.4</code>.
     * 
     * @return The tick label factor. 
     */
    public double getTickLabelFactor() {
        return this.tickLabelFactor;
    }
    
    /**
     * Sets the tick label factor and sends an {@link Axis3DChangeEvent}
     * to all registered listeners.  This should be at least 1.0, higher values
     * will result in larger gaps between the tick marks.
     * 
     * @param factor  the factor. 
     */
    public void setTickLabelFactor(double factor) {
        this.tickLabelFactor = factor;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the tick label offset, the gap between the tick marks and the
     * tick labels (in Java2D units).  The default value is <code>5.0</code>.
     * 
     * @return The tick label offset.
     */
    public double getTickLabelOffset() {
        return this.tickLabelOffset;
    }
    
    /**
     * Sets the tick label offset and sends an {@link Axis3DChangeEvent} to
     * all registered listeners.
     * 
     * @param offset  the offset.
     */
    public void setTickLabelOffset(double offset) {
        this.tickLabelOffset = offset;
    }
    
    /**
     * Returns the length of the tick marks (in Java2D units).  The default
     * value is <code>3.0</code>.
     * 
     * @return The length of the tick marks. 
     */
    public double getTickMarkLength() {
        return this.tickMarkLength;
    }
    
    /**
     * Sets the length of the tick marks and sends an {@link Axis3DChangeEvent}
     * to all registered listeners.  You can set this to <code>0.0</code> if
     * you prefer no tick marks to be displayed on the axis.
     * 
     * @param length  the length (in Java2D units). 
     */
    public void setTickMarkLength(double length) {
        this.tickMarkLength = length;
        fireChangeEvent(false);
    }

    /**
     * Returns the stroke used to draw the tick marks.  The default value is
     * <code>BasicStroke(0.5f)</code>.
     * 
     * @return The tick mark stroke (never <code>null</code>).
     */
    public Stroke getTickMarkStroke() {
        return this.tickMarkStroke;
    }
    
    /**
     * Sets the stroke used to draw the tick marks and sends an 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param stroke  the stroke (<code>null</code> not permitted). 
     */
    public void setTickMarkStroke(Stroke stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.tickMarkStroke = stroke;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the paint used to draw the tick marks.  The default value is
     * <code>Color.GRAY</code>.
     * 
     * @return The tick mark paint (never <code>null</code>). 
     */
    public Paint getTickMarkPaint() {
        return this.tickMarkPaint;
    }
    
    /**
     * Sets the paint used to draw the tick marks and sends an 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param paint  the paint (<code>null</code> not permitted). 
     */
    public void setTickMarkPaint(Paint paint) {
        ArgChecks.nullNotPermitted(paint, "paint");
        this.tickMarkPaint = paint;
        fireChangeEvent(false);
    }

    /**
     * Adjusts the range by adding the lower and upper margins and taking into
     * account also the 'autoRangeStickyZero' flag.
     * 
     * @param range  the range (<code>null</code> not permitted).
     * 
     * @return The adjusted range. 
     */
    @Override
    protected Range adjustedDataRange(Range range) {
        ArgChecks.nullNotPermitted(range, "range");
        double lm = range.getLength() * getLowerMargin();
        double um = range.getLength() * getUpperMargin();
        double lowerBound = range.getMin() - lm;
        double upperBound = range.getMax() + um;
        // does zero fall in the margins?
        if (this.autoRangeStickyZero) {
            if (0.0 <= range.getMin() && 0.0 > lowerBound) {
                lowerBound = 0.0;
            }
            if (0.0 >= range.getMax() && 0.0 < upperBound) {
                upperBound = 0.0;
            }
        }
        return new Range(lowerBound, upperBound);
    }
    
    /**
     * Draws the axis using the supplied graphics device, with the
     * specified starting and ending points for the line.  This method is used
     * internally, you should not need to call it directly.
     *
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param pt0  the starting point (<code>null</code> not permitted).
     * @param pt1  the ending point (<code>null</code> not permitted).
     * @param opposingPt  an opposing point (to determine which side of the 
     *     axis line the labels should appear, <code>null</code> not permitted).
     * @param tickData  tick details (<code>null</code> not permitted).
     */
    @Override
    public void draw(Graphics2D g2, Point2D pt0, Point2D pt1, 
            Point2D opposingPt, boolean labels, List<TickData> tickData) {
        
        if (!isVisible()) {
            return;
        }
        
        // draw a line for the axis
        g2.setStroke(getLineStroke());
        g2.setPaint(getLineColor());
        Line2D axisLine = new Line2D.Float(pt0, pt1);  
        g2.draw(axisLine);
        
        // draw the tick marks and labels
        double maxTickLabelWidth = 0.0;
        for (TickData t : tickData) {
            Line2D perpLine = Utils2D.createPerpendicularLine(axisLine, 
                    t.getAnchorPt(), this.tickMarkLength 
                    + this.tickLabelOffset, opposingPt);
            
            if (this.tickMarkLength > 0.0) {
                Line2D tickLine = Utils2D.createPerpendicularLine(axisLine, 
                       t.getAnchorPt(), this.tickMarkLength, 
                       opposingPt);
                g2.setPaint(this.tickMarkPaint);
                g2.setStroke(this.tickMarkStroke);
                g2.draw(tickLine);
            }
            
            if (getTickLabelsVisible()) {
                double theta = Utils2D.calculateTheta(axisLine);
                double thetaAdj = theta + Math.PI / 2.0;
                if (thetaAdj < -Math.PI / 2.0) {
                    thetaAdj = thetaAdj + Math.PI;
                }
                if (thetaAdj > Math.PI / 2.0) {
                    thetaAdj = thetaAdj - Math.PI;
                }

                double perpTheta = Utils2D.calculateTheta(perpLine);  
                TextAnchor textAnchor = TextAnchor.CENTER_LEFT;
                if (Math.abs(perpTheta) > Math.PI / 2.0) {
                    textAnchor = TextAnchor.CENTER_RIGHT;
                } 
                g2.setFont(getTickLabelFont());
                g2.setPaint(getTickLabelColor());
                String tickLabel = this.tickLabelFormatter.format(
                        t.getDataValue());
                maxTickLabelWidth = Math.max(maxTickLabelWidth, 
                        g2.getFontMetrics().stringWidth(tickLabel));
                TextUtils.drawRotatedString(tickLabel, g2, 
                        (float) perpLine.getX2(), (float) perpLine.getY2(), 
                        textAnchor, thetaAdj, textAnchor);
            }
        }

        // draw the axis label (if any)...
        if (getLabel() != null) {
            drawAxisLabel(g2, axisLine, opposingPt, maxTickLabelWidth 
                    + this.tickMarkLength + this.tickLabelOffset + 10);
        }
    }
    
    /**
     * Converts a data value to world coordinates, taking into account the
     * current axis range (assumes the world axis is zero-based and has the
     * specified length).
     * 
     * @param value  the data value (in axis units).
     * @param length  the length of the (zero based) world axis.
     * 
     * @return A world coordinate.
     */
    @Override
    public double translateToWorld(double value, double length) {
        return length * (value - getRange().getMin()) / getRange().getLength();
    }
  
    /**
     * Selects a tick size that is appropriate for drawing the axis from
     * <code>pt0</code> to <code>pt1</code>.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param pt0  the starting point for the axis.
     * @param pt1  the ending point for the axis.
     * @param opposingPt  a point on the opposite side of the line from where
     *     the labels should be drawn.
     */
    @Override
    public double selectTick(Graphics2D g2, Point2D pt0, Point2D pt1, 
            Point2D opposingPt) {
        
        if (this.tickSelector == null) {
            return this.tickSize;
        }
        
        // based on the font height, we can determine roughly how many tick
        // labels will fit in the length available
        double length = pt0.distance(pt1);
        g2.setFont(getTickLabelFont());
        int height = g2.getFontMetrics(getTickLabelFont()).getHeight();
        // the tickLabelFactor allows some control over how dense the labels
        // will be
        int maxTicks = (int) (length / (height * this.tickLabelFactor));
        if (maxTicks > 2 && this.tickSelector != null) {
            double rangeLength = getRange().getLength();
            this.tickSelector.select(rangeLength / 2.0);
            // step through until we have too many ticks OR we run out of 
            // tick sizes
            int tickCount = (int) (rangeLength 
                    / this.tickSelector.getCurrentTickSize());
            while (tickCount < maxTicks) {
                this.tickSelector.previous();
                tickCount = (int) (rangeLength
                        / this.tickSelector.getCurrentTickSize());
            }
            this.tickSelector.next();
            this.tickSize = this.tickSelector.getCurrentTickSize();
            this.tickLabelFormatter 
                    = this.tickSelector.getCurrentTickLabelFormat();
        } else {
            this.tickSize = Double.NaN;
        }
        return this.tickSize;
    }

    /**
     * Returns a list of tick info for the specified tick unit.
     * 
     * @param tickUnit  the tick unit.
     * 
     * @return A list of tick info. 
     */
    @Override
    public List<TickData> generateTickData(double tickUnit) {
        List<TickData> result = new ArrayList<TickData>();
        if (Double.isNaN(tickUnit)) {
            result.add(new TickData(0, getRange().getMin()));
            result.add(new TickData(1, getRange().getMax()));
        } else {
            double x = tickUnit * Math.ceil(this.range.getMin() / tickUnit);
            while (x <= this.range.getMax()) {
                result.add(new TickData(this.range.percent(x), x));
                x += tickUnit;
            }
        }
        return result;
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
        if (!(obj instanceof NumberAxis3D)) {
            return false;
        }
        NumberAxis3D that = (NumberAxis3D) obj;
        if (this.autoRangeIncludesZero != that.autoRangeIncludesZero) {
            return false;
        }
        if (this.autoRangeStickyZero != that.autoRangeStickyZero) {
            return false;
        }
        if (this.tickSize != that.tickSize) {
            return false;
        }
        if (!ObjectUtils.equals(this.tickSelector, that.tickSelector)) {
            return false;
        }
        if (!this.tickLabelFormatter.equals(that.tickLabelFormatter)) {
            return false;
        }
        if (this.tickLabelFactor != that.tickLabelFactor) {
            return false;
        }
        if (this.tickLabelOffset != that.tickLabelOffset) {
            return false;
        }
        if (this.tickMarkLength != that.tickMarkLength) {
            return false;
        }
        if (!ObjectUtils.equalsPaint(this.tickMarkPaint, that.tickMarkPaint)) {
            return false;
        }
        if (!this.tickMarkStroke.equals(that.tickMarkStroke)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Returns a hash code for this instance.
     * 
     * @return A hash code. 
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.tickSize) 
                ^ (Double.doubleToLongBits(this.tickSize) >>> 32));
        hash = 59 * hash + ObjectUtils.hashCode(this.tickLabelFormatter);
        return hash;
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
        SerialUtils.writePaint(this.tickMarkPaint, stream);
        SerialUtils.writeStroke(this.tickMarkStroke, stream);
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
        this.tickMarkPaint = SerialUtils.readPaint(stream);
        this.tickMarkStroke = SerialUtils.readStroke(stream);
    }
    
}
