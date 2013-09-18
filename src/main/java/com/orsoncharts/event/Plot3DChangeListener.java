/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package com.orsoncharts.event;

import java.util.EventListener;
import com.orsoncharts.plot.Plot3D;

/**
 * An interface used to receive change events from {@link Plot3D} instances.
 */
public interface Plot3DChangeListener extends EventListener {

  public void plotChanged(Plot3DChangeEvent event);
}
