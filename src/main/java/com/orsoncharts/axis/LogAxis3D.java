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

import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.text.AttributedString;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import com.orsoncharts.Range;
import com.orsoncharts.graphics3d.Utils2D;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ObjectUtils;
import com.orsoncharts.util.TextAnchor;
import com.orsoncharts.util.TextUtils;

/**
 * A numerical axis with a logarithmic scale.
 * 
 * @since 1.2
 */
public class LogAxis3D extends AbstractValueAxis3D implements ValueAxis3D {
    
    /** The default value for the smallest value attribute. */
    public static final double DEFAULT_SMALLEST_VALUE = 1E-100;
    
    /** The logarithm base. */
    private double base = 10.0;

    /** The logarithm of the base value - cached for performance. */
    private double baseLog;

    /** The logarithms of the current axis range. */
    private Range logRange;

    /** 
     * The smallest value for the axis.  In general, only positive values 
     * can be plotted against a log axis but to simplify the generation of
     * bar charts (where the base of the bars is typically at 0.0) the axis
     * will return <code>smallestValue</code> as the translated value for 0.0.
     * It is important to make sure there are no real data values smaller 
     * than this value.
     */
    private double smallestValue;
    
    /** 
     * The symbol used to represent the log base on the tick labels.  If this
     * is <code>null</code> the numerical value will be displayed. 
     */
    private String baseSymbol;
    
    /**
     * The number formatter for the base value.
     */
    private NumberFormat baseFormatter = new DecimalFormat("0");
    
    /** 
     * The tick selector (if not <code>null</code>, then auto-tick selection 
     * is used). 
     */
    private TickSelector tickSelector = new NumberTickSelector();

    /** 
     * The tick size.  If the tickSelector is not <code>null</code> then it is 
     * used to auto-select an appropriate tick size and format.
     */
    private double tickSize = 1.0;

    /** The tick formatter (never <code>null</code>). */
    private Format tickLabelFormatter = new DecimalFormat("0.0");
    
    /**
     * Creates a new log axis with a default base of 10.0.
     * 
     * @param label  the axis label (<code>null</code> permitted). 
     */
    public LogAxis3D(String label) {
        super(label, new Range(DEFAULT_SMALLEST_VALUE, 1.0));
        this.base = 10.0;
        this.baseLog = Math.log(this.base);
        this.logRange = new Range(calculateLog(DEFAULT_SMALLEST_VALUE), 
                calculateLog(1.0));
        this.smallestValue = DEFAULT_SMALLEST_VALUE;
    }

    /**
     * Returns the logarithmic base value.  The default is 10.0.
     * 
     * @return The logarithmic base value. 
     */
    public double getBase() {
        return this.base;
    }
    
    /**
     * Sets the logarithmic base value and sends a change event to all
     * registered listeners.
     * 
     * @param base  the base value. 
     */
    public void setBase(double base) {
        this.base = base;
        this.baseLog = Math.log(base);
        fireChangeEvent(true);
    }
    
    /**
     * Returns the base symbol, used in tick labels for the axis.  A typical 
     * value would be "e" when using a natural logarithm scale.  If this is
     * <code>null</code>, the tick labels will display the numerical base
     * value.  The default value is <code>null</code>.
     * 
     * @return The base symbol (possibly <code>null</code>). 
     */
    public String getBaseSymbol() {
        return this.baseSymbol;
    }
    
    /**
     * Sets the base symbol and sends a change event to all registered 
     * listeners.  If you set this to <code>null</code>, the tick labels will
     * display a numerical representation of the base value.
     * 
     * @param symbol  the base symbol (<code>null</code> permitted).
     */
    public void setBaseSymbol(String symbol) {
        this.baseSymbol = symbol;
        fireChangeEvent(false);
    }

    /**
     * Returns the formatter used for the log base value when it is displayed 
     * in tick labels.  The default value is <code>NumberFormat("0")</code>.
     * 
     * @return The base formatter (never <code>null</code>).
     */
    public NumberFormat getBaseFormatter() {
        return this.baseFormatter;
    }
    
    /**
     * Sets the formatter for the log base value and sends a change event to
     * all registered listeners.
     * 
     * @param formatter  the formatter (<code>null</code> not permitted). 
     */
    public void setBaseFormatter(NumberFormat formatter) {
        ArgChecks.nullNotPermitted(formatter, "formatter");
        this.baseFormatter = formatter;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the smallest positive data value that will be represented on 
     * the axis.  This will be used as the lower bound for the axis if the
     * data range contains any value from 0.0 up to this value.
     * 
     * @return The smallest value. 
     */
    public double getSmallestValue() {
        return this.smallestValue;
    }
    
    /**
     * Sets the smallest positive data value that will be represented on the 
     * axis and sends a change event to all registered listeners.
     * 
     * @param smallestValue  the value (must be positive). 
     */
    public void setSmallestValue(double smallestValue) {
        ArgChecks.positiveRequired(smallestValue, "smallestValue");
        this.smallestValue = smallestValue;
        fireChangeEvent(true);
    }

    /**
     * Returns the tick selector for the axis.
     * 
     * @return The tick selector (possibly <code>null</code>). 
     */
    public TickSelector getTickSelector() {
        return this.tickSelector;    
    }
    
    /**
     * Sets the tick selector and sends a change event to all registered
     * listeners.
     * 
     * @param selector  the selector (<code>null</code> permitted).
     */
    public void setTickSelector(TickSelector selector) {
        this.tickSelector = selector;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the tick size to be used when the tick selector is 
     * <code>null</code>.
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
     * <code>DecimalFormat("0.0")</code>.
     * 
     * @return The tick label formatter (never <code>null</code>). 
     */
    public Format getTickLabelFormatter() {
        return this.tickLabelFormatter;
    }
    
    /**
     * Sets the formatter for the tick labels and sends a change event
     * to all registered listeners.
     * 
     * @param formatter  the formatter (<code>null</code> not permitted).
     */
    public void setTickLabelFormatter(Format formatter) {
        ArgChecks.nullNotPermitted(formatter, "formatter");
        this.tickLabelFormatter = formatter;
        fireChangeEvent(false);
    }
    
    /**
     * Sets the range for the axis.  This method is overridden to check that 
     * the range does not contain negative values, and to update the log values
     * for the range.
     * 
     * @param range  the range (<code>null</code> not permitted). 
     */
    @Override
    public void setRange(Range range) {
        ArgChecks.nullNotPermitted(range, "range");
        this.range = new Range(Math.max(range.getMin(), this.smallestValue), 
                range.getMax());
        this.logRange = new Range(calculateLog(this.range.getMin()), 
                calculateLog(this.range.getMax()));
        fireChangeEvent(true);
    }

    /**
     * Sets the range for the axis.  This method is overridden to check that 
     * the range does not contain negative values, and to update the log values
     * for the range.
     * 
     * @param range  the range (<code>null</code> not permitted). 
     */
    @Override
    public void setRange(double min, double max) {
        ArgChecks.negativeNotPermitted(min, "min");
        this.range = new Range(Math.max(min, this.smallestValue), max);
        this.logRange = new Range(calculateLog(this.range.getMin()), 
                calculateLog(this.range.getMax()));
        fireChangeEvent(true);
    }

    @Override
    protected void updateRange(Range range) {
        this.range = range;
        this.logRange = new Range(calculateLog(this.range.getMin()), 
                calculateLog(this.range.getMax()));
    }

    /**
     * Calculates the log of the given <code>value</code>, using the current 
     * base.
     *
     * @param value  the value (negatives not permitted).
     *
     * @return The log of the given value.
     *
     * @see #calculateValue(double)
     * @see #getBase()
     */
    public final double calculateLog(double value) {
        return Math.log(value) / this.baseLog;
    }
    
    /**
     * Calculates the value from a given log value.
     *
     * @param log  the log value.
     *
     * @return The value with the given log.
     *
     * @see #calculateLog(double)
     * @see #getBase()
     */
    public final double calculateValue(double log) {
        return Math.pow(this.base, log);
    }

    /**
     * Translates a data value to a world coordinate, assuming that the axis
     * begins at the origin and has the specified length.
     * 
     * @param value  the data value.
     * @param length  the axis length in world coordinates.
     * 
     * @return The world coordinate of this data value on the axis. 
     */
    @Override
    public double translateToWorld(double value, double length) {
        double logv = calculateLog(value);
        double percent = this.logRange.percent(logv);
        return percent * length;
    }

    /**
     * Draws the axis.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param startPt  the starting point.
     * @param endPt  the ending point.
     * @param opposingPt  an opposing point (labels will be on the other side 
     *     of the line).
     * @param tickData  the tick data (including anchor points calculated by
     *     the 3D engine).
     */
    @Override
    public void draw(Graphics2D g2, Point2D startPt, Point2D endPt, 
            Point2D opposingPt, List<TickData> tickData) {
        
        if (!isVisible()) {
            return;
        }

        // draw a line for the axis
        g2.setStroke(getLineStroke());
        g2.setPaint(getLineColor());
        Line2D axisLine = new Line2D.Float(startPt, endPt);  
        g2.draw(axisLine);
        
        // draw the tick marks and labels
        double maxTickLabelWidth = 0.0;
        double tickMarkLength = getTickMarkLength();
        double tickLabelOffset = getTickLabelOffset();
        g2.setPaint(getTickMarkPaint());
        g2.setStroke(getTickMarkStroke());
        g2.setFont(getTickLabelFont());
        for (TickData t : tickData) {
            if (tickMarkLength > 0.0) {
                Line2D tickLine = Utils2D.createPerpendicularLine(axisLine, 
                       t.getAnchorPt(), tickMarkLength, opposingPt);
                g2.draw(tickLine);
            }
        }
        
        if (getTickLabelsVisible()) {
            g2.setPaint(getTickLabelColor());
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
                        t.getAnchorPt(), tickMarkLength + tickLabelOffset, 
                        opposingPt);
                double perpTheta = Utils2D.calculateTheta(perpLine);  
                TextAnchor textAnchor = TextAnchor.CENTER_LEFT;
                if (Math.abs(perpTheta) > Math.PI / 2.0) {
                    textAnchor = TextAnchor.CENTER_RIGHT;
                } 
                double logy = calculateLog(t.getDataValue());
                AttributedString as = createTickLabelAttributedString(logy);
                Rectangle2D bounds = new Rectangle2D.Double();
                TextUtils.drawRotatedString(as, g2, 
                        (float) perpLine.getX2(), (float) perpLine.getY2(), 
                        textAnchor, thetaAdj, textAnchor, bounds);
                maxTickLabelWidth = Math.max(maxTickLabelWidth, 
                        bounds.getWidth());
            }
        }

        // draw the axis label (if any)...
        if (getLabel() != null) {
            drawAxisLabel(g2, axisLine, opposingPt, maxTickLabelWidth 
                    + tickMarkLength + tickLabelOffset + 10);
        }
    }

    private AttributedString createTickLabelAttributedString(double logy) {
        String baseStr = this.baseSymbol;
        if (baseStr == null) {
            baseStr = this.baseFormatter.format(this.base);
        }
        String exponentStr = this.tickLabelFormatter.format(logy);
        AttributedString as = new AttributedString(baseStr + exponentStr);
        as.addAttributes(getTickLabelFont().getAttributes(), 0, (baseStr 
                + exponentStr).length());
        as.addAttribute(TextAttribute.SUPERSCRIPT, 
                TextAttribute.SUPERSCRIPT_SUPER, baseStr.length(), 
                baseStr.length() + exponentStr.length());
        return as;   
    }
    
    /**
     * Adjusts the range by adding the lower and upper margins on the 
     * logarithmic range.
     * 
     * @param range  the range (<code>null</code> not permitted).
     * 
     * @return The adjusted range. 
     */
    @Override
    protected Range adjustedDataRange(Range range) {
        ArgChecks.nullNotPermitted(range, "range");
        double logmin = calculateLog(Math.max(range.getMin(), 
                this.smallestValue));
        double logmax = calculateLog(range.getMax());
        double length = logmax - logmin;
        double lm = length * getLowerMargin();
        double um = length * getUpperMargin();
        double lowerBound = calculateValue(logmin - lm);
        double upperBound = calculateValue(logmax + um);
        return new Range(lowerBound, upperBound);
    }
    
    /**
     * Selects a standard tick unit on the logarithmic range.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param pt0  the starting point.
     * @param pt1  the ending point.
     * @param opposingPt  an opposing point.
     * 
     * @return The tick unit (log increment).
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
        int maxTicks = (int) (length / (height * getTickLabelFactor()));
        if (maxTicks > 2 && this.tickSelector != null) {
            double rangeLength = this.logRange.getLength();
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
     * Generates tick data for the axis, assuming the specified tick unit
     * (a log increment in this case).  If the tick unit is Double.NaN then
     * ticks will be added for the bounds of the axis only.
     * 
     * @param tickUnit  the tick unit.
     * 
     * @return A list of tick data items. 
     */
    @Override
    public List<TickData> generateTickData(double tickUnit) {
        List<TickData> result = new ArrayList<TickData>();
        if (Double.isNaN(tickUnit)) {
            result.add(new TickData(0, getRange().getMin()));
            result.add(new TickData(1, getRange().getMax()));
        } else {
            double logx = tickUnit 
                    * Math.ceil(this.logRange.getMin() / tickUnit);
            while (logx <= this.logRange.getMax()) {
                result.add(new TickData(this.logRange.percent(logx), 
                        calculateValue(logx)));
                logx += tickUnit;
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.base) 
                ^ (Double.doubleToLongBits(this.base) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.smallestValue) 
                ^ (Double.doubleToLongBits(this.smallestValue) >>> 32));
        hash = 59 * hash + ObjectUtils.hashCode(this.baseSymbol);
        hash = 59 * hash + ObjectUtils.hashCode(this.baseFormatter);
        hash = 59 * hash + ObjectUtils.hashCode(this.tickSelector);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.tickSize) 
                ^ (Double.doubleToLongBits(this.tickSize) >>> 32));
        hash = 59 * hash + ObjectUtils.hashCode(this.tickLabelFormatter);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LogAxis3D other = (LogAxis3D) obj;
        if (Double.doubleToLongBits(this.base) 
                != Double.doubleToLongBits(other.base)) {
            return false;
        }
        if (Double.doubleToLongBits(this.smallestValue) 
                != Double.doubleToLongBits(other.smallestValue)) {
            return false;
        }
        if (!ObjectUtils.equals(this.baseSymbol, other.baseSymbol)) {
            return false;
        }
        if (!ObjectUtils.equals(this.baseFormatter, other.baseFormatter)) {
            return false;
        }
        if (!ObjectUtils.equals(this.tickSelector, other.tickSelector)) {
            return false;
        }
        if (Double.doubleToLongBits(this.tickSize) 
                != Double.doubleToLongBits(other.tickSize)) {
            return false;
        }
        if (!ObjectUtils.equals(this.tickLabelFormatter, 
                other.tickLabelFormatter)) {
            return false;
        }
        return super.equals(obj);
    }

}
