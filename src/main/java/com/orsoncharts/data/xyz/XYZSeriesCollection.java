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

package com.orsoncharts.data.xyz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.data.AbstractDataset3D;
import com.orsoncharts.data.JSONUtils;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.xyz.XYZRenderer;

/**
 * A collection of {@link XYZSeries} objects (implements the {@link XYZDataset}
 * interface so that it can be used as a source of data for an 
 * {@link XYZRenderer} on an {@link XYZPlot}).
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
@SuppressWarnings("serial")
public class XYZSeriesCollection extends AbstractDataset3D 
        implements XYZDataset, Serializable {

    /** Storage for the data series. */
    private List<XYZSeries> series;

    /**
     * Creates a new (empty) <code>XYZSeriesCollection</code> instance.
     */
    public XYZSeriesCollection() {
        this.series = new ArrayList<XYZSeries>();
    }

    /**
     * Returns the number of series in the collection.
     * 
     * @return The number of series in the collection. 
     */
    @Override
    public int getSeriesCount() {
        return this.series.size();
    }
    
    /**
     * Returns the index of the series with the specified key, or 
     * <code>-1</code> if there is no series with the specified key.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return The series index or <code>-1</code>. 
     */
    @Override
    public int getSeriesIndex(Comparable<?> key) {
        ArgChecks.nullNotPermitted(key, "key");
        return getSeriesKeys().indexOf(key);
    }

    /**
     * Returns a new list containing all the series keys.  Modifying this list 
     * will have no impact on the <code>XYZSeriesCollection</code> instance.
     * 
     * @return A list containing the series keys (possibly empty, but never 
     *     <code>null</code>).
     */
    @Override
    public List<Comparable<?>> getSeriesKeys() {
        List<Comparable<?>> result = new ArrayList<Comparable<?>>();
        for (XYZSeries s : this.series) {
            result.add(s.getKey());
        }
        return result;
    }
    
    /**
     * Returns the key for the specified series.
     * 
     * @param seriesIndex  the series index.
     * 
     * @return The series key.
     * 
     * @since 1.3
     */
    @Override
    public Comparable<?> getSeriesKey(int seriesIndex) {
        return getSeries(seriesIndex).getKey();
    }

    /**
     * Adds a series to the collection (note that the series key must be
     * unique within the collection).
     * 
     * @param series  the series (<code>null</code> not permitted). 
     */
    public void add(XYZSeries series) {
        ArgChecks.nullNotPermitted(series, "series");
        if (getSeriesIndex(series.getKey()) >= 0) {
            throw new IllegalArgumentException("Another series with the same key already exists within the collection.");
        }
        this.series.add(series);
    }
    
    /**
     * Returns the series with the specified index.
     * 
     * @param index  the series index.
     * 
     * @return The series.
     * 
     * @since 1.2
     */
    public XYZSeries getSeries(int index) {
        ArgChecks.checkArrayBounds(index, "index", this.series.size());
        return this.series.get(index);
    }
    
    /**
     * Returns the series with the specified key, or <code>null</code> if 
     * there is no such series.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return The series. 
     * 
     * @since 1.2
     */
    public XYZSeries getSeries(Comparable<?> key) {
        ArgChecks.nullNotPermitted(key, "key");
        for (XYZSeries s : this.series) {
            if (s.getKey().equals(key)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Returns the number of items in the specified series.
     * 
     * @param seriesIndex  the series index.
     * 
     * @return The number of items in the specified series. 
     */
    @Override
    public int getItemCount(int seriesIndex) {
        ArgChecks.nullNotPermitted(this, null);
        XYZSeries s = this.series.get(seriesIndex);
        return s.getItemCount();
    }

    /**
     * Returns the x-value for one item in a series.
     * 
     * @param seriesIndex  the series index.
     * @param itemIndex  the item index.
     * 
     * @return The x-value. 
     */
    @Override
    public double getX(int seriesIndex, int itemIndex) {
        XYZSeries s = this.series.get(seriesIndex);
        return s.getXValue(itemIndex);
    }

    /**
     * Returns the y-value for one item in a series.
     * 
     * @param seriesIndex  the series index.
     * @param itemIndex  the item index.
     * 
     * @return The y-value. 
     */
    @Override
    public double getY(int seriesIndex, int itemIndex) {
        XYZSeries s = this.series.get(seriesIndex);
        return s.getYValue(itemIndex);
    }

    /**
     * Returns the z-value for one item in a series.
     * 
     * @param seriesIndex  the series index.
     * @param itemIndex  the item index.
     * 
     * @return The z-value. 
     */
    @Override
    public double getZ(int seriesIndex, int itemIndex) {
        XYZSeries s = this.series.get(seriesIndex);
        return s.getZValue(itemIndex);
    }

    /**
     * Tests this dataset for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> not permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof XYZSeriesCollection)) {
            return false;
        }
        XYZSeriesCollection that = (XYZSeriesCollection) obj;
        if (!this.series.equals(that.series)) {
            return false;
        }
        return true;
    }
    
    /**
     * Returns a string representation of this instance, primarily for 
     * debugging purposes.
     * <br><br>
     * Implementation note: the current implementation (which is subject to 
     * change) writes the dataset in JSON format using 
     * {@link JSONUtils#writeXYZDataset(com.orsoncharts.data.xyz.XYZDataset)}.
     * 
     * @return A string. 
     */
    @Override
    public String toString() {
        return JSONUtils.writeXYZDataset(this);
    }

}
