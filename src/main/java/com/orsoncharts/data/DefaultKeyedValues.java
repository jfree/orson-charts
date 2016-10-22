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
import java.util.ArrayList;
import java.util.List;

import com.orsoncharts.util.ArgChecks;

/**
 * A list of {@code (key, value)} pairs.
 * <br><br>
 * This is the basic structure of the data required for a pie chart.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 * 
 * @param <K>  the key type (must implement Comparable).
 * @param <T>  the value type.
 */
@SuppressWarnings("serial")
public final class DefaultKeyedValues<K extends Comparable<K>, T> 
        implements KeyedValues<K, T>, Serializable {

    /** Storage for the data items. */
    private List<KeyedValue<K, T>> data;
  
    /**
     * Creates a new (empty) list of keyed values.
     */
    public DefaultKeyedValues() {
        this(new ArrayList<K>());
    }
  
    /**
     * Creates a new instance with the specified keys (each associated with
     * a {@code null} value).  There is usually no need to specify any
     * keys in advance, so you will normally use the default constructor.  This
     * constructor is provided for the convenience of some internal code.
     * 
     * @param keys  the keys ({@code null} not permitted).
     */
    public DefaultKeyedValues(List<K> keys) {
        ArgChecks.nullNotPermitted(keys, "keys");
        this.data = new ArrayList<KeyedValue<K, T>>();
        for (K key : keys) {
            this.data.add(new DefaultKeyedValue<K, T>(key, null));
        }
    }
  
    /**
     * Clears all the data.
     */
    public void clear() {
        this.data.clear();
    }
 
    /**
     * Adds a value or, if there is an existing value with the same key, updates 
     * an existing value.
     * 
     * @param key  the key ({@code null} not permitted)
     * @param value  the value.
     */
    public void put(K key, T value) {
        ArgChecks.nullNotPermitted(key, "key");
        DefaultKeyedValue<K, T> dkv;
        int index = getIndex(key);
        if (index >= 0) {
            dkv = (DefaultKeyedValue<K, T>) this.data.get(index);
            dkv.setValue(value);
        } else {
            this.data.add(new DefaultKeyedValue<K, T>(key, value));
        }
    }
  
    /**
     * Removes the item with the specified key, if there is one.
     * 
     * @param key  the key ({@code null} not permitted).
     */
    public void remove(K key) {
        ArgChecks.nullNotPermitted(key, "key");
        int index = getIndex(key);
        if (index >= 0) {
            remove(index);
        }
    }
  
    /**
     * Removes the item with the specified index.
     * 
     * @param index  the index. 
     */
    public void remove(int index) {
        this.data.remove(index);
    }
  
    /**
     * Returns the key for the item with the specified index.
     * 
     * @param index  the item index.
     * 
     * @return The key. 
     */
    @Override
    public K getKey(int index) {
        KeyedValue<K, T> kv = this.data.get(index);
        return kv.getKey();
    }

    /**
     * Returns the index of the item with the specified key, or {@code -1}
     * if there is no such item.
     * 
     * @param key  the key ({@code null} not permitted).
     * 
     * @return The item index, or {@code -1}. 
     */
    @Override
    public int getIndex(K key) {
        ArgChecks.nullNotPermitted(key, "key");
        for (int i = 0; i < this.data.size(); i++) {
            KeyedValue<K, T> kv = this.data.get(i);
            if (kv.getKey().equals(key)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns a list of all the keys.  Note that the list will be a copy, so
     * modifying it will not impact this data structure.
     * 
     * @return A list of keys (possibly empty, but never {@code null}).
     */
    @Override
    public List<K> getKeys() {
        List<K> keys = new ArrayList<K>();
        for (KeyedValue<K, T> kv : this.data) {
            keys.add(kv.getKey());
        }
        return keys;
    }

    /**
     * Returns the value with the specified key.
     * 
     * @param key  the key ({@code null} not permitted).
     * 
     * @return The value (possibly {@code null}). 
     */
    @Override
    public T getValue(K key) {
        // arg checking is performed by getIndex()
        int index = getIndex(key);
        if (index < 0) {
            return null;
        }
        return getValue(index);
    }

    /**
     * Returns the number of items in the list.
     * 
     * @return The number of items in the list. 
     */
    @Override
    public int getItemCount() {
        return this.data.size();
    }

    /**
     * Returns the value for the specified item.
     * 
     * @param item  the item index.
     * 
     * @return The value (possibly {@code null}). 
     */
    @Override
    public T getValue(int item) {
        KeyedValue<K, T> kv = this.data.get(item);
        return kv.getValue();
    }
  
    /**
     * Returns the value for the specified item, as a double primitive, 
     * provided that the data value is an instance of {@code Number}.
     * 
     * @param item  the item index.
     * 
     * @return The value.
     */
    @Override
    public double getDoubleValue(int item) {  
        T n = getValue(item);
        if (n != null && n instanceof Number) {
            return ((Number) n).doubleValue();
        }
        return Double.NaN;
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
        if (!(obj instanceof DefaultKeyedValues<?, ?>)) {
            return false;
        }
        DefaultKeyedValues<?, ?> that = (DefaultKeyedValues<?, ?>) obj;
        if (!this.data.equals(that.data)) {
            return false;
        }
        return true;
    }
}
