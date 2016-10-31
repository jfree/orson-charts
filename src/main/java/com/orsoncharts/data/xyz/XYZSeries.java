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
import javax.swing.event.EventListenerList;
import com.orsoncharts.data.Series3DChangeEvent;
import com.orsoncharts.data.Series3DChangeListener;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ObjectUtils;

/**
 * A data series containing a sequence of {@code (x, y, z)} data items.  
 * The series has an immutable key to identify it, and can be added to an 
 * {@link XYZSeriesCollection} to create a dataset.  When a series is part
 * of an {@link XYZSeriesCollection}, the collection will register with the
 * series to receive change events - in this way, the collection can notify
 * its own listeners when a change is made to the series.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 * 
 * @param <K> the type for the series key (it is recommended that this is a
 *     class of immutable objects, because the series key should never be
 *     modified).
 */
@SuppressWarnings("serial")
public class XYZSeries<K extends Comparable<K>> implements Serializable {

    /** The series key (never {@code null}). */
    private final K key;

    /** The data items in the series. */
    private final List<XYZDataItem> items;

    /** Storage for registered change listeners. */
    private EventListenerList listeners;
    
    /** A flag that controls whether or not changes are notified. */
    private boolean notify;
    
    /**
     * Creates a new series with the specified key.  Note that the series key
     * cannot be changed after it has been set in the constructor - this is by 
     * design, to ensure that each series in a {@link XYZSeriesCollection}
     * always has a unique key.  For the same reason, the key type should be
     * an immutable class.
     * 
     * @param key  the key ({@code null} not permitted). 
     */
    public XYZSeries(K key) {
        ArgChecks.nullNotPermitted(key, "key");
        this.key = key;
        this.items = new ArrayList<XYZDataItem>();
        this.listeners = new EventListenerList();
        this.notify = true;
    }

    /**
     * Returns the series key.
     * 
     * @return The series key (never {@code null}). 
     */
    public K getKey() {
        return this.key;
    }
    
    /**
     * Returns the number of items in the series.
     * 
     * @return The number of items in the series. 
     */
    public int getItemCount() {
        return this.items.size();
    }
    
    /**
     * Returns a list containing all the items for the dataset (a new list
     * is created each time this method is called, so the list can be freely
     * modified without affecting the state of this series).
     * 
     * @return A list of all items.
     * 
     * @since 1.6
     */
    public List<XYZDataItem> getItems() {
        return new ArrayList<XYZDataItem>(this.items); // leave redundant generics for Java 1.6
    }
    
    /**
     * Returns the x-value for the specified item in the series.
     * 
     * @param itemIndex  the item index.
     * 
     * @return The x-value. 
     */
    public double getXValue(int itemIndex) {
        return this.items.get(itemIndex).getX();
    }
  
    /**
     * Returns the y-value for the specified item in the series.
     * 
     * @param itemIndex  the item index.
     * 
     * @return The y-value. 
     */
    public double getYValue(int itemIndex) {
        return this.items.get(itemIndex).getY();
    }

    /**
     * Returns the z-value for the specified item in the series.
     * 
     * @param itemIndex  the item index.
     * 
     * @return The z-value. 
     */
    public double getZValue(int itemIndex) {
        return this.items.get(itemIndex).getZ();
    }

    /**
     * Adds a new data item to the series and sends a 
     * {@link Series3DChangeEvent} to all registered listeners.
     * 
     * @param x  the x-value.
     * @param y  the y-value.
     * @param z  the z-value.
     */
    public void add(double x, double y, double z) {
        add(new XYZDataItem(x, y, z));   
    }

    /**
     * Adds a new data item to the series and sends a 
     * {@link Series3DChangeEvent} to all registered listeners.
     * 
     * @param item  the data item ({@code null} not permitted).
     */
    public void add(XYZDataItem item) {
        ArgChecks.nullNotPermitted(item, "item");
        this.items.add(item);
        fireSeriesChanged();
    }
    
    /**
     * Removes a data item from the series and sends a 
     * {@link Series3DChangeEvent} to all registered listeners.
     * 
     * @param itemIndex  the item index.
     * 
     * @since 1.6
     */
    public void remove(int itemIndex) {
        this.items.remove(itemIndex);
        fireSeriesChanged();
    }

    /**
     * Registers an object with this series, to receive notification whenever
     * the series changes.
     * <P>
     * Objects being registered must implement the 
     * {@link Series3DChangeListener} interface.
     *
     * @param listener  the listener to register.
     * 
     * @since 1.6
     */
    public void addChangeListener(Series3DChangeListener listener) {
        this.listeners.add(Series3DChangeListener.class, listener);
    }

    /**
     * Deregisters an object, so that it not longer receives notification
     * whenever the series changes.
     *
     * @param listener  the listener to deregister.
     * 
     * @since 1.6
     */
    public void removeChangeListener(Series3DChangeListener listener) {
        this.listeners.remove(Series3DChangeListener.class, listener);
    }

    /**
     * Returns the flag that controls whether or not change events are sent to
     * registered listeners.
     *
     * @return A boolean.
     *
     * @see #setNotify(boolean)
     * @since 1.6
     */
    public boolean getNotify() {
        return this.notify;
    }

    /**
     * Sets the flag that controls whether or not change events are sent to
     * registered listeners.
     *
     * @param notify  the new value of the flag.
     *
     * @see #getNotify()
     * @since 1.6
     */
    public void setNotify(boolean notify) {
        if (this.notify != notify) {
            this.notify = notify;
            if (notify) {
                fireSeriesChanged();
            }
        }
    }
    
    /**
     * General method for signaling to registered listeners that the series
     * has been changed.
     * 
     * @since 1.6
     */
    public void fireSeriesChanged() {
        if (this.notify) {
            notifyListeners(new Series3DChangeEvent(this));
        }
    }

    /**
     * Sends a change event to all registered listeners.
     *
     * @param event  contains information about the event that triggered the
     *               notification.
     * 
     * @since 1.6
     */
    protected void notifyListeners(Series3DChangeEvent event) {
        Object[] listenerList = this.listeners.getListenerList();
        for (int i = listenerList.length - 2; i >= 0; i -= 2) {
            if (listenerList[i] == Series3DChangeListener.class) {
                ((Series3DChangeListener) listenerList[i + 1]).seriesChanged(
                        event);
            }
        }
    }

    /**
     * Tests this series for equality with an arbitrary object.
     * 
     * @param obj  the object to test ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof XYZSeries)) {
            return false;
        }
        XYZSeries that = (XYZSeries) obj;
        if (!this.key.equals(that.key)) {
            return false;
        }
        if (!this.items.equals(that.items)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + ObjectUtils.hashCode(this.key);
        return hash;
    }

}
