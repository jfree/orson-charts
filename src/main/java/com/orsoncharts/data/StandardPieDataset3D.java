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

package com.orsoncharts.data;

import java.io.Serializable;
import java.util.List;

import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.plot.PiePlot3D;

/**
 * A dataset that can be used with a {@link PiePlot3D}.  This class represents
 * an ordered list of (key, value) items.  The keys can be any instance of
 * {@link Comparable} ({@code String} is commonly used) and the values
 * can be any {@link Number} instance (bearing in mind that the downstream 
 * code will use the {@code toDouble()} method to read values) or 
 * {@code null}.
 * <br><br>
 * This class provides an implementation of 
 * {@code KeyedValues&lt;Number&gt;}, so the following useful utility 
 * methods can be used:
 * <ul>
 * {@link DataUtils#total(com.orsoncharts.data.Values)}
 * {@link JSONUtils#writeKeyedValues(com.orsoncharts.data.KeyedValues)}
 * </ul>
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 * 
 * @param <K> the key type.
 */
@SuppressWarnings("serial")
public final class StandardPieDataset3D<K extends Comparable<K>> extends AbstractDataset3D 
        implements PieDataset3D<K>, Serializable {

    /** Storage for the data. */
    private DefaultKeyedValues<K, Number> data;

    /**
     * Creates a new (empty) dataset.
     */
    public StandardPieDataset3D() {
        this.data = new DefaultKeyedValues<K, Number>();
    }

    /**
     * Returns the number of items in the dataset.
     * 
     * @return The number of items in the dataset. 
     */
    @Override
    public int getItemCount() {
        return this.data.getItemCount();
    }

    /**
     * Returns the key for the specified item in the list.
     * 
     * @param item  the item index.
     * 
     * @return The key. 
     */
    @Override
    public K getKey(int item) {
        return this.data.getKey(item);
    }

    /**
     * Returns the index for the specified key, or {@code -1} if the key
     * is not present in the list.
     * 
     * @param key  the key ({@code null} not permitted).
     * 
     * @return The item index, or {@code -1}. 
     */
    @Override
    public int getIndex(K key) {
        return this.data.getIndex(key);
    }

    /**
     * Returns the value for the specified item.
     *
     * @param item  the item index.
     *
     * @return The value for the specified item (possibly {@code null}).
     */
    @Override
    public Number getValue(int item) {
        return this.data.getValue(item);
    }

    /**
     * Returns the value associated with the specified key, or 
     * {@code null}.
     * 
     * @param key  the key ({@code null} not permitted).
     * 
     * @return The value (possibly {@code null}). 
     */
    @Override
    public Number getValue(K key) {
        return this.data.getValue(key);
    }

    /**
     * Adds a value to the dataset (if there is already a value with the given
     * key, the value is overwritten) and sends a {@link Dataset3DChangeEvent}
     * to all registered listeners.
     * 
     * @param key  the key ({@code null} not permitted).
     * @param value  the value.
     */
    public void add(K key, double value) {
        add(key, Double.valueOf(value));
    }
    
    /**
     * Adds a value to the dataset (if there is already a value with the given
     * key, the value is overwritten) and sends a {@link Dataset3DChangeEvent}
     * to all registered listeners.
     * 
     * @param key  the key ({@code null} not permitted).
     * @param value  the value ({@code null} permitted).
     */
    public void add(K key, Number value) {
        ArgChecks.nullNotPermitted(key, "key");
        this.data.put(key, value);
        fireDatasetChanged();
    }

    /**
     * Returns a list of all the keys in the dataset.  Note that the list will 
     * be a copy, so modifying it will not impact this dataset.
     * 
     * @return A list of keys (possibly empty, but never {@code null}).
     */
    @Override
    public List<K> getKeys() {
        return this.data.getKeys();
    }

    /**
     * Returns the value for the specified item as a double primitive.  Where
     * the {@link #getValue(int)} method returns {@code null}, this method
     * returns {@code Double.NaN}.
     * 
     * @param item  the item index.
     * 
     * @return The value for the specified item. 
     */
    @Override
    public double getDoubleValue(int item) {
        return this.data.getDoubleValue(item);
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
        if (!(obj instanceof StandardPieDataset3D)) {
            return false;
        }
        StandardPieDataset3D that = (StandardPieDataset3D) obj;
        if (!this.data.equals(that.data)) {
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
     * {@link JSONUtils#writeKeyedValues(com.orsoncharts.data.KeyedValues)}.
     * 
     * @return A string. 
     */
    @Override
    public String toString() {
        return JSONUtils.writeKeyedValues(this);
    }
}
