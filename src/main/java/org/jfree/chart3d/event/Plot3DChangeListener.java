/**
 * (C)opyright 2013, by Object Refinery Limited
 */
package org.jfree.chart3d.event;

import java.util.EventListener;
import org.jfree.chart3d.plot.Plot3D;

/**
 * An interface used to receive change events from {@link Plot3D} instances.
 */
public interface Plot3DChangeListener extends EventListener {

  public void plotChanged(Plot3DChangeEvent event);
}
