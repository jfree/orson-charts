/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.data;

import java.util.ArrayList;
import java.util.List;

/**
 * A two dimensional grid of numerical data.
 */
public class DefaultKeyedValues2D implements KeyedValues2D {

    List<Comparable> xKeys;
    
    List<Comparable> yKeys;
    
    List<DefaultKeyedValues> data;
  
    public DefaultKeyedValues2D() {
        this.xKeys = new ArrayList<Comparable>();
        this.yKeys = new ArrayList<Comparable>();
        this.data = new ArrayList<DefaultKeyedValues>();    
    }

    @Override
    public Comparable getXKey(int xIndex) {
        return this.xKeys.get(xIndex);
    }

    @Override
    public Comparable getYKey(int yIndex) {
        return this.yKeys.get(yIndex);
    }

    @Override
    public int getXIndex(Comparable xkey) {
        return this.xKeys.indexOf(xkey);
    }

    @Override
    public int getYIndex(Comparable ykey) {
        return this.yKeys.indexOf(ykey);
    }

    /**
     * Returns a copy of the list of xKeys.
     * @return 
     */
    @Override
    public List<Comparable> getXKeys() {
        return new ArrayList(this.xKeys);
    }

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
    
}
