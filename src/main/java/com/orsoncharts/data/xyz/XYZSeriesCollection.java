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

package com.orsoncharts.data.xyz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.data.AbstractDataset3D;
import com.orsoncharts.data.Dataset3DChangeEvent;
import com.orsoncharts.data.JSONUtils;
import com.orsoncharts.data.Series3DChangeEvent;
import com.orsoncharts.data.Series3DChangeListener;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.xyz.XYZRenderer;
import com.orsoncharts.util.ObjectUtils;

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
public class XYZSeriesCollection<S extends Comparable<S>> 
        extends AbstractDataset3D 
        implements XYZDataset<S>, Series3DChangeListener, Serializable {

    /** Storage for the data series. */
    private final List<XYZSeries<S>> series;

    /**
     * Creates a new (empty) {@code XYZSeriesCollection} instance.
     */
    public XYZSeriesCollection() {
        this.series = new ArrayList<XYZSeries<S>>();
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
     * {@code -1} if there is no series with the specified key.
     * 
     * @param key  the key ({@code null} not permitted).
     * 
     * @return The series index or {@code -1}. 
     */
    @Override
    public int getSeriesIndex(S key) {
        ArgChecks.nullNotPermitted(key, "key");
        return getSeriesKeys().indexOf(key);
    }

    /**
     * Returns a new list containing all the series keys.  Modifying this list 
     * will have no impact on the {@code XYZSeriesCollection} instance.
     * 
     * @return A list containing the series keys (possibly empty, but never 
     *     {@code null}).
     */
    @Override
    public List<S> getSeriesKeys() {
        List<S> result = new ArrayList<S>();
        for (XYZSeries<S> s : this.series) {
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
    public S getSeriesKey(int seriesIndex) {
        return (S) getSeries(seriesIndex).getKey();
    }

    /**
     * Adds a series to the collection (note that the series key must be
     * unique within the collection).  The collection will automatically
     * register to receive change events from the series, and fire a 
     * {@link Dataset3DChangeEvent} whenever the data in the series changes.
     * 
     * @param series  the series ({@code null} not permitted). 
     */
    public void add(XYZSeries<S> series) {
        ArgChecks.nullNotPermitted(series, "series");
        if (getSeriesIndex(series.getKey()) >= 0) {
            throw new IllegalArgumentException("Another series with the same key already exists within the collection.");
        }
        this.series.add(series);
        series.addChangeListener(this);
        fireDatasetChanged();
    }
    
    /**
     * Removes a series from the collection and sends a
     * {@link Dataset3DChangeEvent} to all registered listeners.
     * 
     * @param seriesIndex  the series index.
     * 
     * @since 1.6
     */
    public void remove(int seriesIndex) {
        XYZSeries s = getSeries(seriesIndex);
        remove(s);
    }
    
    /**
     * Removes a series from the collection and sends a
     * {@link Dataset3DChangeEvent} to all registered listeners.  If the series
     * is not part of the collection, this method does nothing.
     *
     * @param series  the series ({@code null} not permitted).
     * 
     * @since 1.6
     */
    public void remove(XYZSeries series) {
        ArgChecks.nullNotPermitted(series, "series");
        if (this.series.contains(series)) {
            series.removeChangeListener(this);
            this.series.remove(series);
            fireDatasetChanged();
        }
    }

    /**
     * Removes all the series from the collection and sends a
     * {@link Dataset3DChangeEvent} to all registered listeners.  If the
     * collection is already empty, this method does nothing.
     */
    public void removeAll() {
        if (!this.series.isEmpty()) {
            for (XYZSeries s : this.series) {
                s.removeChangeListener(this);
            }
            this.series.clear();
            fireDatasetChanged();
        }
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
    public XYZSeries<S> getSeries(int index) {
        ArgChecks.checkArrayBounds(index, "index", this.series.size());
        return this.series.get(index);
    }
    
    /**
     * Returns the series with the specified key, or {@code null} if 
     * there is no such series.
     * 
     * @param key  the key ({@code null} not permitted).
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
     * Called when an observed series changes in some way.
     *
     * @param event  information about the change.
     * 
     * @since 1.6
     */
    @Override
    public void seriesChanged(Series3DChangeEvent event) {
        fireDatasetChanged();
    }

    /**
     * Tests this dataset for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} not permitted).
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + ObjectUtils.hashCode(this.series);
        return hash;
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
