/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.event;

import java.util.EventListener;

/**
 * A listener for...
 */
public interface Axis3DChangeListener extends EventListener {
    
  /**
   * Called to inform that an axis change event has occurred.
   * 
   * @param event  the event. 
   */
  public void axisChanged(Axis3DChangeEvent event);
}
