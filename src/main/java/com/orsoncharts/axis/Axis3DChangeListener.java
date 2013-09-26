/* ===========
 * OrsonCharts
 * ===========
 * 
 * (C)opyright 2013 by Object Refinery Limited.
 * 
 */

package com.orsoncharts.axis;

import java.util.EventListener;

/**
 * A listener for axis change events.
 */
public interface Axis3DChangeListener extends EventListener {
    
  /**
   * Called to inform that an axis change event has occurred.
   * 
   * @param event  the event (<code>null</code> not permitted). 
   */
  public void axisChanged(Axis3DChangeEvent event);
}
