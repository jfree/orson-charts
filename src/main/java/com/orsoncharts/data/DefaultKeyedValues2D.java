/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

import java.util.ArrayList;
import java.util.List;
import com.orsoncharts.util.ArgChecks;

/**
 * A two dimensional grid of numerical data.
 */
public final class DefaultKeyedValues2D<T> implements KeyedValues2D {

    /** The x-keys. */
    List<Comparable> xKeys;
    
    /** The y-keys. */
    List<Comparable> yKeys;
    
    /** The data values. */
    List<DefaultKeyedValues<T>> data;  // one entry per xKey
  
    /**
     * Creates a new (empty) instance.
     */
    public DefaultKeyedValues2D() {
        this(new ArrayList<Comparable>(), new ArrayList<Comparable>());
    }
    
    /**
     * Creates a new instance with the specified keys and all data values 
     * initialized to <code>null</code>.
     * 
     * @param xKeys  the xKeys (<code>null</code> not permitted).
     * @param yKeys  the yKeys (<code>null</code> not permitted).
     */
    public DefaultKeyedValues2D(List<Comparable> xKeys, 
            List<Comparable> yKeys) {
        ArgChecks.nullNotPermitted(xKeys, "xKeys");
        ArgChecks.nullNotPermitted(yKeys, "yKeys");
        this.xKeys = new ArrayList<Comparable>(xKeys);
        this.yKeys = new ArrayList<Comparable>(yKeys);
        this.data = new ArrayList<DefaultKeyedValues<T>>();    
        for (Comparable c : xKeys) {
            this.data.add(new DefaultKeyedValues<T>(yKeys));
        }
    }

    /**
     * Returns the x-key corresponding to the specified index.
     * 
     * @param xIndex  the index.
     * 
     * @return The key. 
     */
    @Override
    public Comparable getXKey(int xIndex) {
        return this.xKeys.get(xIndex);
    }

    /**
     * Returns the y-key corresponding to the specified index.
     * 
     * @param yIndex  the index.
     * 
     * @return The key. 
     */
    @Override
    public Comparable getYKey(int yIndex) {
        return this.yKeys.get(yIndex);
    }

    /**
     * Returns the index corresponding to the specified x-key.
     * 
     * @param xkey  the x-key (<code>null</code> not permitted).
     * 
     * @return The index. 
     */
    @Override
    public int getXIndex(Comparable xkey) {
        ArgChecks.nullNotPermitted(xkey, "xkey");
        return this.xKeys.indexOf(xkey);
    }

    /**
     * Returns the index corresponding to the specified y-key.
     * 
     * @param ykey  the y-key (<code>null</code> not permitted).
     * 
     * @return The index. 
     */
    @Override
    public int getYIndex(Comparable ykey) {
        ArgChecks.nullNotPermitted(ykey, "ykey");
        return this.yKeys.indexOf(ykey);
    }

    /**
     * Returns a copy of the list of xKeys.
     * 
     * @return A copy of the list of xKeys (never <code>null</code>). 
     */
    @Override
    public List<Comparable> getXKeys() {
        return new ArrayList<Comparable>(this.xKeys);
    }

    /**
     * Returns a copy of the list of y-keys.
     * 
     * @return A copy of the list of y-keys (never <code>null</code>). 
     */
    @Override
    public List<Comparable> getYKeys() {
        return new ArrayList<Comparable>(this.yKeys);
    }

    /**
     * Returns the number of x-keys in the table.
     * 
     * @return The number of x-keys in the table.
     */
    @Override
    public int getXCount() {
        return this.xKeys.size();
    }
    
    /**
     * Returns the number of y-keys in the data structure.
     * 
     * @return The number of y-keys.
     */
    @Override
    public int getYCount() {
        return this.yKeys.size();
    }

    /**
     * Returns a value from one cell in the table.
     * 
     * @param xKey  the x-key (<code>null</code> not permitted).
     * @param yKey  the y-key (<code>null</code> not permitted).
     * 
     * @return The value (possibly <code>null</code>).
     */
    @Override
    public T getValue(Comparable xKey, Comparable yKey) {
        // arg checking is handled in getXIndex() and getYIndex()
        int xIndex = getXIndex(xKey);
        int yIndex = getYIndex(yKey);
        return getValue(xIndex, yIndex);
    }

    /**
     * Returns the value from one cell in the table.
     * 
     * @param xIndex  the x-index.
     * @param yIndex  the y-index.
     * 
     * @return The value (possibly <code>null</code>). 
     */
    @Override
    public T getValue(int xIndex, int yIndex) {
        return this.data.get(xIndex).getValue(yIndex);
    }

    /**
     * Returns the data item at the specified position as a double primitive.
     * Where the {@link #getValue(int, int)} method returns <code>null</code>, 
     * this method returns <code>Double.NaN</code>.
     * 
     * @param xIndex  the x-index.
     * @param yIndex  the y-index.
     * 
     * @return The data value.
     */
    @Override
    public double getDoubleValue(int xIndex, int yIndex) {
        T n = getValue(xIndex, yIndex);
        if (n != null && n instanceof Number) {
            return ((Number) n).doubleValue();
        }
        return Double.NaN;
    } 

    /**
     * Sets a value for one cell in the table.
     * 
     * @param n  the value (<code>null</code> permitted).
     * @param xKey  the x-key (<code>null</code> not permitted).
     * @param yKey  the y-key (<code>null</code> not permitted).
     */
    public void setValue(T n, Comparable xKey, Comparable yKey) {
        ArgChecks.nullNotPermitted(xKey, "xKey");
        ArgChecks.nullNotPermitted(yKey, "yKey");
        
        if (this.data.isEmpty()) {  // 1. no data - just add one new entry
            this.xKeys.add(xKey);
            this.yKeys.add(yKey);
            DefaultKeyedValues dkvs = new DefaultKeyedValues<T>();
            dkvs.addValue(yKey, n);
            this.data.add(dkvs);
        } else {
            int xIndex = getXIndex(xKey);
            int yIndex = getYIndex(yKey);
            if (xIndex >= 0) {
                DefaultKeyedValues<T> dkvs = this.data.get(xIndex);
                if (yIndex >= 0) {
                    // 2.  Both keys exist - just update the value
                    dkvs.addValue(yKey, n);
                } else {
                    // 3.  xKey exists, but yKey does not (add the yKey to 
                    //     each series)
                    this.yKeys.add(yKey);
                    for (DefaultKeyedValues kv : this.data) {
                        kv.addValue(yKey, null);
                    }
                    dkvs.addValue(yKey, n);
                }
            } else {
                if (yIndex >= 0) {
                    // 4.  xKey does not exist, but yKey does
                    this.xKeys.add(xKey);
                    DefaultKeyedValues d = new DefaultKeyedValues<T>(
                            this.yKeys);
                    d.addValue(yKey, n);
                    this.data.add(d);
                } else {
                    // 5.  neither key exists, need to create the new series, 
                    //     plus the new entry in every series
                    this.xKeys.add(xKey);
                    this.yKeys.add(yKey);
                    for (DefaultKeyedValues<T> kv : this.data) {
                        kv.addValue(yKey, null);
                    }
                    DefaultKeyedValues<T> d = new DefaultKeyedValues<T>(
                            this.yKeys);
                    d.addValue(yKey, n);
                    this.data.add(d);
                }
            }
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DefaultKeyedValues2D)) {
            return false;
        }
        DefaultKeyedValues2D that = (DefaultKeyedValues2D) obj;
        if (!this.xKeys.equals(that.xKeys)) {
            return false;
        }
        if (!this.yKeys.equals(that.yKeys)) {
            return false;
        }
        if (!this.data.equals(that.data)) {
            return false;
        }
        return true;
    }
}
