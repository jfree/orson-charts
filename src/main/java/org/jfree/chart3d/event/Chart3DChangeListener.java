/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.event;

import java.util.EventListener;

/**
 * An interface for receiving notification of changes to a {@link JFreeChart3D}
 * instance.
 */
public interface Chart3DChangeListener extends EventListener {
  
  /**
   * Called to inform that a chart change event has occurred.
   * 
   * @param event  the event. 
   */
  public void chartChanged(Chart3DChangeEvent event);
}
