/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.data;

import java.util.List;

/**
 * A three dimensional cube of data values where each value is uniquely 
 * identified by three keys (the xKey, yKey and zKey).
 */
public interface KeyedValues3D extends Values3D {

  public Comparable getXKey(int xIndex);
  public Comparable getYKey(int yIndex);
  public Comparable getZKey(int zIndex);

  public int getXIndex(Comparable xkey);
  public int getYIndex(Comparable ykey);
  public int getZIndex(Comparable zkey);
  
  public List<Comparable> getXKeys();
  public List<Comparable> getYKeys();
  public List<Comparable> getZKeys();

  public Number getValue(Comparable xKey, Comparable yKey, Comparable zKey);
}
