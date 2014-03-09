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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.orsoncharts.ChartElementVisitor;
import com.orsoncharts.Range;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.marker.MarkerData;
import com.orsoncharts.marker.NumberMarker;
import com.orsoncharts.marker.RangeMarker;
import com.orsoncharts.marker.ValueMarker;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ObjectUtils;
import com.orsoncharts.util.SerialUtils;

/**
 * A base class for implementing numerical axes.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
public abstract class AbstractValueAxis3D extends AbstractAxis3D 
        implements ValueAxis3D, Serializable{

    /** The axis range. */
    protected Range range;

    /** 
     * A flag that controls whether or not the axis range is automatically
     * adjusted to display all of the data items in the dataset.
     */
    private boolean autoAdjustRange;
    
    /** The percentage margin to leave at the lower end of the axis. */
    private double lowerMargin;
    
    /** The percentage margin to leave at the upper end of the axis. */
    private double upperMargin;

    /** 
     * The default range to apply when there is no data in the dataset and the
     * autoAdjustRange flag is true.  A sensible default is going to depend on
     * the context, so the user should change it as necessary.
     */
    private Range defaultAutoRange;

    /** The tick label offset (number of Java2D units). */
    private double tickLabelOffset;
    
    /** The length of tick marks (in Java2D units).  Can be set to 0.0. */
    private double tickMarkLength;
    
    /** The tick mark stroke (never <code>null</code>). */
    private transient Stroke tickMarkStroke;
    
    /** The tick mark paint (never <code>null</code>). */
    private transient Paint tickMarkPaint;
    
    /** The orientation for the tick labels. */
    private LabelOrientation tickLabelOrientation;

    /** The tick label factor (defaults to 1.4). */
    private double tickLabelFactor;    

    /** Storage for value markers for the axis (empty by default). */
    private Map<String, ValueMarker> valueMarkers;
    
    public AbstractValueAxis3D(String label, Range range) {
        super(label);
        this.range = range;
        this.autoAdjustRange = true;
        this.lowerMargin = 0.05;
        this.upperMargin = 0.05;
        this.defaultAutoRange = new Range(0.0, 1.0);
        this.tickLabelOffset = 5.0;
        this.tickLabelOrientation = LabelOrientation.PARALLEL;
        this.tickLabelFactor = 1.4;
        this.tickMarkLength = 3.0;
        this.tickMarkStroke = new BasicStroke(0.5f);
        this.tickMarkPaint = Color.GRAY;
        this.valueMarkers = new LinkedHashMap<String, ValueMarker>();
    }

    /**
     * Returns the axis range.  You can set the axis range manually or you can
     * rely on the autoAdjustRange feature to set the axis range to match
     * the data being plotted.
     * 
     * @return the axis range (never <code>null</code>).
     */
    @Override
    public Range getRange() {
        return this.range;
    }
  
    /**
     * Sets the axis range (bounds) and sends an {@link Axis3DChangeEvent} to 
     * all registered listeners.
     * 
     * @param range  the new range (must have positive length and 
     *     <code>null</code> is not permitted).
     */
    @Override
    public void setRange(Range range) {
        ArgChecks.nullNotPermitted(range, "range");
        if (range.getLength() <= 0.0) {
            throw new IllegalArgumentException(
                    "Requires a range with length > 0");
        }
        this.range = range;
        this.autoAdjustRange = false;
        fireChangeEvent(true);
    }
    
    /**
     * Updates the axis range (used by the auto-range calculation) without
     * notifying listeners.
     * 
     * @param range  the new range. 
     */
    protected void updateRange(Range range) {
        this.range = range;        
    }
    
    /**
     * Sets the axis range and sends an {@link Axis3DChangeEvent} to all 
     * registered listeners.
     * 
     * @param min  the lower bound for the range (requires min &lt; max).
     * @param max  the upper bound for the range (requires max &gt; min).
     */
    @Override
    public void setRange(double min, double max) {
        setRange(new Range(min, max));
    }

    /**
     * Returns the flag that controls whether or not the axis range is 
     * automatically updated in response to dataset changes.  The default 
     * value is <code>true</code>.
     * 
     * @return A boolean. 
     */
    public boolean isAutoAdjustRange() {
        return this.autoAdjustRange;
    }
    
    /**
     * Sets the flag that controls whether or not the axis range is 
     * automatically updated in response to dataset changes, and sends an
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param autoAdjust  the new flag value. 
     */
    public void setAutoAdjustRange(boolean autoAdjust) {
        this.autoAdjustRange = autoAdjust;
        fireChangeEvent(true);
    }
    
    /**
     * Returns the size of the lower margin that is added by the auto-range
     * calculation, as a percentage of the data range.  This margin is used to 
     * prevent data items from being plotted right at the edges of the chart.  
     * The default value is <code>0.05</code> (five percent).
     * 
     * @return The lower margin.
     */
    public double getLowerMargin() {
        return this.lowerMargin;
    }
    
    /**
     * Sets the size of the lower margin that will be added by the auto-range
     * calculation and sends an {@link Axis3DChangeEvent} to all registered
     * listeners.
     * 
     * @param margin  the margin as a percentage of the data range 
     *     (0.05 = five percent).
     * 
     * @see #setUpperMargin(double) 
     */
    public void setLowerMargin(double margin) {
        this.lowerMargin = margin;
        fireChangeEvent(true);
    }
    
    /**
     * Returns the size of the upper margin that is added by the auto-range
     * calculation, as a percentage of the data range.  This margin is used to 
     * prevent data items from being plotted right at the edges of the chart.  
     * The default value is <code>0.05</code> (five percent).
     * 
     * @return The upper margin.
     */
    public double getUpperMargin() {
        return this.upperMargin;
    }
    
    /**
     * Sets the size of the upper margin that will be added by the auto-range
     * calculation and sends an {@link Axis3DChangeEvent} to all registered
     * listeners.
     * 
     * @param margin  the margin as a percentage of the data range 
     *     (0.05 = five percent).
     * 
     * @see #setLowerMargin(double) 
     */
    public void setUpperMargin(double margin) {
        this.upperMargin = margin;
        fireChangeEvent(true);
    }
    
    
    /**
     * Returns the default range used when the <code>autoAdjustRange</code>
     * flag is <code>true</code> but the dataset contains no values.  The
     * default range is <code>(0.0 to 1.0)</code>, depending on the context
     * you may want to change this.
     * 
     * @return The default range (never <code>null</code>).
     * 
     * @see #setDefaultAutoRange(com.orsoncharts.Range) 
     */
    public Range getDefaultAutoRange() {
        return this.defaultAutoRange;
    }
    
    /**
     * Sets the default range used  when the <code>autoAdjustRange</code>
     * flag is <code>true</code> but the dataset contains no values, and sends
     * an {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param range  the range (<code>null</code> not permitted).
     *
     * @see #getDefaultAutoRange() 
     */
    public void setDefaultAutoRange(Range range) {
        ArgChecks.nullNotPermitted(range, "range");
        this.defaultAutoRange = range;
        fireChangeEvent(true);
    }

    /**
     * Returns the orientation for the tick labels.  The default value is
     * {@link LabelOrientation#PARALLEL}. 
     * 
     * @return The orientation for the tick labels (never <code>null</code>).
     * 
     * @since 1.2
     */
    public LabelOrientation getTickLabelOrientation() {
        return this.tickLabelOrientation;
    }
    
    /**
     * Sets the orientation for the tick labels and sends a change event to
     * all registered listeners.  In general, <code>PARALLEL</code> is the
     * best setting for X and Z axes, and <code>PERPENDICULAR</code> is the
     * best setting for Y axes.
     * 
     * @param orientation  the orientation (<code>null</code> not permitted).
     * 
     * @since 1.2
     */
    public void setTickLabelOrientation(LabelOrientation orientation) {
        ArgChecks.nullNotPermitted(orientation, "orientation");
        this.tickLabelOrientation = orientation;
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
     * Configures the axis to be used as the value axis for the specified
     * plot.  This method is used internally, you should not need to call it
     * directly.
     * 
     * @param plot  the plot (<code>null</code> not permitted). 
     */
    @Override
    public void configureAsValueAxis(CategoryPlot3D plot) {
        if (this.autoAdjustRange) {
        	CategoryDataset3D dataset = plot.getDataset();
            Range valueRange = plot.getRenderer().findValueRange(dataset);
            if (valueRange != null) {
                updateRange(adjustedDataRange(valueRange));
            } else {
                updateRange(this.defaultAutoRange);
            }
        }
    }
    
    /**
     * Configures the axis to be used as the x-axis for the specified plot.  
     * This method is used internally, you should not need to call it
     * directly.
     * 
     * @param plot  the plot (<code>null</code> not permitted). 
     */
    @Override
    public void configureAsXAxis(XYZPlot plot) {
        if (this.autoAdjustRange) {
            Range xRange = plot.getRenderer().findXRange(plot.getDataset());
            if (xRange != null) {
                updateRange(adjustedDataRange(xRange));
            } else {
                updateRange(this.defaultAutoRange);
            }
        }
    }

    /**
     * Configures the axis to be used as the y-axis for the specified plot.  
     * This method is used internally, you should not need to call it
     * directly.
     * 
     * @param plot  the plot (<code>null</code> not permitted). 
     */
    @Override
    public void configureAsYAxis(XYZPlot plot) {
        if (this.autoAdjustRange) {
            Range yRange = plot.getRenderer().findYRange(plot.getDataset());
            if (yRange != null) {
                updateRange(adjustedDataRange(yRange));
            } else {
                updateRange(this.defaultAutoRange);
            }
        }
    }

    /**
     * Configures the axis to be used as the z-axis for the specified plot.  
     * This method is used internally, you should not need to call it
     * directly.
     * 
     * @param plot  the plot (<code>null</code> not permitted). 
     */
    @Override
    public void configureAsZAxis(XYZPlot plot) {
        if (this.autoAdjustRange) {
            Range zRange = plot.getRenderer().findZRange(plot.getDataset());
            if (zRange != null) {
                updateRange(adjustedDataRange(zRange));
            } else {
                updateRange(this.defaultAutoRange);
            }
        }
    }

    /**
     * Adjusts the range by adding the lower and upper margins and taking into
     * account any other settings.
     * 
     * @param range  the range (<code>null</code> not permitted).
     * 
     * @return The adjusted range. 
     */
    protected abstract Range adjustedDataRange(Range range);
    
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
    public ValueMarker getMarker(String key) {
        return this.valueMarkers.get(key);
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
    public void setMarker(String key, ValueMarker marker) {
        ValueMarker existing = this.valueMarkers.get(key);
        if (existing != null) {
            existing.removeChangeListener(this);
        }
        this.valueMarkers.put(key, marker);
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
        return new LinkedHashMap<String, ValueMarker>(this.valueMarkers);    
    }

    /**
     * Generates and returns a list of marker data items for the axis.
     * 
     * @return A list of marker data items (never <code>null</code>). 
     */
    @Override
    public List<MarkerData> generateMarkerData() {
        List<MarkerData> result = new ArrayList<MarkerData>();
        Range range = getRange();
        for (Map.Entry<String, ValueMarker> entry 
                : this.valueMarkers.entrySet()) {
            ValueMarker vm = entry.getValue();
            if (range.intersects(vm.getRange())) {
                MarkerData markerData;
                if (vm instanceof NumberMarker) {
                    NumberMarker nm = (NumberMarker) vm;
                    markerData = new MarkerData(entry.getKey(), 
                            range.percent(nm.getValue()));
                    markerData.setLabelAnchor(nm.getLabel() != null 
                            ? nm.getLabelAnchor() : null);
                } else if (vm instanceof RangeMarker) {
                    RangeMarker rm = (RangeMarker) vm;
                    double startValue = rm.getStart().getValue();
                    boolean startPegged = false;
                    if (!range.contains(startValue)) {
                        startValue = range.peggedValue(startValue);
                        startPegged = true;
                    } 
                    double startPos = range.percent(startValue);
                    double endValue = rm.getEnd().getValue();
                    boolean endPegged = false;
                    if (!range.contains(endValue)) {
                        endValue = range.peggedValue(endValue);
                        endPegged = true;
                    }
                    double endPos = range.percent(endValue);
                    markerData = new MarkerData(entry.getKey(), startPos, 
                            startPegged, endPos, endPegged);
                    markerData.setLabelAnchor(rm.getLabel() != null 
                            ? rm.getLabelAnchor() : null);
                } else {
                    throw new RuntimeException("Unrecognised marker.");
                }
                result.add(markerData);
            }
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
        for (ValueMarker marker : this.valueMarkers.values()) {
            marker.receive(visitor);
        }
        visitor.visit(this);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractValueAxis3D)) {
            return false;
        }
        AbstractValueAxis3D that = (AbstractValueAxis3D) obj;
        if (!this.range.equals(that.range)) {
            return false;
        }
        if (this.autoAdjustRange != that.autoAdjustRange) {
            return false;
        }
        if (this.lowerMargin != that.lowerMargin) {
            return false;
        }
        if (this.upperMargin != that.upperMargin) {
            return false;
        }
        if (!this.defaultAutoRange.equals(that.defaultAutoRange)) {
            return false;
        }
        if (this.tickLabelOffset != that.tickLabelOffset) {
            return false;
        }
        if (this.tickLabelFactor != that.tickLabelFactor) {
            return false;
        }
        if (!this.tickLabelOrientation.equals(that.tickLabelOrientation)) {
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
