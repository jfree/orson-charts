/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

import com.orsoncharts.ArgChecks;
import java.util.ArrayList;
import java.util.List;

/**
 * A list of <code>(key, value)</code> pairs.  This is the basic structure of 
 * the data required for a pie chart.
 */
public class DefaultKeyedValues implements KeyedValues {

    private List<KeyedValue> data;
  
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
        this.data = new ArrayList<KeyedValue>();
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
     * Adds a <code>(key, value)</code> pair.  If an item already exists 
     * with the same <code>key</code>, the value of that item is updated.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * @param value  the value.
     */
    public void addValue(Comparable key, double value) {
        addValue(key, Double.valueOf(value));
    }
 
    /**
     * Adds a value or, if there is an existing value with the same key, updates 
     * an existing value.
     * 
     * @param key  the key (<code>null</code> not permitted)
     * @param value  the value.
     */
    public void addValue(Comparable key, Number value) {
        ArgChecks.nullNotPermitted(key, "key");
        DefaultKeyedValue dkv;
        int index = getIndex(key);
        if (index >= 0) {
            dkv = (DefaultKeyedValue) this.data.get(index);
            dkv.setValue(value);
        } else {
            this.data.add(new DefaultKeyedValue(key, value));
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
     * Returns a list of all the keys.
     * 
     * @return A list of all the keys. 
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
     * @return 
     */
    @Override
    public Number getValue(Comparable key) {
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
    public Number getValue(int item) {
        KeyedValue kv = this.data.get(item);
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
        Number n = getValue(item);
        if (n != null) {
            return n.doubleValue();
        }
        return Double.NaN;
    }
    
}
