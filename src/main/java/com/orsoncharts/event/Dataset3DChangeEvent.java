/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.event;

import java.util.EventObject;

/**
 * A dataset change event.
 */
public class Dataset3DChangeEvent extends EventObject {

  private Object dataset;
  
  public Dataset3DChangeEvent(Object source, Object dataset) {
    super(source);
    this.dataset = dataset;
  }
  
  public Object getDataset() {
    return this.dataset;
  }
}
