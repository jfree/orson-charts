/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.plot;

import java.util.EventListener;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DChangeEvent;

/**
 * An interface used to receive change events from {@link Plot3D} instances.
 * The {@link Chart3D} class will register with its plot to receive change
 * notifications - and upon receiving a change notification, it will pass it
 * on as a {@link Chart3DChangeEvent}.
 */
public interface Plot3DChangeListener extends EventListener {

    /**
     * Receives notification that a plot has changed.
     * 
     * @param event  event info. 
     */
    void plotChanged(Plot3DChangeEvent event);

}
