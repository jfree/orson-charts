/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A three dimensional table of numerical values, implementing the 
 * {@link KeyedValues3D} interface.
 */
public class DefaultKeyedValues3D implements KeyedValues3D {

  private List<Comparable> xKeys;
  
  private List<Comparable> yKeys;
  
  private List<Comparable> zKeys;

  private List<DefaultKeyedValues2D> data;
  
  /**
   * Creates a new (empty) table.
   */
  public DefaultKeyedValues3D() {
    this.xKeys = new ArrayList<Comparable>();
    this.yKeys = new ArrayList<Comparable>();
    this.zKeys = new ArrayList<Comparable>();
    this.data = new ArrayList<DefaultKeyedValues2D>();
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
  public Comparable getZKey(int zIndex) {
    return this.zKeys.get(zIndex);
  }

  @Override
  public int getXIndex(Comparable xkey) {
    return this.xKeys.indexOf(xkey);
  }

  @Override
  public int getYIndex(Comparable ykey) {
    return this.yKeys.indexOf(ykey);
  }

  @Override
  public int getZIndex(Comparable zkey) {
    return this.zKeys.indexOf(zkey);
  }

  @Override
  public List<Comparable> getXKeys() {
    return Collections.unmodifiableList(this.xKeys);
  }

  @Override
  public List<Comparable> getYKeys() {
    return Collections.unmodifiableList(this.yKeys);
  }

  @Override
  public List<Comparable> getZKeys() {
    return Collections.unmodifiableList(this.zKeys);
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
  public int getZCount() {
    return this.zKeys.size();
  }

  @Override
  public Number getValue(int xIndex, int yIndex, int zIndex) {
    return this.data.get(zIndex).getValue(xIndex, yIndex);
  }
    
  @Override
  public Number getValue(Comparable xKey, Comparable yKey, Comparable zKey) {
    return getValue(getXIndex(xKey), getYIndex(yKey), getZIndex(zKey));
  }

  public void addValue(Number n, Comparable xKey, Comparable yKey, 
          Comparable zKey) {
      // cases:
      // 1 - the dataset is empty, so we just need to add a new layer with the
      //     given keys;
      // 2 - the three keys already exist -> just update the value
      // 3 - complicated part...
  }
}
