/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.data;

import com.orsoncharts.event.Dataset3DChangeListener;

/**
 * The base interface for datasets in JFreeChart3D.  All datasets must support
 * the change event notification mechanism.
 */
public interface Dataset3D {

  /**
   * Registers a change listener to receive notification of changes to the
   * dataset.
   * 
   * @param listener  the listener (<code>null</code> not permitted). 
   */
  void addChangeListener(Dataset3DChangeListener listener);  
  
  /**
   * De-registers a change listener so that it no longer receives notification
   * of changes to the dataset.
   * 
   * @param listener  the listener (<code>null</code> not permitted). 
   */
  void removeChangeListener(Dataset3DChangeListener listener);  

}
