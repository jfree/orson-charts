/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.data;

import java.util.List;

/**
 * A two dimensional grid of data values where each value is uniquely 
 * identified by two keys (the xKey and the yKey).
 */
public interface KeyedValues2D<T> extends Values2D<T> {

    public Comparable getXKey(int xIndex);

    public Comparable getYKey(int yIndex);

    public int getXIndex(Comparable xkey);

    public int getYIndex(Comparable ykey);
  
    public List<Comparable> getXKeys();
    public List<Comparable> getYKeys();

    public T getValue(Comparable xKey, Comparable yKey);
}
