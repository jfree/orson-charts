/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2016, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package com.orsoncharts.axis;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.font.LineMetrics;
import java.text.DecimalFormat;
import java.text.Format;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.orsoncharts.Chart3DHints;
import com.orsoncharts.Range;
import com.orsoncharts.graphics3d.RenderedElement;
import com.orsoncharts.graphics3d.RenderingInfo;
import com.orsoncharts.graphics3d.Utils2D;
import com.orsoncharts.interaction.InteractiveElementType;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.util.TextUtils;
import com.orsoncharts.util.TextAnchor;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ObjectUtils;

/**
 * A numerical axis for use with 3D plots (implements {@link ValueAxis3D}).
 * In a {@link CategoryPlot3D} the value axis (the vertical one) is numerical, 
 * and in an {@link XYZPlot} all the axes (x, y and z) are numerical - for
 * all these cases an instance of this class can be used.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
public class NumberAxis3D extends AbstractValueAxis3D implements ValueAxis3D,
        Serializable {

    /** 
     * A flag indicating whether or not the auto-range calculation should
     * include zero.
     */
    private boolean autoRangeIncludesZero;
    
    /**
     * A flag that controls how zero is handled when it falls within the
     * margins.  If {@code true}, the margin is truncated at zero, if
     * {@code false} the margin is not changed.
     */
    private boolean autoRangeStickyZero;
        
    /** 
     * The tick selector (if not {@code null}, then auto-tick selection is 
     * used). 
     */
    private TickSelector tickSelector;

    /** 
     * The tick size.  If the tickSelector is not {@code null} then it is 
     * used to auto-select an appropriate tick size and format.
     */
    private double tickSize;

    /** The tick formatter (never {@code null}). */
    private Format tickLabelFormatter;

    /**
     * Creates a new axis with the specified label and default attributes.
     * 
     * @param label  the axis label ({@code null} permitted). 
     */
    public NumberAxis3D(String label) {
        this(label, new Range(0.0, 1.0));
    }
    
    /**
     * Creates a new axis with the specified label and range.
     *
     * @param label  the axis label ({@code null} permitted).
     * @param range  the range ({@code null} not permitted).
     */
    public NumberAxis3D(String label, Range range) {
        super(label, range);
        this.autoRangeIncludesZero = false;
        this.autoRangeStickyZero = true;
        this.tickSelector = new NumberTickSelector();
        this.tickSize = range.getLength() / 10.0;
        this.tickLabelFormatter = new DecimalFormat("0.00");
    }
      
    /**
     * Returns the flag that determines whether or not the auto range 
     * mechanism should force zero to be included in the range.  The default
     * value is {@code false}.
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
     * is {@code true}.
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
     * when zero falls into the axis margins.  If {@code true}, when
     * zero is in the axis margin the axis range is truncated at zero.  If
     * {@code false}, there is no special treatment.
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
     * @param selector  the selector ({@code null} permitted).
     * 
     * @see #getTickSelector() 
     */
    public void setTickSelector(TickSelector selector) {
        this.tickSelector = selector;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the tick size (to be used when the tick selector is 
     * {@code null}).
     * 
     * @return The tick size.
     */
    public double getTickSize() {
        return this.tickSize;
    }

    /**
     * Sets the tick size and sends an {@link Axis3DChangeEvent} to all 
     * registered listeners.
     * 
     * @param tickSize  the new tick size.
     */
    public void setTickSize(double tickSize) {
        this.tickSize = tickSize;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the tick label formatter.  The default value is
     * {@code DecimalFormat("0.00")}.
     * 
     * @return The tick label formatter (never {@code null}). 
     */
    public Format getTickLabelFormatter() {
        return this.tickLabelFormatter;
    }
    
    /**
     * Sets the formatter for the tick labels and sends an 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param formatter  the formatter ({@code null} not permitted).
     */
    public void setTickLabelFormatter(Format formatter) {
        ArgChecks.nullNotPermitted(formatter, "formatter");
        this.tickLabelFormatter = formatter;
        fireChangeEvent(false);
    }
    
    /**
     * Adjusts the range by adding the lower and upper margins and taking into
     * account also the {@code autoRangeStickyZero} flag.
     * 
     * @param range  the range ({@code null} not permitted).
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
        if ((upperBound - lowerBound) < getMinAutoRangeLength()) {
            double adj = (getMinAutoRangeLength() - (upperBound - lowerBound)) 
                    / 2.0;
            lowerBound -= adj;
            upperBound += adj;
        }
        return new Range(lowerBound, upperBound);
    }
    
    /**
     * Draws the axis to the supplied graphics target ({@code g2}, with the
     * specified starting and ending points for the line.  This method is used
     * internally, you should not need to call it directly.
     *
     * @param g2  the graphics target ({@code null} not permitted).
     * @param pt0  the starting point ({@code null} not permitted).
     * @param pt1  the ending point ({@code null} not permitted).
     * @param opposingPt  an opposing point (to determine which side of the 
     *     axis line the labels should appear, {@code null} not permitted).
     * @param tickData  tick details ({@code null} not permitted).
     * @param info  an object to be populated with rendering info 
     *     ({@code null} permitted).
     * @param hinting  perform element hinting?
     */
    @Override
    public void draw(Graphics2D g2, Point2D pt0, Point2D pt1, 
            Point2D opposingPt, List<TickData> tickData, RenderingInfo info,
            boolean hinting) {
        
        if (!isVisible()) {
            return;
        }
        if (pt0.equals(pt1)) {
            return;
        }
        
        // draw a line for the axis
        g2.setStroke(getLineStroke());
        g2.setPaint(getLineColor());
        Line2D axisLine = new Line2D.Float(pt0, pt1);  
        g2.draw(axisLine);
        
        // draw the tick marks and labels
        g2.setFont(getTickLabelFont());
        // we track the max width or height of the labels to know how far to
        // offset the axis label when we draw it later
        double maxTickLabelDim = 0.0;
        if (getTickLabelOrientation().equals(LabelOrientation.PARALLEL)) {
            LineMetrics lm = g2.getFontMetrics().getLineMetrics("123", g2);
            maxTickLabelDim = lm.getHeight();
        }
        double tickMarkLength = getTickMarkLength();
        double tickLabelOffset = getTickLabelOffset();
        g2.setPaint(getTickMarkPaint());
        g2.setStroke(getTickMarkStroke());
        for (TickData t : tickData) {
            if (tickMarkLength > 0.0) {
                Line2D tickLine = Utils2D.createPerpendicularLine(axisLine, 
                       t.getAnchorPt(), tickMarkLength, opposingPt);
                g2.draw(tickLine);
            }
            String tickLabel = this.tickLabelFormatter.format(t.getDataValue());
            if (getTickLabelOrientation().equals(
                    LabelOrientation.PERPENDICULAR)) {
                maxTickLabelDim = Math.max(maxTickLabelDim, 
                        g2.getFontMetrics().stringWidth(tickLabel));
            }
        }
            
        if (getTickLabelsVisible()) {
            g2.setPaint(getTickLabelColor());
            if (getTickLabelOrientation().equals(
                    LabelOrientation.PERPENDICULAR)) {
                drawPerpendicularTickLabels(g2, axisLine, opposingPt, tickData,
                        info, hinting);
            } else {
                drawParallelTickLabels(g2, axisLine, opposingPt, tickData, 
                        info, hinting);
            }
        } else {
            maxTickLabelDim = 0.0;
        }

        // draw the axis label (if any)...
        if (getLabel() != null) {
            Shape labelBounds = drawAxisLabel(getLabel(), g2, axisLine, 
                    opposingPt, maxTickLabelDim + tickMarkLength 
                    + tickLabelOffset + getLabelOffset(), info, hinting);
        }
    }
    
    /**
     * Draws tick labels parallel to the axis.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param axisLine  the axis line ({@code null} not permitted).
     * @param opposingPt  an opposing point (to determine on which side the 
     *     labels appear, {@code null} not permitted).
     * @param tickData  the tick data ({@code null} not permitted).
     * @param info  if not {@code null} this object will be updated with
     *     {@link RenderedElement} instances for each of the tick labels.
     */
    private void drawParallelTickLabels(Graphics2D g2, Line2D axisLine,
            Point2D opposingPt, List<TickData> tickData, RenderingInfo info,
            boolean hinting) {
        
        g2.setFont(getTickLabelFont());
        double halfAscent = g2.getFontMetrics().getAscent() / 2.0;
        for (TickData t : tickData) {
            Line2D perpLine = Utils2D.createPerpendicularLine(axisLine, 
                    t.getAnchorPt(), getTickMarkLength()
                    + getTickLabelOffset() + halfAscent, opposingPt);
            double axisTheta = Utils2D.calculateTheta(axisLine);
            TextAnchor textAnchor = TextAnchor.CENTER;
            if (axisTheta >= Math.PI / 2.0) {
                axisTheta = axisTheta - Math.PI;
            } else if (axisTheta <= -Math.PI / 2) {
                axisTheta = axisTheta + Math.PI;  
            }
            String tickLabel = this.tickLabelFormatter.format(
                    t.getDataValue());
            if (hinting) {
                Map<String, String> m = new HashMap<String, String>();
                m.put("ref", "{\"type\": \"valueTickLabel\", \"axis\": \"" 
                        + axisStr() + "\", \"value\": \"" 
                        + t.getDataValue() + "\"}");
                g2.setRenderingHint(Chart3DHints.KEY_BEGIN_ELEMENT, m);
            }
            Shape bounds = TextUtils.drawRotatedString(tickLabel, g2, 
                    (float) perpLine.getX2(), (float) perpLine.getY2(), 
                    textAnchor, axisTheta, textAnchor);
            if (hinting) {
                g2.setRenderingHint(Chart3DHints.KEY_END_ELEMENT, true);
            }
            if (info != null) {
                RenderedElement tickLabelElement = new RenderedElement(
                        InteractiveElementType.VALUE_AXIS_TICK_LABEL, bounds);
                tickLabelElement.setProperty("axis", axisStr());
                tickLabelElement.setProperty("value", 
                        Double.valueOf(t.getDataValue()));
                info.addOffsetElement(tickLabelElement);
            }
        }
    }
    
    /**
     * Draws tick labels perpendicular to the axis.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param axisLine  the axis line ({@code null} not permitted).
     * @param opposingPt  an opposing point (to determine on which side the 
     *     labels appear, {@code null} not permitted).
     * @param tickData  the tick data ({@code null} not permitted).
     * @param info  if not {@code null} this object will be updated with
     *     {@link RenderedElement} instances for each of the tick labels.
     */
    private void drawPerpendicularTickLabels(Graphics2D g2, Line2D axisLine,
            Point2D opposingPt, List<TickData> tickData, RenderingInfo info,
            boolean hinting) {
        for (TickData t : tickData) {
            double theta = Utils2D.calculateTheta(axisLine);
            double thetaAdj = theta + Math.PI / 2.0;
            if (thetaAdj < -Math.PI / 2.0) {
                thetaAdj = thetaAdj + Math.PI;
            }
            if (thetaAdj > Math.PI / 2.0) {
                thetaAdj = thetaAdj - Math.PI;
            }

            Line2D perpLine = Utils2D.createPerpendicularLine(axisLine, 
                    t.getAnchorPt(), getTickMarkLength() + getTickLabelOffset(), 
                    opposingPt);
            double perpTheta = Utils2D.calculateTheta(perpLine);  
            TextAnchor textAnchor = TextAnchor.CENTER_LEFT;
            if (Math.abs(perpTheta) > Math.PI / 2.0) {
                textAnchor = TextAnchor.CENTER_RIGHT;
            } 
            String tickLabel = this.tickLabelFormatter.format(
                    t.getDataValue());
            if (hinting) {
                Map<String, String> m = new HashMap<String, String>();
                m.put("ref", "{\"type\": \"valueTickLabel\", \"axis\": \"" 
                        + axisStr() + "\", \"value\": \"" 
                        + t.getDataValue() + "\"}");
                g2.setRenderingHint(Chart3DHints.KEY_BEGIN_ELEMENT, m);
            }
            Shape bounds = TextUtils.drawRotatedString(tickLabel, g2, 
                    (float) perpLine.getX2(), (float) perpLine.getY2(), 
                    textAnchor, thetaAdj, textAnchor);
            if (hinting) {
                g2.setRenderingHint(Chart3DHints.KEY_END_ELEMENT, true);
            }
            if (info != null) {
                RenderedElement tickLabelElement = new RenderedElement(
                        InteractiveElementType.VALUE_AXIS_TICK_LABEL, bounds);
                tickLabelElement.setProperty("axis", axisStr());
                tickLabelElement.setProperty("value", 
                        Double.valueOf(t.getDataValue()));
                info.addOffsetElement(tickLabelElement);
            }
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
        double p = getRange().percent(value, isInverted());
        return length * p;
    }
  
    /**
     * Selects a tick size that is appropriate for drawing the axis from
     * {@code pt0} to {@code pt1}.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
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
        g2.setFont(getTickLabelFont()); 
        FontMetrics fm = g2.getFontMetrics(getTickLabelFont());        
        double length = pt0.distance(pt1);
        LabelOrientation orientation = getTickLabelOrientation();
        if (orientation.equals(LabelOrientation.PERPENDICULAR)) {
            // based on the font height, we can determine roughly how many tick
            // labels will fit in the length available
            double height = fm.getHeight();
            // the tickLabelFactor allows some control over how dense the labels
            // will be
            int maxTicks = (int) (length / (height * getTickLabelFactor()));
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
        } else if (orientation.equals(LabelOrientation.PARALLEL)) {
            // choose a unit that is at least as large as the length of the axis
            this.tickSelector.select(getRange().getLength());
            boolean done = false;
            while (!done) {
                if (this.tickSelector.previous()) {
                    // estimate the label widths, and do they overlap?
                    Format f = this.tickSelector.getCurrentTickLabelFormat();
                    String s0 = f.format(this.range.getMin());
                    String s1 = f.format(this.range.getMax());
                    double w0 = fm.stringWidth(s0);
                    double w1 = fm.stringWidth(s1);
                    double w = Math.max(w0, w1);
                    int n = (int) (length / (w * this.getTickLabelFactor()));
                    if (n < getRange().getLength() 
                            / tickSelector.getCurrentTickSize()) {
                        tickSelector.next();
                        done = true;
                    }
                } else {
                    done = true;
                }
            }
            this.tickSize = this.tickSelector.getCurrentTickSize();
            this.tickLabelFormatter 
                    = this.tickSelector.getCurrentTickLabelFormat();
        }
        return this.tickSize;
    }

    /**
     * Generates a list of tick data items for the specified tick unit.  This
     * data will be passed to the 3D engine and will be updated with a 2D
     * projection that can later be used to write the axis tick labels in the
     * appropriate places.
     * <br><br>
     * If {@code tickUnit} is {@code Double.NaN}, then tick data is
     * generated for just the bounds of the axis.
     * 
     * @param tickUnit  the tick unit.
     * 
     * @return A list of tick data (never {@code null}). 
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
                result.add(new TickData(this.range.percent(x, isInverted()), 
                        x));
                x += tickUnit;
            }
        }
        return result;
    }

    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object to test against ({@code null} permitted).
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
    
}
