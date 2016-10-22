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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.LineMetrics;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.orsoncharts.Chart3DHints;
import com.orsoncharts.ChartElementVisitor;
import com.orsoncharts.Range;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.graphics3d.RenderedElement;
import com.orsoncharts.graphics3d.RenderingInfo;
import com.orsoncharts.graphics3d.Utils2D;
import com.orsoncharts.interaction.InteractiveElementType;
import com.orsoncharts.label.CategoryLabelGenerator;
import com.orsoncharts.label.StandardCategoryLabelGenerator;
import com.orsoncharts.marker.CategoryMarker;
import com.orsoncharts.marker.CategoryMarkerType;
import com.orsoncharts.marker.Marker;
import com.orsoncharts.marker.MarkerData;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.category.AreaRenderer3D;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ObjectUtils;
import com.orsoncharts.util.SerialUtils;
import com.orsoncharts.util.TextAnchor;
import com.orsoncharts.util.TextUtils;

/**
 * An axis that displays categories.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
public class StandardCategoryAxis3D extends AbstractAxis3D 
        implements CategoryAxis3D, Serializable {
    
    /** The categories. */
    private List<Comparable<?>> categories;
  
    /** 
     * The axis range (never {@code null}). 
     */
    private Range range;
    
    private boolean inverted;

    /** The percentage margin to leave at the lower end of the axis. */
    private double lowerMargin;
    
    /** The percentage margin to leave at the upper end of the axis. */
    private double upperMargin;

    /** 
     * Hide half of the first category?  This brings the category label 
     * closer to the beginning of the axis.  It is useful if the renderer 
     * doesn't make full use of the category space for the first item.
     */
    private boolean firstCategoryHalfWidth = false;
    
    /** 
     * Hide half of the last category?  This brings the category label 
     * closer to the end of the axis.  It is useful if the renderer 
     * doesn't make full use of the category space for the last item.
     */
    private boolean lastCategoryHalfWidth = false;
        
    /** 
     * The tick mark length (in Java2D units).  When this is 0.0, no tick
     * marks will be drawn.
     */
    private double tickMarkLength;
    
    /** The tick mark stroke (never {@code null}). */
    private transient Stroke tickMarkStroke;
    
    /** The tick mark paint (never {@code null}). */
    private transient Paint tickMarkPaint;
    
    /** The tick label generator. */
    private CategoryLabelGenerator tickLabelGenerator;
    
    /** 
     * The tick label offset (in Java2D units).  This is the gap between the
     * tick marks and their associated labels.
     */
    private double tickLabelOffset;
    
    /** The orientation for the tick labels. */
    private LabelOrientation tickLabelOrientation;

    /** 
     * The maximum number of offset levels to use for tick labels on the axis. 
     */
    private int maxTickLabelLevels = 3;
    
    /**
     * The tick label factor (used as a multiplier for the tick label width
     * when checking for overlapping labels).  
     */
    private double tickLabelFactor = 1.2;
    
    /** 
     * The markers for the axis (this may be empty, but not {@code null}). 
     */
    private Map<String, CategoryMarker> markers;
    
    /** A flag to indicate that this axis has been configured as a row axis. */
    private boolean isRowAxis;
    
    /** 
     * A flag to indicate that this axis has been configured as a column 
     * axis. 
     */
    private boolean isColumnAxis;
    
    /**
     * Default constructor.
     */
    public StandardCategoryAxis3D() {
        this(null);
    }
 
    /**
     * Creates a new axis with the specified label.
     * 
     * @param label  the axis label ({@code null} permitted). 
     */
    public StandardCategoryAxis3D(String label) {
        super(label);
        this.categories = new ArrayList<Comparable<?>>();
        this.range = new Range(0.0, 1.0);
        this.lowerMargin = 0.05;
        this.upperMargin = 0.05;
        this.firstCategoryHalfWidth = false;
        this.lastCategoryHalfWidth = false;
        this.tickMarkLength = 3.0;
        this.tickMarkPaint = Color.GRAY;
        this.tickMarkStroke = new BasicStroke(0.5f);
        this.tickLabelGenerator = new StandardCategoryLabelGenerator();
        this.tickLabelOffset = 5.0;
        this.tickLabelOrientation = LabelOrientation.PARALLEL;
        this.tickLabelFactor = 1.4;
        this.maxTickLabelLevels = 3;
        this.markers = new LinkedHashMap<String, CategoryMarker>();
        this.isRowAxis = false;
        this.isColumnAxis = false;
    }

    /**
     * Returns {@code true} if this axis has been configured as a 
     * row axis for the plot that it belongs to, and {@code false} 
     * otherwise.
     * 
     * @return A boolean.
     * 
     * @since 1.3
     */
    @Override
    public boolean isRowAxis() {
        return isRowAxis;
    }

    /**
     * Returns {@code true} if this axis has been configured as a
     * column axis for the plot that it belongs to, and {@code false}
     * otherwise.
     * 
     * @return A boolean.
     * 
     * @since 1.3
     */
    @Override
    public boolean isColumnAxis() {
        return isColumnAxis;
    }

    /**
     * Returns the range for the axis.  By convention, the category axes have
     * a range from 0.0 to 1.0.
     * 
     * @return The range. 
     */
    @Override
    public Range getRange() {
        return this.range;
    }

    /**
     * Sets the range for the axis and sends an {@link Axis3DChangeEvent} to
     * all registered listeners.
     * 
     * @param lowerBound  the lower bound.
     * @param upperBound  the upper bound.
     */
    @Override
    public void setRange(double lowerBound, double upperBound) {
        setRange(new Range(lowerBound, upperBound));
    }

    /**
     * Sets the range for the axis and sends an {@link Axis3DChangeEvent} to
     * all registered listeners. Note that changing the range for the 
     * category axis will have no visible effect.
     * 
     * @param range  the range ({@code null} not permitted). 
     */
    @Override
    public void setRange(Range range) {
        ArgChecks.nullNotPermitted(range, "range");
        this.range = range;
        fireChangeEvent(true);
    }
    
    /**
     * Returns the margin to leave at the lower end of the axis, as a 
     * percentage of the axis length.  The default is {@code 0.05} (five 
     * percent).
     * 
     * @return The lower margin.
     */
    public double getLowerMargin() {
        return this.lowerMargin;
    }
    
    /**
     * Sets the margin to leave at the lower end of the axis and sends an
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param margin  the margin. 
     */
    public void setLowerMargin(double margin) {
        this.lowerMargin = margin;
        fireChangeEvent(true);
    }
    
    /**
     * Returns the margin to leave at the upper end of the axis, as a 
     * percentage of the axis length.  The default is {@code 0.05} (five 
     * percent).
     * 
     * @return The lower margin.
     */
    public double getUpperMargin() {
        return this.upperMargin;
    }
    
    /**
     * Sets the margin to leave at the upper end of the axis and sends an
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param margin  the margin. 
     */
    public void setUpperMargin(double margin) {
        this.upperMargin = margin;
        fireChangeEvent(true);
    }
    
    /**
     * Returns {@code true} if the first category on the axis should
     * occupy half the normal width, and {@code false} otherwise.
     * 
     * @return A boolean.
     * 
     * @see #setFirstCategoryHalfWidth(boolean) 
     */
    public boolean isFirstCategoryHalfWidth() {
        return this.firstCategoryHalfWidth;
    }
    
    /**
     * Sets the flag that controls whether the first category on the axis 
     * occupies a full or half width, and sends an {@link Axis3DChangeEvent}
     * to all registered listeners.  There are some renderers where the 
     * charts look better when half-widths are used (for example,
     * {@link AreaRenderer3D}).
     * 
     * @param half  half width?
     * 
     * @see #setLastCategoryHalfWidth(boolean) 
     */
    public void setFirstCategoryHalfWidth(boolean half) {
        this.firstCategoryHalfWidth = half;
        fireChangeEvent(true);
    }
    
    /**
     * Returns {@code true} if the last category on the axis should
     * occupy half the normal width, and {@code false} otherwise.
     * 
     * @return A boolean.
     * 
     * @see #setLastCategoryHalfWidth(boolean) 
     */
    public boolean isLastCategoryHalfWidth() {
        return this.lastCategoryHalfWidth;
    }
    
    /**
     * Sets the flag that controls whether the last category on the axis 
     * occupies a full or half width, and sends an {@link Axis3DChangeEvent}
     * to all registered listeners.  There are some renderers where the 
     * charts look better when half-widths are used (for example,
     * {@link AreaRenderer3D}).
     * 
     * @param half  half width?
     * 
     * @see #setFirstCategoryHalfWidth(boolean) 
     */
    public void setLastCategoryHalfWidth(boolean half) {
        this.lastCategoryHalfWidth = half;
        fireChangeEvent(true);
    }

    /**
     * Returns the tick mark length (in Java2D units).  The default value
     * is {@code 3.0}.
     * 
     * @return The tick mark length. 
     */
    public double getTickMarkLength() {
        return this.tickMarkLength;
    }
    
    /**
     * Sets the tick mark length (in Java2D units) and sends an 
     * {@link Axis3DChangeEvent} to all registered listeners.  You can set
     * the length to {@code 0.0} if you don't want any tick marks on the
     * axis.
     * 
     * @param length  the length (in Java2D units).
     */
    public void setTickMarkLength(double length) {
        this.tickMarkLength = length;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the paint used to draw the tick marks, if they are visible.  
     * The default value is {@code Color.GRAY}.
     * 
     * @return The paint used to draw the tick marks (never {@code null}). 
     */
    public Paint getTickMarkPaint() {
        return this.tickMarkPaint;
    }

    /**
     * Sets the paint used to draw the tick marks and sends an 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param paint  the paint ({@code null} not permitted). 
     */
    public void setTickMarkPaint(Paint paint) {
        ArgChecks.nullNotPermitted(paint, "paint");
        this.tickMarkPaint = paint;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the stroke used to draw the tick marks, if they are visible.  
     * The default value is {@code new BasicStroke(0.5f)}.
     * 
     * @return The stroke used to draw the tick marks (never {@code null}). 
     */
    public Stroke getTickMarkStroke() {
        return this.tickMarkStroke;
    }
    
    /**
     * Sets the stroke used to draw the tick marks and sends an 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param stroke  the stroke ({@code null} not permitted). 
     */
    public void setTickMarkStroke(Stroke stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.tickMarkStroke = stroke;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the tick label generator for the axis.  This is an object that
     * is responsible for creating the category labels on the axis.  You can
     * plug in your own instance to take full control over the generation
     * of category labels.
     * 
     * @return The tick label generator for the axis (never {@code null}). 
     * 
     * @since 1.2
     */
    public CategoryLabelGenerator getTickLabelGenerator() {
        return this.tickLabelGenerator;
    }
    
    /**
     * Sets the tick label generator for the axis and sends a change event to 
     * all registered listeners.
     * 
     * @param generator  the generator ({@code null} not permitted).
     * 
     * @since 1.2
     */
    public void setTickLabelGenerator(CategoryLabelGenerator generator) {
        ArgChecks.nullNotPermitted(generator, "generator");
        this.tickLabelGenerator = generator;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the offset between the tick marks and the tick labels.  The
     * default value is {@code 5.0}.
     * 
     * @return The offset between the tick marks and the tick labels (in Java2D
     *     units).
     */
    public double getTickLabelOffset() {
        return this.tickLabelOffset;
    }
    
    /**
     * Sets the offset between the tick marks and the tick labels and sends
     * a {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param offset  the offset.
     */
    public void setTickLabelOffset(double offset) {
        this.tickLabelOffset = offset;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the orientation for the tick labels.  The default value is
     * {@link LabelOrientation#PARALLEL}.
     * 
     * @return The orientation for the tick labels (never {@code null}).
     * 
     * @since 1.2
     */
    public LabelOrientation getTickLabelOrientation() {
        return this.tickLabelOrientation;
    }
    
    /**
     * Sets the orientation for the tick labels and sends a change event to
     * all registered listeners.
     * 
     * @param orientation  the orientation ({@code null} not permitted).
     * 
     * @since 1.2
     */
    public void setTickLabelOrientation(LabelOrientation orientation) {
        ArgChecks.nullNotPermitted(orientation, "orientation");
        this.tickLabelOrientation = orientation;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the maximum number of offset levels for the category labels on 
     * the axis.  The default value is 3.
     * 
     * @return The maximum number of offset levels.
     * 
     * @since 1.2
     */
    public int getMaxTickLabelLevels() {
        return this.maxTickLabelLevels;
    }
    
    /**
     * Sets the maximum number of offset levels for the category labels on the
     * axis and sends a change event to all registered listeners.
     * 
     * @param levels  the maximum number of levels.
     * 
     * @since 1.2
     */
    public void setMaxTickLabelLevels(int levels) {
        this.maxTickLabelLevels = levels;
        fireChangeEvent(false);
    }
 
    /**
     * Returns the tick label factor.  The default value is {@code 1.4}.
     * 
     * @return The tick label factor.
     * 
     * @since 1.2
     */
    public double getTickLabelFactor() {
        return this.tickLabelFactor;
    }
    
    /**
     * Sets the tick label factor and sends a change event to all registered 
     * listeners.  
     * 
     * @param factor  the new factor (should be at least 1.0).
     * 
     * @since 1.2
     */
    public void setTickLabelFactor(double factor) {
        this.tickLabelFactor = factor;
        fireChangeEvent(false);
    }
    
    /**
     * Returns the marker with the specified key, if there is one.
     * 
     * @param key  the key ({@code null} not permitted).
     * 
     * @return The marker (possibly {@code null}). 
     * 
     * @since 1.2
     */
    @Override
    public CategoryMarker getMarker(String key) {
        return this.markers.get(key);
    }

    /**
     * Sets the marker for the specified key and sends a change event to 
     * all registered listeners.  If there is an existing marker it is replaced
     * (and the axis will no longer listen for change events on the previous 
     * marker).
     * 
     * @param key  the key that identifies the marker ({@code null} not 
     *         permitted).
     * @param marker  the marker ({@code null} permitted).
     * 
     * @since 1.2
     */
    public void setMarker(String key, CategoryMarker marker) {
        CategoryMarker existing = this.markers.get(key);
        if (existing != null) {
            existing.removeChangeListener(this);
        }
        this.markers.put(key, marker);
        if (marker != null) {
            marker.addChangeListener(this);
        }
        fireChangeEvent(false);
    } 

    /**
     * Returns a new map containing the markers that are assigned to this axis.
     * 
     * @return A map. 
     * 
     * @since 1.2
     */
    public Map<String, CategoryMarker> getMarkers() {
        return new LinkedHashMap<String, CategoryMarker>(this.markers);    
    }
    
    /**
     * Returns the width of a single category in the units of the axis
     * range.
     * 
     * @return The width of a single category. 
     */
    @Override
    public double getCategoryWidth() {
        double length = this.range.getLength();
        double start = this.range.getMin() + (this.lowerMargin * length);
        double end = this.range.getMax() - (this.upperMargin * length);
        double available = (end - start);
        return available / this.categories.size();
    }
    
    /**
     * Configures the axis to be used as a row axis for the specified
     * plot.  This method is for internal use, you should not call it directly.
     * 
     * @param plot  the plot ({@code null} not permitted).
     */
    @Override @SuppressWarnings("unchecked")
    public void configureAsRowAxis(CategoryPlot3D plot) {
        ArgChecks.nullNotPermitted(plot, "plot");
        this.categories = plot.getDataset().getRowKeys();
        this.isColumnAxis = false;
        this.isRowAxis = true;
    }

    /**
     * Configures the axis to be used as a column axis for the specified
     * plot.  This method is for internal use, you won't normally need to call
     * it directly.
     * 
     * @param plot  the plot ({@code null} not permitted).
     */
    @Override @SuppressWarnings("unchecked")
    public void configureAsColumnAxis(CategoryPlot3D plot) {
        ArgChecks.nullNotPermitted(plot, "plot");
        this.categories = plot.getDataset().getColumnKeys();
        this.isColumnAxis = true;
        this.isRowAxis = false;
    }

    /**
     * Returns the value for the specified category, or {@code Double.NaN}
     * if the category is not registered on the axis.
     * 
     * @param category  the category ({@code null} not permitted).
     * 
     * @return The value.
     */
    @Override
    public double getCategoryValue(Comparable<?> category) {
        int index = this.categories.indexOf(category);
        if (index < 0) {
            return Double.NaN;
        }
        double length = this.range.getLength();
        double start = this.range.getMin() + (this.lowerMargin * length);
        double end = this.range.getMax() - (this.upperMargin * length);
        double available = (end - start);
        double categoryCount = this.categories.size();
        if (categoryCount == 1) {
            return (start + end) / 2.0;
        }
        if (this.firstCategoryHalfWidth) {
            categoryCount -= 0.5;
        }
        if (this.lastCategoryHalfWidth) {
            categoryCount -= 0.5;
        }
        double categoryWidth = 0.0;
        if (categoryCount > 0.0) {
            categoryWidth = available / categoryCount;
        }
        double adj = this.firstCategoryHalfWidth ? 0.0 : 0.5;
        return start + (adj + index) * categoryWidth;
    }
    
    /**
     * Translates a value on the axis to the equivalent coordinate in the 
     * 3D world used to construct a model of the chart.
     * 
     * @param value  the value along the axis.
     * @param length  the length of one side of the 3D box containing the model.
     * 
     * @return A coordinate in 3D space.
     */
    @Override
    public double translateToWorld(double value, double length) {
        double p = getRange().percent(value, isInverted());
        return length * p;
    }

    /**
     * Draws the axis between the two points {@code pt0} and {@code pt1} in 
     * Java2D space.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param pt0  the starting point for the axis ({@code null} not 
     *     permitted).
     * @param pt1  the ending point for the axis ({@code null} not 
     *     permitted).
     * @param opposingPt  a point on the opposite side of the line from the 
     *         labels ({@code null} not permitted).
     * @param tickData  the tick data, contains positioning anchors calculated 
     *     by the 3D engine ({@code null} not permitted).
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
        if (pt0.equals(pt1)) { // if the axis starts and ends on the same point
            return;            // there is nothing we can draw
        }
        
        // draw the axis line (if you want no line, setting the line color
        // to fully transparent will achieve this)
        g2.setStroke(getLineStroke());
        g2.setPaint(getLineColor());
        Line2D axisLine = new Line2D.Float(pt0, pt1);
        g2.draw(axisLine);
 
        // draw the tick marks - during this pass we will also find the maximum
        // tick label width
        g2.setPaint(this.tickMarkPaint);
        g2.setStroke(this.tickMarkStroke);
        g2.setFont(getTickLabelFont());
        double maxTickLabelWidth = 0.0;
        for (TickData t : tickData) {
            if (this.tickMarkLength > 0.0) {
                Line2D tickLine = Utils2D.createPerpendicularLine(axisLine, 
                        t.getAnchorPt(), this.tickMarkLength, opposingPt);
                g2.draw(tickLine);
            }
            String tickLabel = t.getKeyLabel();
            maxTickLabelWidth = Math.max(maxTickLabelWidth, 
                    g2.getFontMetrics().stringWidth(tickLabel));
        }

        double maxTickLabelDim = maxTickLabelWidth;
        if (getTickLabelsVisible()) {
            g2.setPaint(getTickLabelColor());
            if (this.tickLabelOrientation.equals(
                    LabelOrientation.PERPENDICULAR)) {
                drawPerpendicularTickLabels(g2, axisLine, opposingPt, tickData,
                        info, hinting);
            } else if (this.tickLabelOrientation.equals(
                    LabelOrientation.PARALLEL)) {
                maxTickLabelDim = drawParallelTickLabels(g2, axisLine, 
                        opposingPt, tickData, maxTickLabelWidth, info, hinting);
            }
        } else {
            maxTickLabelDim = 0.0;
        }

        // draw the axis label if there is one
        if (getLabel() != null) {
            Shape labelBounds = drawAxisLabel(getLabel(), g2, axisLine, 
                    opposingPt, maxTickLabelDim + this.tickMarkLength 
                    + this.tickLabelOffset + getLabelOffset(), info, hinting);
        }
    }
    
    /**
     * Returns "row" if the axis has been configured as a row axis, "column" if
     * the axis has been configured as a column axis, and the empty string ("")
     * if the axis has not yet been configured.
     * 
     * @return A string (never {@code null}).
     * 
     * @since 1.3
     */
    @Override
    protected String axisStr() {
        String result = "";
        if (this.isRowAxis) {
            result = "row";
        } else if (this.isColumnAxis) {
            result = "column";
        }
        return result;
    }
    
    private double drawParallelTickLabels(Graphics2D g2, Line2D axisLine,
            Point2D opposingPt, List<TickData> tickData, 
            double maxTickLabelWidth, RenderingInfo info, boolean hinting) {
        int levels = 1;
        LineMetrics lm = g2.getFontMetrics().getLineMetrics("123", g2);
        double height = lm.getHeight();
        if (tickData.size() > 1) {
        
            // work out how many offset levels we need to display the 
            // categories without overlapping
            Point2D p0 = tickData.get(0).getAnchorPt();
            Point2D pN = tickData.get(tickData.size() - 1).getAnchorPt();
            double availableWidth = pN.distance(p0) 
                    * tickData.size() / (tickData.size() - 1);
            int labelsPerLevel = (int) Math.floor(availableWidth / 
                    (maxTickLabelWidth * tickLabelFactor));
            int levelsRequired = this.maxTickLabelLevels;
            if (labelsPerLevel > 0) {
                levelsRequired = this.categories.size() / labelsPerLevel + 1;
            }
            levels = Math.min(levelsRequired, this.maxTickLabelLevels);
        }
        
        int index = 0;
        for (TickData t : tickData) {
            int level = index % levels;
            double adj = height * (level + 0.5);
            Line2D perpLine = Utils2D.createPerpendicularLine(axisLine, 
                    t.getAnchorPt(), this.tickMarkLength 
                    + this.tickLabelOffset + adj, opposingPt);
            double axisTheta = Utils2D.calculateTheta(axisLine);
            TextAnchor textAnchor = TextAnchor.CENTER;
            if (axisTheta >= Math.PI / 2.0) {
                axisTheta = axisTheta - Math.PI;
            } else if (axisTheta <= -Math.PI / 2) {
                axisTheta = axisTheta + Math.PI;  
            }
            String tickLabel = t.getKeyLabel();
            if (hinting) {
                Map<String, String> m = new HashMap<String, String>();
                m.put("ref", "{\"type\": \"categoryTickLabel\", \"axis\": \"" 
                        + axisStr() + "\", \"key\": \"" 
                        + t.getKey() + "\"}");
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
                        InteractiveElementType.CATEGORY_AXIS_TICK_LABEL, bounds);
                tickLabelElement.setProperty("label", tickLabel);
                tickLabelElement.setProperty("axis", axisStr());
                info.addOffsetElement(tickLabelElement);
            }
            index++;
        }
        return height * levels;
    }
    
    /**
     * Draws the category labels perpendicular to the axis.
     * 
     * @param g2  the graphics target.
     * @param axisLine  the axis line.
     * @param opposingPt  an opposing point (used to indicate which side the
     *     labels will appear on).
     * @param tickData  the tick data.
     * @param info  if not {@code null} this will be populated with 
     *     {@link RenderedElement} instances for the tick labels.
     * @param hinting  
     */
    @SuppressWarnings("unchecked")
    private void drawPerpendicularTickLabels(Graphics2D g2, Line2D axisLine,
            Point2D opposingPt, List<TickData> tickData, RenderingInfo info,
            boolean hinting) {
        
        for (TickData t : tickData) {
            Line2D perpLine = Utils2D.createPerpendicularLine(axisLine, 
                    t.getAnchorPt(), this.tickMarkLength 
                    + this.tickLabelOffset, opposingPt);
            double perpTheta = Utils2D.calculateTheta(perpLine);
            TextAnchor textAnchor = TextAnchor.CENTER_LEFT;
            if (perpTheta >= Math.PI / 2.0) {
                perpTheta = perpTheta - Math.PI;
                textAnchor = TextAnchor.CENTER_RIGHT;
            } else if (perpTheta <= -Math.PI / 2) {
                perpTheta = perpTheta + Math.PI;
                textAnchor = TextAnchor.CENTER_RIGHT;   
            }
            String tickLabel = t.getKeyLabel();
            if (hinting) {
                Map m = new HashMap<String, String>();
                m.put("ref", "{\"type\": \"categoryAxisLabel\", \"axis\": \"" 
                        + axisStr() + "\", \"key\": \"" 
                        + t.getKey() + "\"}");
                g2.setRenderingHint(Chart3DHints.KEY_BEGIN_ELEMENT, m);
            }
            Shape bounds = TextUtils.drawRotatedString(tickLabel, g2, 
                    (float) perpLine.getX2(), (float) perpLine.getY2(), 
                    textAnchor, perpTheta, textAnchor);
            if (hinting) {
                g2.setRenderingHint(Chart3DHints.KEY_END_ELEMENT, true);
            }
            if (info != null) {
                RenderedElement tickLabelElement = new RenderedElement(
                        InteractiveElementType.CATEGORY_AXIS_TICK_LABEL, bounds);
                tickLabelElement.setProperty("label", tickLabel);
                tickLabelElement.setProperty("axis", axisStr());
                info.addOffsetElement(tickLabelElement);
            }
        }
    }
    
    /**
     * Generates the tick data for the axis (assumes the axis is being used
     * as the row axis).  The dataset is passed as an argument to provide the 
     * opportunity to incorporate dataset-specific info into tick labels (for 
     * example, a row label might show the total for that row in the dataset)
     * ---whether or not this is used depends on the axis implementation.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * 
     * @return The tick data.
     * 
     * @since 1.2
     */
    @Override @SuppressWarnings("unchecked")
    public List<TickData> generateTickDataForRows(CategoryDataset3D dataset) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        List<TickData> result = new ArrayList<TickData>(this.categories.size());
        for (Comparable<?> key : this.categories) {
            double pos = this.range.percent(getCategoryValue(key));
            String label = this.tickLabelGenerator.generateRowLabel(dataset, 
                    key);
            result.add(new TickData(pos, key, label));
        }
        return result;
    }

    /**
     * Generates the tick data for the axis (assumes the axis is being used
     * as the row axis).  The dataset is passed as an argument to provide the 
     * opportunity to incorporate dataset-specific info into tick labels (for 
     * example, a row label might show the total for that row in the dataset)
     * ---whether or not this is used depends on the axis implementation.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * 
     * @return The tick data.
     * 
     * @since 1.2
     */
    @Override @SuppressWarnings("unchecked")
    public List<TickData> generateTickDataForColumns(
            CategoryDataset3D dataset) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        List<TickData> result = new ArrayList<TickData>(this.categories.size());
        for (Comparable<?> key : this.categories) {
            double pos = this.range.percent(getCategoryValue(key));
            String label = this.tickLabelGenerator.generateColumnLabel(dataset, 
                    key);
            result.add(new TickData(pos, key, label));
        }
        return result;
    }

    /**
     * Generates and returns a list of marker data items for the axis.
     * 
     * @return A list of marker data items (never {@code null}). 
     */
    @Override
    public List<MarkerData> generateMarkerData() {
        List<MarkerData> result = new ArrayList<MarkerData>();
        for (Map.Entry<String, CategoryMarker> entry 
                : this.markers.entrySet()) {
            CategoryMarker cm = entry.getValue();
            if (cm == null) {
                continue;
            }
            MarkerData markerData;
            if (cm.getType().equals(CategoryMarkerType.LINE)) {
                double pos = getCategoryValue(cm.getCategory());
                markerData = new MarkerData(entry.getKey(), pos);
                markerData.setLabelAnchor(cm.getLabel() != null 
                            ? cm.getLabelAnchor() : null);
            } else if (cm.getType().equals(CategoryMarkerType.BAND)) {
                double pos = getCategoryValue(cm.getCategory());
                double width = getCategoryWidth();                
                markerData = new MarkerData(entry.getKey(), pos - width / 2, 
                        false, pos + width / 2, false);
                markerData.setLabelAnchor(cm.getLabel() != null 
                            ? cm.getLabelAnchor() : null);
            } else {
                throw new RuntimeException("Unrecognised marker: " 
                        + cm.getType());
            }
            result.add(markerData);
        }
        return result;
    }

    /**
     * Receives a {@link ChartElementVisitor}.  This method is part of a general
     * mechanism for traversing the chart structure and performing operations
     * on each element in the chart.  You will not normally call this method
     * directly.
     * 
     * @param visitor  the visitor ({@code null} not permitted).
     * 
     * @since 1.2
     */
    @Override
    public void receive(ChartElementVisitor visitor) {
        for (Marker marker : this.markers.values()) {
            marker.receive(visitor);
        }
        visitor.visit(this);
    }

    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object to test against ({@code null} not permitted).
     * 
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StandardCategoryAxis3D)) {
            return false;
        }
        StandardCategoryAxis3D that = (StandardCategoryAxis3D) obj;
        if (this.lowerMargin != that.lowerMargin) {
            return false;
        }
        if (this.upperMargin != that.upperMargin) {
            return false;
        }
        if (this.firstCategoryHalfWidth != that.firstCategoryHalfWidth) {
            return false;
        }
        if (this.lastCategoryHalfWidth != that.lastCategoryHalfWidth) {
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
        if (!this.tickLabelGenerator.equals(that.tickLabelGenerator)) {
            return false;
        }
        if (this.tickLabelOffset != that.tickLabelOffset) {
            return false;
        }
        if (!this.tickLabelOrientation.equals(that.tickLabelOrientation)) {
            return false;
        }
        if (this.tickLabelFactor != that.tickLabelFactor) {
            return false;
        }
        if (this.maxTickLabelLevels != that.maxTickLabelLevels) {
            return false;
        }
        if (!this.markers.equals(that.markers)) {
            return false;
        }
        return super.equals(obj);
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
    
    /**
     * Returns {@code true} if the axis inverts the order of the data items,
     * and {@code false} otherwise.
     * 
     * @return A boolean.
     * 
     * @since 1.5
     */
    @Override
    public boolean isInverted() {
        return this.inverted;
    }
    
    /**
     * Sets the flag that controls whether or not the axis inverts the order
     * of the data items and sends an {@link Axis3DChangeEvent} to all 
     * registered listeners.
     * 
     * @param inverted  the new flag value. 
     * 
     * @since 1.5
     */
    public void setInverted(boolean inverted) {
        this.inverted = inverted;
        fireChangeEvent(true);
    }
}
