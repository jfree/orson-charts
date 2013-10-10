/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

import com.orsoncharts.util.ArgChecks;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A list of <code>(key, value)</code> pairs.  This is the basic structure of 
 * the data required for a pie chart.
 */
public final class DefaultKeyedValues<T> implements KeyedValues<T>, 
        Serializable {

    /** Storage for the data items. */
    private List<KeyedValue<T>> data;
  
    /**
     * Creates a new (empty) list of keyed values.
     */
    public DefaultKeyedValues() {
        this(new ArrayList<Comparable>());
    }
  
    /**
     * Creates a new instance with the specified keys (each associated with
     * a <code>null</code> value).  There is usually no need to specify any
     * keys in advance, so you will normally use the default constructor.  This
     * constructor is provided for the convenience of some internal code.
     * 
     * @param keys  the keys (<code>null</code> not permitted).
     */
    public DefaultKeyedValues(List<Comparable> keys) {
        ArgChecks.nullNotPermitted(keys, "keys");
        this.data = new ArrayList<KeyedValue<T>>();
        for (Comparable key : keys) {
            this.data.add(new DefaultKeyedValue(key, null));
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
     * @param key  the key (<code>null</code> not permitted)
     * @param value  the value.
     */
    public void addValue(Comparable key, T value) {
        ArgChecks.nullNotPermitted(key, "key");
        DefaultKeyedValue<T> dkv;
        int index = getIndex(key);
        if (index >= 0) {
            dkv = (DefaultKeyedValue<T>) this.data.get(index);
            dkv.setValue(value);
        } else {
            this.data.add(new DefaultKeyedValue<T>(key, value));
        }
    }
  
    /**
     * Removes the item with the specified key, if there is one.
     * 
     * @param key  the key (<code>null</code> not permitted).
     */
    public void removeValue(Comparable key) {
        ArgChecks.nullNotPermitted(key, "key");
        int index = getIndex(key);
        if (index >= 0) {
            removeValue(index);
        }
    }
  
    /**
     * Removes the item with the specified index.
     * 
     * @param index  the index. 
     */
    public void removeValue(int index) {
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
    public Comparable getKey(int index) {
        KeyedValue kv = this.data.get(index);
        return kv.getKey();
    }

    /**
     * Returns the index of the item with the specified key, or <code>-1</code>
     * if there is no such item.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return The item index, or <code>-1</code>. 
     */
    @Override
    public int getIndex(Comparable key) {
        ArgChecks.nullNotPermitted(key, "key");
        for (int i = 0; i < this.data.size(); i++) {
            KeyedValue kv = this.data.get(i);
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
     * @return A list of keys (possibly empty, but never <code>null</code>).
     */
    @Override
    public List<Comparable> getKeys() {
        List<Comparable> keys = new ArrayList<Comparable>();
        for (KeyedValue kv : this.data) {
            keys.add(kv.getKey());
        }
        return keys;
    }

    /**
     * Returns the value with the specified key.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return The value (possibly <code>null</code>). 
     */
    @Override
    public T getValue(Comparable key) {
        // arg checking is performed by getIndex()
        return getValue(getIndex(key));
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
     * @return The value (possibly <code>null</code>). 
     */
    @Override
    public T getValue(int item) {
        KeyedValue<T> kv = this.data.get(item);
        return kv.getValue();
    }
  
    /**
     * Returns the value for the specified item, as a double primitive.
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
     * @param obj  the object to test against (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DefaultKeyedValues)) {
            return false;
        }
        DefaultKeyedValues that = (DefaultKeyedValues) obj;
        if (!this.data.equals(that.data)) {
            return false;
        }
        return true;
    }
}
