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
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

import com.orsoncharts.util.TextUtils;
import com.orsoncharts.util.TextAnchor;
import com.orsoncharts.Range;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.graphics3d.Utils2D;
import com.orsoncharts.label.CategoryLabelGenerator;
import com.orsoncharts.label.StandardCategoryLabelGenerator;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.ChartElementVisitor;
import com.orsoncharts.marker.CategoryMarker;
import com.orsoncharts.marker.MarkerData;
import com.orsoncharts.marker.MarkerDataType;
import com.orsoncharts.marker.ValueMarker;
import com.orsoncharts.util.ObjectUtils;
import com.orsoncharts.util.SerialUtils;

/**
 * An axis that displays categories.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
public class StandardCategoryAxis3D extends AbstractAxis3D 
        implements CategoryAxis3D, Serializable {

    /** Should the axis be displayed on the chart? */
    private boolean visible;
    
    /** The categories. */
    private List<Comparable<?>> categories;
  
    /** 
     * The axis range (never <code>null</code>). 
     */
    private Range range;

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
    
    /** The tick mark stroke (never <code>null</code>). */
    private transient Stroke tickMarkStroke;
    
    /** The tick mark paint (never <code>null</code>). */
    private transient Paint tickMarkPaint;
    
    /** The tick label generator. */
    private CategoryLabelGenerator tickLabelGenerator;
    
    /** 
     * The tick label offset (in Java2D units).  This is the gap between the
     * tick marks and their associated labels.
     */
    private double tickLabelOffset;
 
    private Map<String, CategoryMarker> markers;
    
    /**
     * Default constructor.
     */
    public StandardCategoryAxis3D() {
        this(null);
    }
 
    /**
     * Creates a new axis with the specified label.
     * 
     * @param label  the axis label (<code>null</code> permitted). 
     */
    public StandardCategoryAxis3D(String label) {
        super(label);
        this.visible = true;
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
        this.markers = new LinkedHashMap<String, CategoryMarker>();
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
     * @param range  the range (<code>null</code> not permitted). 
     */
    @Override
    public void setRange(Range range) {
        ArgChecks.nullNotPermitted(range, "range");
        this.range = range;
        fireChangeEvent(true);
    }
    
    /**
     * Returns the margin to leave at the lower end of the axis, as a 
     * percentage of the axis length.  The default is <code>0.05</code>.
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
     * percentage of the axis length.  The default is <code>0.05</code>.
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
     * Returns <code>true</code> if the first category on the axis should
     * occupy half the normal width, and <code>false</code> otherwise.
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
     * Returns <code>true</code> if the last category on the axis should
     * occupy half the normal width, and <code>false</code> otherwise.
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
     * is <code>3.0</code>.
     * 
     * @return The tick mark length. 
     */
    public double getTickMarkLength() {
        return this.tickMarkLength;
    }
    
    /**
     * Sets the tick mark length (in Java2D units) and sends an 
     * {@link Axis3DChangeEvent} to all registered listeners.  You can set
     * the length to <code>0.0</code> if you don't want any tick marks on the
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
     * The default value is <code>Color.GRAY</code>.
     * 
     * @return The paint used to draw the tick marks (never <code>null</code>). 
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
     * Returns the stroke used to draw the tick marks, if they are visible.  
     * The default value is <code>new BasicStroke(0.5f)</code>.
     * 
     * @return The stroke used to draw the tick marks (never <code>null</code>). 
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
     * Returns the tick label generator for the axis.  This is an object that
     * is responsible for creating the category labels on the axis.  You can
     * plug in your own instance to take full control over the generation
     * of category labels.
     * 
     * @return The tick label generator for the axis (never <code>null</code>). 
     * 
     * @since 1.2
     */
    public CategoryLabelGenerator getTickLabelGenerator() {
        return this.tickLabelGenerator;
    }
    
    /**
     * Sets the tick label generator for the axis and sends an 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param generator  the generator (<code>null</code> not permitted).
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
     * default value is <code>5.0</code>.
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
     * Returns the marker with the specified key, if there is one.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return The marker (possibly <code>null</code>). 
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
     * (the axis will no longer listen for change events on the previous 
     * marker).
     * 
     * @param key  the key that identifies the marker (<code>null</code> not 
     *         permitted).
     * @param marker  the marker (<code>null</code> permitted).
     * 
     * @since 1.2
     */
    public void setMarker(String key, CategoryMarker marker) {
        CategoryMarker existing = this.markers.get(key);
        if (existing != null) {
            existing.removeChangeListener(this);
        }
        this.markers.put(key, marker);
        marker.addChangeListener(this);
        fireChangeEvent(false);
    } 

    /**
     * Returns a new map containing the markers assigned to this axis.
     * 
     * @return A map. 
     * 
     * @since 1.2
     */
    public Map<String, ValueMarker> getMarkers() {
        return new LinkedHashMap(this.markers);    
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
     * @param plot  the plot (<code>null</code> not permitted).
     */
    @Override
    public void configureAsRowAxis(CategoryPlot3D plot) {
        ArgChecks.nullNotPermitted(plot, "plot");
        this.categories = plot.getDataset().getRowKeys();
    }

    /**
     * Configures the axis to be used as a column axis for the specified
     * plot.  This method is for internal use, you won't normally need to call
     * it directly.
     * 
     * @param plot  the plot (<code>null</code> not permitted).
     */
    @Override
    public void configureAsColumnAxis(CategoryPlot3D plot) {
        ArgChecks.nullNotPermitted(plot, "plot");
        this.categories = plot.getDataset().getColumnKeys();
    }

    /**
     * Returns the value for the specified category, or <code>Double.NaN</code>
     * if the category is not registered on the axis.
     * 
     * @param category  the category (<code>null</code> not permitted).
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
        return length * (value - this.range.getMin()) / this.range.getLength();
    }

    /**
     * Draws the axis between the two points <code>pt0</code> and 
     * <code>pt1</code> in Java2D space.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param pt0  the starting point for the axis.
     * @param pt1  the ending point for the axis.
     * @param opposingPt  a point on the opposite side of the line from the 
     *         labels.
     * @param labels  display labels?
     * @param tickData  the tick data (contains positioning anchors calculated 
     *     by the 3D engine).
     */
    @Override
    public void draw(Graphics2D g2, Point2D pt0, Point2D pt1, 
            Point2D opposingPt, boolean labels, List<TickData> tickData) {
        
        if (!isVisible()) {
            return;
        }
        
        // draw the axis line
        g2.setStroke(getLineStroke());
        g2.setPaint(getLineColor());
        Line2D axisLine = new Line2D.Float(pt0, pt1);
        g2.draw(axisLine);
 
        // draw the tick marks
        double maxTickLabelWidth = 0.0;
        for (TickData t : tickData) {
            Line2D perpLine = Utils2D.createPerpendicularLine(axisLine, 
                    t.getAnchorPt(), this.tickMarkLength + this.tickLabelOffset,
                    opposingPt);
            if (this.tickMarkLength > 0.0) {
                Line2D tickLine = Utils2D.createPerpendicularLine(axisLine, 
                        t.getAnchorPt(), this.tickMarkLength, opposingPt);
                g2.setPaint(this.tickMarkPaint);
                g2.setStroke(this.tickMarkStroke);
                g2.draw(tickLine);
            }

            if (getTickLabelsVisible()) {
                double perpTheta = Utils2D.calculateTheta(perpLine);
                TextAnchor textAnchor = TextAnchor.CENTER_LEFT;
                if (perpTheta >= Math.PI / 2.0) {
                    perpTheta = perpTheta - Math.PI;
                    textAnchor = TextAnchor.CENTER_RIGHT;
                } else if (perpTheta <= -Math.PI / 2) {
                    perpTheta = perpTheta + Math.PI;
                    textAnchor = TextAnchor.CENTER_RIGHT;   
                }
                g2.setFont(getTickLabelFont());
                g2.setPaint(getTickLabelColor());
                String tickLabel = t.getKeyLabel();
                maxTickLabelWidth = Math.max(maxTickLabelWidth, 
                        g2.getFontMetrics().stringWidth(tickLabel));
                TextUtils.drawRotatedString(tickLabel, g2, 
                        (float) perpLine.getX2(), (float) perpLine.getY2(), 
                        textAnchor, perpTheta, textAnchor);
            }
        }        

        // draw the axis label if there is one
        if (getLabel() != null) {
            g2.setFont(getLabelFont());
            g2.setPaint(getLabelColor());
            Line2D labelPosLine = Utils2D.createPerpendicularLine(axisLine, 0.5, 
                    this.tickMarkLength + this.tickLabelOffset 
                    + maxTickLabelWidth + 10.0, 
                    opposingPt);
            double theta = Utils2D.calculateTheta(axisLine);
            if (theta < -Math.PI / 2.0) {
                theta = theta + Math.PI;
            }
            if (theta > Math.PI / 2.0) {
                theta = theta - Math.PI;
            }
            TextUtils.drawRotatedString(getLabel(), g2, 
                    (float) labelPosLine.getX2(), (float) labelPosLine.getY2(), 
                    TextAnchor.CENTER, theta, TextAnchor.CENTER);
        }
    }

    /**
     * Generates the tick data for the axis (assumes the axis is being used
     * as the row axis).  The dataset is passed as an argument to provide the 
     * opportunity to incorporate dataset-specific info into tick labels (for 
     * example, a row label might show the total for that row in the dataset)
     * ---whether or not this is used depends on the axis implementation.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return The tick data.
     * 
     * @since 1.2
     */
    @Override
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
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return The tick data.
     * 
     * @since 1.2
     */
    @Override
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
     * @return 
     */
    @Override
    public List<MarkerData> generateMarkerData() {
        List<MarkerData> result = new ArrayList<MarkerData>();
        for (Map.Entry<String, CategoryMarker> entry : this.markers.entrySet()) {
            CategoryMarker cm = entry.getValue();
            MarkerData markerData;
            if (cm.getType().equals(MarkerDataType.VALUE)) {
                double pos = getCategoryValue(cm.getCategory());
                markerData = new MarkerData(entry.getKey(), pos);
            } else if (cm.getType().equals(MarkerDataType.RANGE)) {
                double pos = getCategoryValue(cm.getCategory());
                double width = getCategoryWidth();                
                markerData = new MarkerData(entry.getKey(), pos - width / 2, 
                        pos + width / 2);
            } else {
                throw new RuntimeException("Unrecognised marker.");
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
     * @param visitor  the visitor (<code>null</code> not permitted).
     * 
     * @since 1.2
     */
    @Override
    public void receive(ChartElementVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object to test against (<code>null</code> not permitted).
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
        if (this.visible != that.visible) {
            return false;
        }
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

}
