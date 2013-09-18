/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.data;

import java.util.List;

/**
 * A two dimensional grid of data values where each value is uniquely 
 * identified by two keys (the xKey and the yKey).
 */
public interface KeyedValues2D extends Values2D {

  public Comparable getXKey(int xIndex);
  public Comparable getYKey(int yIndex);

  public int getXIndex(Comparable xkey);
  public int getYIndex(Comparable ykey);
  
  public List<Comparable> getXKeys();
  public List<Comparable> getYKeys();

  public Number getValue(Comparable xKey, Comparable yKey);
}
