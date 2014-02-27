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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.orsoncharts.ChartElementVisitor;
import com.orsoncharts.Range;
import com.orsoncharts.marker.MarkerData;
import com.orsoncharts.marker.NumberMarker;
import com.orsoncharts.marker.RangeMarker;
import com.orsoncharts.marker.ValueMarker;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.util.ArgChecks;

/**
 * A base class for implementing numerical axes.
 */
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

    /** Storage for value markers for the axis (empty by default). */
    private Map<String, ValueMarker> valueMarkers;
    
    public AbstractValueAxis3D(String label, Range range) {
        super(label);
        this.range = range;
        this.autoAdjustRange = true;
        this.lowerMargin = 0.05;
        this.upperMargin = 0.05;
        this.defaultAutoRange = new Range(0.0, 1.0);
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
        setAutoAdjustRange(false);
        //fireChangeEvent(true); 
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
     * Configures the axis to be used as the value axis for the specified
     * plot.  This method is used internally, you should not need to call it
     * directly.
     * 
     * @param plot  the plot (<code>null</code> not permitted). 
     */
    @Override
    public void configureAsValueAxis(CategoryPlot3D plot) {
        if (this.autoAdjustRange) {
            Range valueRange = plot.getRenderer().findValueRange(
                    plot.getDataset());
            if (valueRange != null) {
                this.range = adjustedDataRange(valueRange);
            } else {
                this.range = this.defaultAutoRange;
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
                this.range = adjustedDataRange(xRange);
            } else {
                this.range = this.defaultAutoRange;
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
                this.range = adjustedDataRange(yRange);
            } else {
                this.range = this.defaultAutoRange;
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
                this.range = adjustedDataRange(zRange);
            } else {
                this.range = this.defaultAutoRange;
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
    protected Range adjustedDataRange(Range range) {
        ArgChecks.nullNotPermitted(range, "range");
        double lm = range.getLength() * this.lowerMargin;
        double um = range.getLength() * this.upperMargin;
        double lowerBound = range.getMin() - lm;
        double upperBound = range.getMax() + um;
        return new Range(lowerBound, upperBound);
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
        return new LinkedHashMap(this.valueMarkers);    
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
        return super.equals(obj);
    }
 
}
