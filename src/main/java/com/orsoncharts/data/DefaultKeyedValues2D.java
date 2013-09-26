/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.data;

import com.orsoncharts.ArgChecks;
import java.util.ArrayList;
import java.util.List;

/**
 * A two dimensional grid of numerical data.
 */
public class DefaultKeyedValues2D implements KeyedValues2D {

    List<Comparable> xKeys;
    
    List<Comparable> yKeys;
    
    List<DefaultKeyedValues> data;  // one entry per xKey
  
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
        this.data = new ArrayList<DefaultKeyedValues>();    
        for (Comparable c : xKeys) {
            this.data.add(new DefaultKeyedValues(yKeys));
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
        return this.yKeys.indexOf(ykey);
    }

    /**
     * Returns a copy of the list of xKeys.
     * 
     * @return A copy of the list of xKeys (never <code>null</code>). 
     */
    @Override
    public List<Comparable> getXKeys() {
        return new ArrayList(this.xKeys);
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

    @Override
    public int getXCount() {
        return this.xKeys.size();
    }

    @Override
    public int getYCount() {
        return this.yKeys.size();
    }

    @Override
    public Number getValue(Comparable xKey, Comparable yKey) {
        int xIndex = getXIndex(xKey);
        int yIndex = getYIndex(yKey);
        return getValue(xIndex, yIndex);
    }

    @Override
    public Number getValue(int xIndex, int yIndex) {
        return this.data.get(xIndex).getValue(yIndex);
    }

    @Override
    public double getDoubleValue(int xIndex, int yIndex) {
        Number n = getValue(xIndex, yIndex);
        if (n != null) {
            return n.doubleValue();
        }
        return Double.NaN;
    } 
    
    public void setValue(Number n, Comparable xKey, Comparable yKey) {
        // cases
        
        // 1.  No data - just add one new entry
        if (this.data.isEmpty()) {
            this.xKeys.add(xKey);
            this.yKeys.add(yKey);
            DefaultKeyedValues dkvs = new DefaultKeyedValues();
            dkvs.addValue(yKey, n);
            this.data.add(dkvs);
        } else {
            int xIndex = getXIndex(xKey);
            int yIndex = getYIndex(yKey);
            if (xIndex >= 0) {
                DefaultKeyedValues dkvs = this.data.get(xIndex);
                if (yIndex >= 0) {
                    // 2.  Both keys exist - just update the value
                    dkvs.addValue(yKey, n);
                } else {
                    // 3.  xKey exists, but yKey does not (add the yKey to each series)
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
                    DefaultKeyedValues d = new DefaultKeyedValues(this.yKeys);
                    d.addValue(yKey, n);
                    this.data.add(d);
                } else {
                    // 5.  neither key exists
                    // need to create the new series, plus the new entry in every series
                    this.xKeys.add(xKey);
                    this.yKeys.add(yKey);
                    for (DefaultKeyedValues kv : this.data) {
                        kv.addValue(yKey, null);
                    }
                    DefaultKeyedValues d = new DefaultKeyedValues(this.yKeys);
                    d.addValue(yKey, n);
                    this.data.add(d);
                }
            }
        }
    }
}
