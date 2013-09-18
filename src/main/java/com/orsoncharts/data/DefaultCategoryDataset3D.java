/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.data;

import java.util.ArrayList;
import java.util.List;

/**
 * A default implementation of the {@link CategoryDataset3D} interface.
 */
public class DefaultCategoryDataset3D extends AbstractDataset3D  
    implements CategoryDataset3D {
  
  private List<Comparable> seriesKeys;
  
  private List<Comparable> rowKeys;
  
  private List<Comparable> columnKeys;
  
  public DefaultCategoryDataset3D() {
    this.seriesKeys = new ArrayList<Comparable>();  
  }

  @Override
  public List<Comparable> getSeriesKeys() {
    throw new UnsupportedOperationException("Not supported yet."); 
  }

  @Override
  public List<Comparable> getRowKeys() {
    throw new UnsupportedOperationException("Not supported yet."); 
  }

  @Override
  public List<Comparable> getColumnKeys() {
    throw new UnsupportedOperationException("Not supported yet."); 
  }

  @Override
  public Number getValue(Comparable seriesKey, Comparable rowKey, Comparable columnKey) {
    throw new UnsupportedOperationException("Not supported yet."); 
  }

  @Override
  public Comparable getXKey(int xIndex) {
    throw new UnsupportedOperationException("Not supported yet."); 
  }

  @Override
  public Comparable getYKey(int yIndex) {
    throw new UnsupportedOperationException("Not supported yet."); 
  }

  @Override
  public Comparable getZKey(int zIndex) {
    throw new UnsupportedOperationException("Not supported yet."); 
  }

  @Override
  public int getXIndex(Comparable xkey) {
    throw new UnsupportedOperationException("Not supported yet."); 
  }

  @Override
  public int getYIndex(Comparable ykey) {
    throw new UnsupportedOperationException("Not supported yet."); 
  }

  @Override
  public int getZIndex(Comparable zkey) {
    throw new UnsupportedOperationException("Not supported yet."); 
  }

  @Override
  public List<Comparable> getXKeys() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public List<Comparable> getYKeys() {
    throw new UnsupportedOperationException("Not supported yet."); 
  }

  @Override
  public List<Comparable> getZKeys() {
    throw new UnsupportedOperationException("Not supported yet."); 
  }

  @Override
  public int getXCount() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int getYCount() {
    throw new UnsupportedOperationException("Not supported yet."); 
  }

  @Override
  public int getZCount() {
    throw new UnsupportedOperationException("Not supported yet."); 
  }

  @Override
  public Number getValue(int xIndex, int yIndex, int zIndex) {
    throw new UnsupportedOperationException("Not supported yet."); 
  }
  
}
